package com.ruixun.car.systemmgr;

import com.ruixun.car.systemmgr.admin.Admin;
import com.ruixun.car.systemmgr.carlease.RentalRecord;
import com.ruixun.car.systemmgr.carmgr.CarMgr;
import com.ruixun.car.systemmgr.carmgr.car.Car;
import com.ruixun.car.systemmgr.carmgr.car.cartype.Coach;
import com.ruixun.car.systemmgr.carmgr.car.cartype.Truck;
import com.ruixun.car.systemmgr.util.DateHelper;
import com.ruixun.car.systemmgr.util.RegExp;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 系统管理类
 *
 * SystemManager类是整个“汽车租赁管理系统的抽象”，该类的实例代表一个“系统对象”，
 * 有一个work方法，调用后，整个系统开始工作。
 *
 * @author yt
 * @date 2020/3/6 - 17:45
 */
public class SystemManager {
    {
        System.out.println("***********************************************************************\n" +
                "*                                                                     *\n" +
                "*                           51汽车租赁系统                             *\n" +
                "*                                                                     *\n" +
                "***********************************************************************\n\n\n\n\n");
    }

    private static String systemName = "51汽车租赁系统>>>>>后台管理";

    private Scanner input = new Scanner(System.in);

    /**
     * 全局map用来存储当前登录管理员的信息
     */
    public static Map<String, Admin> adminMap = new HashMap<>();

    /**
     * 代表整个系统中的管理员
     */
    private Admin admin;
    /**
     * 代表系统中的汽车管理对象
     */
    private CarMgr carMgr = new CarMgr(this);

    /**
     * 开始工作的方法，显示系统名称，进入登录环节，登录环节可以
     * 调用Admin类的静态方法Admin login(String loginId, String loginPsw);
     */
    public void work() throws Exception {

        System.out.println("请管理员先登录\n\n1、登录\n2、注册");
        System.out.println("输入菜单编号进行选择，按其他任意键退出：");
        String loginId;
        String loginPsw;
        String temp = input.next();

        if (temp.equals("1")){
            //引导登录操作
            System.out.println("请输入账号：");
            loginId = input.next();
            System.out.println("请输入密码：");
            loginPsw = input.next();
            //调用Admin类的到来方法
            admin = Admin.login(loginId, loginPsw);
            if (admin != null){
                //将登录的用户添加到map中
                adminMap.put(loginId, admin);
                //登录成功进入系统菜单
                System.out.println("\n您好，" + admin.getName() + ",欢迎进入系统！\n");
                showMenu();
            }else {
                System.err.println("登录失败，账号或密码错误！");
                work();
            }
        }
        if (temp.equals("2")){
            //引导进行注册操作
            System.out.println("请输入账号(6-12位数字、字母或下划线)：");
            loginId = input.next();
            //验证用户输入的账户名信息是否合法
            if (RegExp.userNameVerification(loginId)){
                System.out.println("请输入密码(8-20位数字、字母或下划线)：");
                loginPsw = input.next();
                //验证用户输入的密码是否合法
                if (RegExp.userPasswordVerification(loginPsw)){
                    System.out.println("请输入真实姓名：");
                    String userName = input.next();
                    //调用注册方法注册
                    if (Admin.register(loginId, loginPsw, userName)){
                        System.out.println("注册成功！\n");
                        work();
                    }else {
                        System.err.println("该用户名已被注册！");
                        work();
                    }
                }else {
                    System.err.println("密码不合法！\n");
                    work();
                }
            }else {
                System.err.println("用户名不合法！\n");
                work();
            }
        }
    }

    /**
     * 登录成功后，进入后台管理主菜单，包括密码修改、汽车管理、租赁管理
     */
    private void showMenu() throws Exception {
        System.out.println(systemName + "\n");
        System.out.println("1、修改密码\n2、汽车管理\n3、租赁管理\n输入菜单编号进行选择，" +
                "按其它键退出系统：");
        String temp = input.next();

        if (temp.equals("1")){
            //调用修改密码方法
            changePassword();
        }

        if (temp.equals("2")){
            //调用汽车信息管理方法
            automobileManagement();
        }

        if (temp.equals("3")){
            //调用汽车租赁方法
            carRental();
        }
    }

