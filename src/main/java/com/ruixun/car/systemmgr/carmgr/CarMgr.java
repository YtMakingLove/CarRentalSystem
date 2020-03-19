package com.ruixun.car.systemmgr.carmgr;

import com.ruixun.car.systemmgr.SystemManager;
import com.ruixun.car.systemmgr.carlease.RentalRecord;
import com.ruixun.car.systemmgr.carmgr.car.Car;
import com.ruixun.car.systemmgr.carmgr.car.cartype.Coach;
import com.ruixun.car.systemmgr.carmgr.car.cartype.Truck;
import com.ruixun.car.systemmgr.util.DateHelper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

/**
 * 汽车管理类
 *
 * CarMgr类是对系统中“汽车相关操作的抽象”，该类是整个系统中最复杂的类，
 * 它对汽车入库、报废、浏览，汽车的租借、归还、租赁记录浏览，而支持这些
 * 操作的底层就是对汽车信息和租赁信息的读取和存储。
 *
 * @author yt
 * @date 2020/3/6 - 17:50
 */
public class CarMgr {
    /**
     * 汽车列表
     */
    private Set<Car> carSet;

    /**
     * 租赁记录列表
     */
    private ArrayList<RentalRecord> recordList;
    {
        try {
             carSet = new LinkedHashSet<>();
             recordList = new ArrayList<>();
            load();
            loadRecord();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("加载车库车辆信息出错！");
        }
    }



    /**
     * 系统管理对象，通过CarMgr的构造方法传入
     */
    private SystemManager sys;

    public CarMgr() {
    }

    public CarMgr(SystemManager sys) {
        this.sys = sys;
    }

    /**
     * 加载汽车信息
     */
    private boolean load() throws IOException {
        try(BufferedReader buffReader = new BufferedReader(new FileReader("car.txt"))) {
            String hasLine;
            while ((hasLine = buffReader.readLine()) != null){
                //将读取到的每一行车辆信息根据|进行分隔
                String[] carDatas = hasLine.split("\\|");
                /*
                    创建car对象并添加到carSet中
                    ps:这里判断userDatas是为了防止文件里有回车换行符，读取
                    到之后造成数组线标越界
                 */
                if (carDatas.length == 12){
                    //根据车辆类型创建对应类型的车辆对象
                    if (carDatas[11].equals(Car.TYPE_ZAIKECHE)){
                        //创建客车
                        carSet.add(new Coach(carDatas[0], carDatas[1], carDatas[2], carDatas[3],
                                DateHelper.getDate(carDatas[4]), Integer.valueOf(carDatas[5]),
                                carDatas[6], Integer.valueOf(carDatas[7]),
                                Integer.valueOf(carDatas[8]), carDatas[9],
                                Integer.valueOf(carDatas[10])));
                    }else {
                        //创建货车
                        carSet.add(new Truck(carDatas[0], carDatas[1], carDatas[2], carDatas[3],
                                DateHelper.getDate(carDatas[4]), Integer.valueOf(carDatas[5]),
                                carDatas[6], Integer.valueOf(carDatas[7]),
                                Integer.valueOf(carDatas[8]), carDatas[9],
                                Integer.valueOf(carDatas[10])));
                    }
                }
            }
            return true;
        }
    }


    /**
     * 存储汽车信息
     * @return
     */
    private void save() throws IOException {
            try(BufferedWriter buffWriter =
                        new BufferedWriter(new FileWriter("car.txt", false))) {
                for (Car car : carSet) {
                    //判断当前车辆类型
                    if (car.getClass() == Coach.class){
                        Coach coach = (Coach) car;
                        //将acarSet中的每一个Car对象转化String
                        String cars = coach.getId() + "|" + coach.getEngine() + "|" + coach.getName()
                                + "|" + coach.getColor() + "|" + coach.getProduct_date()
                                + "|" + coach.getMiles() + "|" + coach.getOil_type() + "|" +
                                car.getRent_security() + "|" + coach.getRent_money_day() + "|" +
                                coach.getState() + "|" + coach.getSeatCount() + "|" + Car.TYPE_ZAIKECHE;
                        buffWriter.write(cars);
                        buffWriter.newLine();
                    }else {
                        Truck truck = (Truck) car;
                        //将acarSet中的每一个Car对象转化String
                        String cars = truck.getId() + "|" + truck.getEngine() + "|" + truck.getName()
                                + "|" + truck.getColor() + "|" + truck.getProduct_date()
                                + "|" + truck.getMiles() + "|" + truck.getOil_type() + "|" +
                                car.getRent_security() + "|" + truck.getRent_money_day() + "|"
                                + truck.getState() + "|" + truck.getWeight() + "|" + Car.TYPE_ZAIHUOCHE;
                        buffWriter.write(cars);
                        buffWriter.newLine();
                    }
                }
            }
    }


