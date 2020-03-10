package com.ruixun.car.systemmgr.carlease;

/**
 * 汽车租赁记录类
 *
 * RentalRecord类是对系统中“汽车租、还记录的抽象”，该类包含了租车人的信息、租车的押金、
 * 租车日期、还车日期、租车的实际天数、租车等办理人、租车的收入等属性。
 * RentalRecord的信息都存储在data\record.txt中
 *
 * @author yt
 * @date 2020/3/6 - 17:49
 */
public class RentalRecord {


    /**
     * 租用的汽车车牌号
     */
    private String carCph;

    /**
     * 办理租车手续的管理员姓名
     */
    private String adminName;

    /**
     * 租车人
     */
    private String customer;

    /**
     * 租车人身份证号码
     */
    private String pid;

    /**
     * 租车人联系电话
     */
    private String tel;

    /**
     * 实缴押金
     */
    private int yj;

    /**
     * 预计租车天数
     */
    private int ts;

    /**
     * 租车日期
     */
    private String rentDate;

    /**
     * 还车日期
     */
    private String returnDate = "未归还";

    /**
     * 实际租车天数
     */
    private int sjts = 0;

    /**
     * 收入
     */
    private int sr = 0;


    public RentalRecord() {
    }

    public RentalRecord(String carCph, String adminName, String customer, String pid, String tel,
                        int yj, int ts, String rentDate) {
        this.carCph = carCph;
        this.adminName = adminName;
        this.customer = customer;
        this.pid = pid;
        this.tel = tel;
        this.yj = yj;
        this.ts = ts;
        this.rentDate = rentDate;
    }

    public RentalRecord(String carCph, String adminName, String customer, String pid, String tel,
                        int yj, int ts, String rentDate, String returnDate, int sjts, int sr) {
        this.carCph = carCph;
        this.adminName = adminName;
        this.customer = customer;
        this.pid = pid;
        this.tel = tel;
        this.yj = yj;
        this.ts = ts;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.sjts = sjts;
        this.sr = sr;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getYj() {
        return yj;
    }

    public void setYj(int yj) {
        this.yj = yj;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getSjts() {
        return sjts;
    }

    public void setSjts(int sjts) {
        this.sjts = sjts;
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Override
    public String toString() {
        return "RentalRecord{" + "carCph='" + carCph + '\'' + ", adminName='" + adminName +
                '\'' + ", customer='" + customer + '\'' + ", pid='" + pid + '\'' + ", tel='"
                + tel + '\'' + ", yj=" + yj + ", ts=" + ts + ", rentDate='" + rentDate + '\''
                + ", returnDate='" + returnDate + '\'' + ", sjts=" + sjts + ", sr=" + sr + '}';
    }
}