    /**
     * 汽车信息管理
     */
    private void automobileManagement() throws Exception {
        System.out.println(systemName + ">>>>>汽车信息管理\n");
        System.out.println("1、新车入库\n2、汽车报废\n3、汽车信息浏览\n输入菜单编号进行选择，" +
                "按其它键返回上一级从菜单：");
        String temp = input.next();

        //新车入库
        if (temp.equals("1")){
            System.out.println(systemName + ">>>>>汽车信息管理>>>>>新车入库\n");
            System.out.println("请输入汽车车牌号：");
            String id = input.next();
            System.out.println("请输入汽车发动机号：");
            String engineNumber = input.next();
            System.out.println("请输入汽车车名：");
            String carName = input.next();
            System.out.println("请输入汽车颜色：");
            String color = input.next();
            System.out.println("请输入汽车出厂日期：");
            LocalDate dateOfEntry = DateHelper.getDate(input.next());
            System.out.println("请输入汽车行驶里程：");
            int mileage = Integer.valueOf(input.next());
            System.out.println("请输入汽车燃油类型：");
            String fuelType = input.next();
            System.out.println("请输入汽车租借押金：");
            int deposit = Integer.valueOf(input.next());
            System.out.println("请输入汽车汽车日租金：");
            int dailyRent = Integer.valueOf(input.next());
            System.out.println("请选择汽车类型(1、载客车     2、载货车)：");
            String choice = input.next();
            if (choice.equals("1")){
                System.out.println("请输入载客数：");
                int seatCount = Integer.valueOf(input.next());
                //调用入库方法
                if (carMgr.carAdd(id, engineNumber, carName, color, dateOfEntry, mileage, fuelType,
                        deposit, dailyRent, Car.STATE_KONGXIAN, seatCount, Integer.valueOf(choice))){
                    System.out.println("新车信息入库成功！");
                    //返回上一级
                    automobileManagement();
                }else {
                    System.err.println("新车信息入库失败，该车牌已存在！");
                    automobileManagement();
                }
            }
            if (choice.equals("2")){
                System.out.println("请输入载货量：");
                int cargoWeight = Integer.valueOf(input.next());
                //调用入库方法
                if (carMgr.carAdd(id, engineNumber, carName, color, dateOfEntry, mileage, fuelType,
                        deposit, dailyRent, Car.STATE_KONGXIAN, cargoWeight, Integer.valueOf(choice))){
                    System.out.println("新车信息入库成功！");
                    //返回上一级
                    automobileManagement();
                }else {
                    System.err.println("新车信息入库失败，该车牌已存在！");
                    automobileManagement();
                }
            }
        }

        //车辆报废
        if (temp.equals("2")){
            System.out.println(systemName + ">>>>>汽车信息管理>>>>>汽车报废\n");
            System.out.println("请输入要报废的汽车车牌号：");
            String carId = input.next();
            //查询出该车牌的信息
            String car = carMgr.carView(carId);
            if (car.equals("3")){
                System.err.println("报废失败，没有该车牌所对应车辆信息！");
                automobileManagement();
            }

            if (car.equals("2")){
                System.err.println("报废失败，该车辆已经被报废！");
                automobileManagement();
            }

            if (car.equals("1")){
                System.err.println("报废失败，该车辆已出租！");
                automobileManagement();
            }else {
                System.out.println("请确认要报废的车辆信息：" + car);
                System.out.println("确认报废输入y，按其它键返回上一级：");
                if (input.next().equalsIgnoreCase("y")){
                    if (carMgr.carStop(carId)){
                        System.out.println("汽车报废成功！");
                        automobileManagement();
                    }else{
                        System.err.println("汽车报废失败！");
                        automobileManagement();
                    }
                }else {
                    automobileManagement();
                }
            }
        }
        //车辆信息浏览
        if (temp.equals("3")){
            System.out.println(systemName + ">>>>>汽车信息管理>>>>>汽车信息浏览\n");
            carMgr.carView();
            automobileManagement();
        }
        showMenu();
    }