    /**
     * 汽车信息浏览
     */
    public void carView() {
        for (Car car : carSet) {
            //判断当前车辆类型
            if (car.getClass() == Coach.class){
                Coach coach = (Coach) car;
                //将acarSet中的每一个Car对象转化String
                String cars = "车牌号：" + coach.getId() + "，发动机号：" + coach.getEngine() + "，车名："
                        + coach.getName() + "，颜色：" + coach.getColor() + "，出厂日期：" +
                        coach.getProduct_date() + "，行驶里程：" +
                        coach.getMiles() + "，燃油类型：" + coach.getOil_type() + "，租车押金：" +
                        coach.getRent_security() + "，日租金：" + coach.getRent_money_day() + "，载客数："
                        + coach.getSeatCount() + "，状况：" + coach.getState();
                System.out.println(cars);

            }else {
                Truck truck = (Truck) car;
                //将acarSet中的每一个Car对象转化String
                String cars = "车牌号：" + truck.getId() + "，发动机号：" + truck.getEngine() + "，车名："
                        + truck.getName() + "，颜色：" + truck.getColor() + "，出厂日期：" +
                        truck.getProduct_date() + "，行驶里程：" +
                        truck.getMiles() + "，燃油类型：" + truck.getOil_type() + "，租车押金：" +
                        truck.getRent_security() + "，日租金：" + truck.getRent_money_day() + "，载货量："
                        + truck.getWeight() + "，状况：" + truck.getState();
                System.out.println(cars);
            }
        }
    }

    /**
     * 查看指定车牌的汽车信息
     *
     * @param carId  汽车车牌号
     * @throws IOException
     * @throws ParseException
     */
    public String carView(String carId) {
        boolean flag = false;
        Car tempCar = null;
        //遍历所有car对象
        for (Car car : carSet) {
            //查找carSet中是否已有该车牌
            if (car.getId().equals(carId)){
                flag = true;
                tempCar = car;
            }
        }
        if (flag){
            if (!tempCar.getState().equals(Car.STATE_BAOFEI)){
                if (!tempCar.getState().equals(Car.STATE_YICHUZU)){
                    // 在判断是客车还是货车
                    if (tempCar.getClass() == Coach.class){
                        Coach coach = (Coach) tempCar;
                        //将acarSet中的每一个Car对象转化String
                        String cars = "车牌号：" + coach.getId() + "，发动机号：" + coach.getEngine()
                                + "，车名：" + coach.getName() + "，颜色：" + coach.getColor() +
                                "，出厂日期：" + coach.getProduct_date() +
                                "，行驶里程：" + coach.getMiles() + "，燃油类型：" + coach.getOil_type()
                                + "，租车押金：" + coach.getRent_security() + "，日租金：" +
                                coach.getRent_money_day() + "，载客数：" + coach.getSeatCount() +
                                "，状况：" + coach.getState();
                        return cars;
                    }else {
                        Truck truck = (Truck) tempCar;
                        String cars = "车牌号：" + truck.getId() + "，发动机号：" + truck.getEngine()
                                + "，车名：" + truck.getName() + "，颜色：" + truck.getColor() +
                                "，出厂日期：" + truck.getProduct_date() +
                                "，行驶里程：" + truck.getMiles() + "，燃油类型：" + truck.getOil_type()
                                + "，租车押金：" + truck.getRent_security() + "，日租金：" +
                                truck.getRent_money_day() + "，载货量：" + truck.getWeight() +
                                "，状况：" + truck.getState();
                        return cars;
                    }
                }
                //该车已出租
                return "1";
            }
            //该车已报废
            return "2";
        }
        //没该车牌对应的汽车信息
        return "3";
    }

