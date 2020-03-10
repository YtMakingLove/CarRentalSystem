package com.ruixun.car.systemmgr.admin;


import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 管理员类
 *
 * Admin类是对系统使用者管理员角色的抽象，该类的实例代表一个“成功登录的管理员对象”，
 * 包含了账号、密码、管理员姓名等属性，还包含登录的静态方法，成功登录后返回当前的管理员对象，
 * 该对象拥有修改密码的方法。（无论登录也好还是修改密码也好，都基于隐藏在Admin类私有的，静态
 * 的load和save方法，来读取配置文件中管理员的信息），Admin的信息都存储在data\admin.txt中
 *
 * a)	注册(将所有注册管理员对象信息使用一个全局静态的Map<String,Admin>
 * 保存,只要程序不结束,该用户Map就一直存在!)
 * b)	登录
 * c)	修改密码
 * @author yt
 * @date 2020/3/6 - 17:45
 */
public class Admin {

    /**
     * 代表系统中所有管理员对象的列表
     */
    private static Set<Admin> adminLinkedHashSet = new LinkedHashSet<>();

    /**
     * 代表管理员的账号（对应的get、set方法不再类的方法中赘述）
     */
    private String loginId;

    /**
     * 代表管理员的密码（对应的get、set方法不再类的方法中赘述）
     */
    private String loginPsw;

    /**
     * 代表管理员的姓名（对应的get、set方法不再类的方法中赘述
     */
    private String name;

    public Admin() {
    }

    public Admin(String loginId, String loginPsw, String name) {
        this.loginId = loginId;
        this.loginPsw = loginPsw;
        this.name = name;
    }

    /**
     * 静态的登录方法，根据账号和密码查询管理员信息对象，没有查找到返回null
     *
     * @param loginId   用户名
     * @param loginPsw  密码
     * @return
     */
    public static Admin login(String loginId, String loginPsw) throws IOException {
        //先调用load()方法加载admin.txt中所有用户信息
        load();
        //遍历所有Admin对象
        for (Admin admin1 : adminLinkedHashSet) {
            //在所有的Admin对象中操作是否存在当前登录的用户名和密码
            if (loginId.equals(admin1.getLoginId()) && loginPsw.equals(admin1.getLoginPsw())){
                return admin1;
            }
        }
        return null;
    }

    /**
     * 注册
     * @param loginId   用户名
     * @param loginPsw  密码
     * @param userName  真实姓名
     * @return
     */
    public static boolean register(String loginId, String loginPsw, String userName) throws IOException {
        //先调用load()方法加载admin.txt中所有用户信息
        load();
        //遍历所有Admin对象
        for (Admin admin1 : adminLinkedHashSet) {
            //查询该用户名是否已被注册
            if (loginId.equals(admin1.getLoginId())){
                return false;
            }
        }
        //若该用户名还未注册，则将该用户添加到adminList中
        adminLinkedHashSet.add(new Admin(loginId, loginPsw, userName));
        //将新注册的用户保存到admin.txt文件中
        save();
        return true;
    }


    /**
     * 修改密码
     * @param admin    当前修改密码的Admin对象
     * @param oldPsw   旧密码
     * @param newPsw   新密码
     */
    public boolean modifyPsw(Admin admin, String oldPsw, String newPsw) throws IOException {
            for (Admin admin1 : adminLinkedHashSet) {
                //在adminList中查找当前admin
                if (admin.equals(admin1)){
                    //判断输入的旧密码是否相等
                    if (oldPsw.equals(admin1.getLoginPsw())){
                        //更新密码
                        admin1.setLoginPsw(newPsw);
                        //更新密码之后将新的管理员信息存入admin.txt中
                        save();
                        return true;
                    }
                }
            }
        return false;
    }

    /**
     * 读取admin.txt文件，加载所有的Admin对象信息
     *
     * @return
     */
    private static boolean load() throws IOException {
        try(BufferedReader buffReader = new BufferedReader(new FileReader("admin.txt"))) {
            String hasLine;
            while ((hasLine = buffReader.readLine()) != null){
                //将读取到的每一行用户信息根据用户名、密码、姓名进行分割
                String[] userDatas = hasLine.split("\\|");
                /*
                    创建Admin对象并添加到adminList中
                    ps:这里判断userDatas是为了防止文件里有回车换行符，读取
                    到之后造成数组线标越界
                 */
                if (userDatas.length == 3){
                    adminLinkedHashSet.add(new Admin(userDatas[0], userDatas[1], userDatas[2]));
                }
            }
            return true;
        }
    }


    /**
     * 存储adminList中的所有Admin对象信息
     *
     * 当有用户修改密码后，调用该方法将修改后的adminList重新覆盖写入
     * admin.txt中
     * @return
     */
    private static void save() throws IOException {
        try(BufferedWriter buffWriter =
                    new BufferedWriter(new FileWriter("admin.txt", false))) {
            for (Admin admin : adminLinkedHashSet) {
                //将adminLinkedHashSet中的每一个Admin对象转化String
                String admins = admin.getLoginId() + "|" + admin.getLoginPsw() +
                        "|" + admin.getName();
                buffWriter.write(admins);
                buffWriter.newLine();
            }
        }
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPsw() {
        return loginPsw;
    }

    public void setLoginPsw(String loginPsw) {
        this.loginPsw = loginPsw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj.getClass() == Admin.class){
            Admin admin = (Admin) obj;
            return admin.getLoginId().equals(this.getLoginId());
        }
        if (obj == this){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getLoginId().hashCode();
    }

    @Override
    public String toString() {
        return "Admin{" + "loginId='" + loginId + '\'' + ", loginPsw='" + loginPsw + '\'' + ", name='" + name + '\'' + '}';
    }

}