    /**
     * 汽车租赁方法
     */
    private void carRental() throws Exception {
        System.out.println(systemName + ">>>>>汽车信息管理\n");
        System.out.println("1、汽车租借\n2、汽车归还\n3、浏览租借归还记录\n输入菜单编号进行选择，" +
                "按其它键返回上一级从菜单：");
        String temp = input.next();

        //汽车租借
        if (temp.equals("1")){
            System.out.println(systemName + ">>>>>汽车信息管理>>>>>汽车租借\n");
            System.out.println("请输入要出租的汽车车牌号：");
            String carId = input.next();
            //操作当前租借的管理员姓名
            String adminName = admin.getName();
            //查询出该车牌的信息
            String car = carMgr.carView(carId);
            if (car.equals("1")){
                System.err.println("租借失败，车辆已出租！");
                carRental();
            }
            if (car.equals("2")){
                System.err.println("租借失败，该车辆已经被报废！");
                carRental();
            }
            if (car.equals("3")){
                System.err.println("租借失败，没有该车牌所对应的车辆信息！");
                carRental();
            }else {
                System.out.println("请确认要出租的车辆信息：" + car);
                System.out.println("确认出租输入y，按其它键取消并返回上一级：");
                if (input.next().equalsIgnoreCase("y")){
                    if (carMgr.carStop(carId)){
                        System.out.println("请输入租车人姓名：");
 ;                        String customer = input.next();
                        System.out.println("请输入租车人身份证号码：");
                        String pid = input.next();
                        System.out.println("请输入租车人联系电话：");
                        String tel = input.next();
                        System.out.println("请输入租车实际押金：");
                        int yj = Integer.valueOf(input.next());
                        System.out.println("请输入租车预计天数：");
                        int ts = Integer.valueOf(input.next());
                        //租车日期
                        String rentDate = DateHelper.getDate();
                        //调用租车方法，

                        if (carMgr.carRent(carId, adminName, customer, pid, tel, yj, ts, rentDate)){
                            System.out.println("汽车出租成功！");
                            carRental();
                        }
                        System.err.println("汽车出租失败！");
                        carRental();
                    }
                }else {
                    carRental();
                }
            }
        }

        //汽车归还
        if (temp.equals("2")){
            System.out.println(systemName + ">>>>>汽车信息管理>>>>>汽车归还\n");
            System.out.println("请输入要归还的汽车车牌号：");
            String carId = input.next();
            //先根据车牌查询该车牌的租借信息
            RentalRecord record = carMgr.recordView(carId);
            //根据当前车牌获取对应的Car对象
            Car car = carMgr.rtnCar(carId);
            if (record != null){
                System.out.println("请核对以下要归还的汽车信息：");
                System.out.println("车牌号：" + record.getCarCph());
                System.out.println("车名：" + car.getName());
                System.out.println("日租金：" + car.getRent_money_day());
                //判断是客车还是货车
                if (car.getClass() == Coach.class){
                    Coach coach = (Coach) car;
                    System.out.println("载客人数：" + coach.getSeatCount());
                }else {
                    Truck truck = (Truck) car;
                    System.out.println("载货量：" + truck.getWeight());
                }
                System.out.println("租车人：" + record.getCustomer());
                System.out.println("已交押金：" + record.getYj());
                System.out.println("租车日期：" + record.getRentDate());
                System.out.println("当前日期：" + record.getReturnDate());
                System.out.println("实际天数：" + record.getSjts());
                System.out.println("租车费用：" + record.getSr());
                System.out.println("\n确认归还y/n");
                String confirm = input.next();
                if (confirm.equalsIgnoreCase("y")){
                    carMgr.carReturn(record);
                    System.out.println("归还成功！");
                    carRental();
                }else if (confirm.equalsIgnoreCase("n")){
                    carRental();
                }else {
                    System.err.println("输入有误！");
                    carRental();
                }
            }
            carRental();
        }

        //浏览租借信息
        if (temp.equals("3")){
            System.out.println(systemName + ">>>>>汽车信息管理>>>>>租借归还信息浏览\n");
            carMgr.recordView();
            System.out.println("按任意非空格键返回上一级：");
            String tm = input.next();
            if (!tm.equals(" ")){
                carRental();
            }
        }
        showMenu();
    }

    /**
     * 修改密码方法
     *
     * @throws ParseException
     * @throws IOException
     */
    private void changePassword() throws Exception {
        System.out.println(systemName + ">>>>>修改密码\n");
        System.out.println("1、修改密码\n输入菜单编号进行选择，按其它键返回上一级从菜单：");
        String choice = input.next();
        if (choice.equals("1")){
            System.out.println("请输入旧密码：");
            String oldPsw = input.next();
            System.out.println("请输入新密码(8-20位数字、字母或下划线)：");
            String newPsw = input.next();
            if (RegExp.userPasswordVerification(newPsw)){
                //调用修改密码的方法
                boolean flag = admin.modifyPsw(admin, oldPsw, newPsw);
                if (flag){
                    System.out.println("修改密码成功！\n");
                    showMenu();
                }else {
                    System.err.println("旧密码错误，修改密码失败！\n");
                    showMenu();
                }
            }else {
                System.err.println("密码不合法！\n");
                showMenu();
            }
        }
        showMenu();
    }

}