    /**
     * 汽车入库
     * @param id 车牌号
     * @param engine 发动机编号
     * @param name 汽车车名
     * @param color 汽车颜色
     * @param product_date 出厂日期
     * @param miles 行驶里程
     * @param oil_type 燃油类型
     * @param rent_security 租车押金
     * @param rent_money_day 日租金
     * @param state 车辆状况
     * @param seatCountOrWeight 载客数或载货量
     * @param carType   汽车类型
     * @return
     * @throws IOException
     */
    public boolean carAdd(String id, String engine, String name, String color, LocalDate product_date,
                          int miles, String oil_type, int rent_security, int rent_money_day,
                          String state, int seatCountOrWeight, int carType) throws IOException {

        boolean flag = false;
        synchronized (this) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //遍历所有car对象
            for (Car car : carSet) {
                //查找carSet中是否已有该车牌
                if (car.getId().equals(id)){
                    flag = true;
                }
            }
            if (!flag){
                    if (carType == 1){
                        //将客车信息添加到carSet中，查询carSet中是否已存储该车牌信息
                        carSet.add(new Coach(id, engine, name, color, product_date, miles, oil_type,
                                rent_security, rent_money_day, state, seatCountOrWeight));
                        //并保存到car.txt文件中
                        save();
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println(Thread.currentThread().getName() + " : 入库了车牌为：" + id);
                        return true;
                    }else {
                        //将货车信息添加到carSet中，查询carSet中是否已存储该车牌信息
                        carSet.add(new Truck(id, engine, name, color, product_date, miles, oil_type,
                                rent_security, rent_money_day, state, seatCountOrWeight));
                        //并保存到car.txt文件中
                        save();
                        System.out.println(Thread.currentThread().getName() + " : 入库了车牌为：" + id);
                        return true;
                    }
            }else {
                //System.out.println(Thread.currentThread().getName() + ":已有该车牌");
                //已有该车牌
                return false;
            }
        }

    }
    public static void main(String[] args) {
        CarMgr carMgr = new CarMgr();
        new Thread(() -> {
            try {
//                carMgr.carAdd("鲁8739", "FF8484", "宝马x5", "红色",
//                        DateHelper.getDate("2020-09-08"), 87333, "#97",
//                        80000,
//                        788, "空闲", 7, 1);
                carMgr.carStop("鲁8739");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
//                carMgr.carAdd("鲁8739", "FF8484", "宝马x5", "红色",
//                        DateHelper.getDate("2020-09-08"), 87333, "#97",
//                        80000,
//                        788, "空闲", 7, 1);
                carMgr.carStop("鲁8739");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 汽车报废
     * @param id 车牌号(车辆唯一标识)
     */
    public boolean carStop(String id) throws IOException {

        //在carSet中找到当前车牌对应的车辆信息
        for (Car car: carSet) {
            synchronized (this) {
                if (car.getId().equals(id)){
                    if (car.getState().equals(Car.STATE_BAOFEI)){
                        System.out.println(Thread.currentThread().getName() + ": 该车已报废！");
                        return false;
                    }
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //修改车辆状况为"报废"
                    car.setState(Car.STATE_BAOFEI);
                    //将更新的状况信息更新到car.txt中
                    save();
                   System.out.println(Thread.currentThread().getName() + " : 报废了" + id);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 加载汽车租赁记录信息
     * @return
     */
    private boolean loadRecord() throws IOException {
        try(BufferedReader buffReader = new BufferedReader(new FileReader("record.txt"))) {
            String hasLine;
            while ((hasLine = buffReader.readLine()) != null){
                //将读取到的每一行车辆信息根据|进行分隔
                String[] recDatas = hasLine.split("\\|");
                /*
                    创建RentalRecord对象并添加到List中
                    ps:这里判断recDatas是为了防止文件里有回车换行符，读取
                    到之后造成数组线标越界
                 */
                if (recDatas.length == 11){
                    //将租借信息加载到list中
                    recordList.add(new RentalRecord(recDatas[0], recDatas[1], recDatas[2], recDatas[3],
                            recDatas[4], Integer.valueOf(recDatas[5]), Integer.valueOf(recDatas[6]),
                            recDatas[7], recDatas[8], Integer.valueOf(recDatas[9]),
                            Integer.valueOf(recDatas[10])));
                }
            }
            return true;
        }
    }

    /**
     * 存储汽车租赁记录信息
     * @return
     */
    private void saveRecord() throws IOException {
        try(BufferedWriter buffWriter =
                    new BufferedWriter(
                            new FileWriter("record.txt", false))) {
            for (RentalRecord record : recordList) {
                //将list中的每一个RentalRecord对象转化String
                String records = record.getCarCph() + "|" + record.getAdminName() + "|" + record.getCustomer()
                        + "|" + record.getPid() + "|" +record.getTel() + "|" + record.getYj() + "|" +
                        record.getTs() + "|" + record.getRentDate() + "|" + record.getReturnDate() +
                        "|" + record.getSjts() + "|" + record.getSr() + "|";
                buffWriter.write(records);
                buffWriter.newLine();
            }
        }
    }

    /**
     * 汽车租借
     * @param carCph    车牌号
     * @param adminName  管理员姓名
     * @param customer  租车人姓名
     * @param pid   租车人身份证号码
     * @param tel   租车人电话号码
     * @param yj    实缴押金
     * @param ts    预计租借天数
     * @param rentDate  租借日期
     * @return
     * @throws Exception
     */
    public boolean carRent(String carCph, String adminName, String customer, String pid,
                           String tel, int yj, int ts, String rentDate) throws Exception {
        //在carSet中查询是否有该车牌
        for (Car car : carSet) {
            if (car.getId().equals(carCph)){
                //修改车辆状况为已出租
                car.setState(Car.STATE_YICHUZU);
                //将修改后的信息更新到car.txt
                save();
                //将当前租借信息添加到list中
                recordList.add(new RentalRecord(carCph, adminName, customer, pid, tel, yj, ts, rentDate));
                //将修改后的信息更新到record.txt
                saveRecord();
                //记录归还日志
                leaseLog(carCph, pid, "1", rentDate);
                return true;
            }
        }
        return false;
    }

    /**
     * 汽车归还
     * @param record    租借记录实例
     * @throws Exception
     */
    public void carReturn(RentalRecord record) throws Exception {
        //通过车牌号在recordList中查找当前归还的对应车辆，更新对应的信息
        for (RentalRecord rentalRecord : recordList) {
            if (rentalRecord.getCarCph().equals(record.getCarCph())){
                //更新归还日期
                rentalRecord.setReturnDate(record.getReturnDate());
                //更新实际租车天数
                rentalRecord.setSjts(record.getSjts());
                //更新租车收入
                rentalRecord.setSr(record.getSr());
                //保存到record.txt
                saveRecord();
            }
        }
        //更新carSet和car.txt里的车辆状况信息
        for (Car car : carSet) {
            if (car.getId().equals(record.getCarCph())){
                //更改车辆状况为空闲
                car.setState(Coach.STATE_KONGXIAN);
                //将更新的信息保存更新到car.txt
                save();
            }
        }
        //记录归还日志
        leaseLog(record.getCarCph(), record.getPid(), "2", record.getReturnDate());
    }

    /**
     * 租赁记录信息浏览
     */
    public void recordView(){
        if (recordList.size() > 0){
            for (RentalRecord record : recordList) {
                String records = "租出车牌号：" + record.getCarCph() + "，管理员：" + record.getAdminName()
                        + "，租车人：" + record.getCustomer() + "，身份证号码：" + record.getPid() +
                        "，联系电话：" + record.getTel() + "，实缴押金：" + record.getYj() + "，预计天数："
                        + record.getTs() + "，租车日期：" + record.getRentDate() + "，归还日期：" +
                        record.getReturnDate() + "，实际天数：" + record.getSjts() + "，收入：" +
                        record.getSr();
                System.out.println(records);
            }
        }else {
            System.err.println("系统还没有任何租借记录！");
        }

    }

    /**
     * 浏览指定车牌对应的车辆信息
     * @param carId 车牌号
     */
    public RentalRecord recordView(String carId) throws ParseException {
        boolean flag = false;
        RentalRecord record = null;
        //遍历所有record对象
        for (RentalRecord rentalRecord : recordList) {
            if (rentalRecord.getCarCph().equals(carId)){
                flag = true;
                record = rentalRecord;
            }
        }
        if (flag){
            //判断该车牌对应的汽车是否处于未归还的状态
            if (record.getReturnDate().equals("未归还")){
                //获得当前归还日期
                String returnDate = DateHelper.getDate();
                //计算出实际天数
                int sjts = DateHelper.getDayDiff(record.getRentDate(), returnDate);
                //租车费用
                int sr = 0;
                //判断当前车牌对应的汽车类型，默认为货车
                for (Car c : carSet) {
                    if (c.getId().equals(carId)){
                        if (c.getClass() == Coach.class){
                            Coach coach = (Coach) c;
                            //调用客车计算费用的方法
                            sr = coach.calcRental(sjts) - record.getYj();
                        }else {
                            Truck truck = (Truck) c;
                            //调用货车计算费用的方法
                            sr = truck.calcRental(sjts) - record.getYj();
                        }
                    }
                }
                record.setReturnDate(returnDate);
                record.setSjts(sjts);
                record.setSr(sr);
                return record;
            }
            System.err.println("该汽车已处于归还状态，无需操作！");
            return null;
        }
        System.err.println("没有该车牌的租借信息！");
        return null;
    }

    /**
     * 根据车牌返回对应car对象
     *
     * @param carId
     * @return
     */
    public Car rtnCar(String carId){
        for (Car car : carSet) {
            if (car.getId().equals(carId)){
                return car;
            }
        }
        return null;
    }


    /**
     * 租借日志记录-添加子节点
     *
     * @param carId 车牌号
     * @param pid   租车人身份证毫秒
     * @param type  出租/归还
     * @param date  租车日期/归还日期
     */
    public void leaseLog(String carId, String pid, String type, String date) throws Exception {
        //获得文档对象
        Document dom = getDom("leaseLog.xml");
        //获得根标签
        Element root = dom.getRootElement();
        //添加一个record节点
        Element record = root.addElement("record");
        //给person的添加子节点
        record.addElement("carId").setText(carId);
        record.addElement("pid").setText(pid);
        record.addElement("type").setText(type);
        record.addElement("date").setText(date);

        //设置添加节点的样式，不然添加进去全部在一行
        OutputFormat format = OutputFormat.createPrettyPrint();
         /*
            修改之后需要关闭流才会刷新
            ps:只要是同步修改或者添加的都要关闭流，才会自动刷新缓冲更新
        */
        XMLWriter xmlWriter = new XMLWriter(new FileWriter("leaseLog.xml"), format);
        xmlWriter.write(dom);
        xmlWriter.close();
        setLogId();
    }

    /**
     * 给日志记录设置id属性
     * @throws Exception
     */
    public void setLogId() throws Exception {
        //获得文档对象
        Document dom = getDom("leaseLog.xml");
        //获得根标签
        Element root = dom.getRootElement();
        //用根标签获得所有person标签的对象
        List<Element> recordList = root.elements("record");
        for (int i = 0; i < recordList.size(); i++) {
            Element rod = recordList.get(i);
            //给
            rod.addAttribute("id", i + 1 + "");
        }
        FileWriter fileWriter = new FileWriter("leaseLog.xml");
        dom.write(fileWriter);
        fileWriter.close();
    }

    /**
     * 获取dom对象的方法
     *
     * @return
     * @throws Exception
     */
    public static Document getDom(String xmlFilePath) throws Exception{
        //创建文件
        File xmlFile = new File(xmlFilePath);
        //创建一个XML读写器对象
        SAXReader reader = new SAXReader();
        /*
            回顾XML的几个要素：
            文档Document
            节点元素：
            Element--标签
            Attribute--属性
            标签体
         */
        //获得XML文档对象
        Document dom = reader.read(xmlFile);
        return dom;
    }
}
