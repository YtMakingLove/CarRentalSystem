package com.ruixun.car.systemmgr.carmgr.car;


import java.time.LocalDate;
import java.util.Date;

/**
 * 汽车类
 *
 * Car类是对系统中租赁的汽车的抽象，该类是一个抽象类，包含了一系列汽车的属性，
 * 还包含一个根据日租金计算租赁费用的方法。
 * Car的信息都存储在data\car.txt中
 * @author yt
 * @date 2020/3/6 - 17:45
 */
public abstract class Car {
    public static final String TYPE_ZAIKECHE = "载客车";
    public static final String TYPE_ZAIHUOCHE = "载货车";
    public static final String STATE_BAOFEI = "报废";
    public static final String STATE_KONGXIAN = "空闲";
    public static final String STATE_YICHUZU = "已出租";

    /**
     * 车牌号
     */
    private String id;

    /**
     * 发动机号
     */
    private String engine;

    /**
     * 车名
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

    /**
     * 出厂日期
     */
    private LocalDate product_date;

    /**
     * private int miles
     */
    private int miles;

    /**
     * 燃油类型
     */
    private String oil_type;

    /**
     * 租车押金
     */
    private int rent_security;

    /**
     * 日租金
     */
    private int rent_money_day;

    /**
     * 状况（空闲，已出租，报废）
     */
    private String state;

    /**
     * 根据日租金以及出租天数来计算基本租金
     * @param days 实际用车天数
     * @return
     */
    public int calcRental(int days){
        return rent_money_day * days;
    }

    public Car() {
    }

    public Car(String id, String engine, String name, String color, LocalDate product_date, int miles,
               String oil_type, int rent_security, int rent_money_day, String state) {
        this.id = id;
        this.engine = engine;
        this.name = name;
        this.color = color;
        this.product_date = product_date;
        this.miles = miles;
        this.oil_type = oil_type;
        this.rent_security = rent_security;
        this.rent_money_day = rent_money_day;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getProduct_date() {
        return product_date;
    }

    public void setProduct_date(LocalDate product_date) {
        this.product_date = product_date;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public String getOil_type() {
        return oil_type;
    }

    public void setOil_type(String oil_type) {
        this.oil_type = oil_type;
    }

    public int getRent_security() {
        return rent_security;
    }

    public void setRent_security(int rent_security) {
        this.rent_security = rent_security;
    }

    public int getRent_money_day() {
        return rent_money_day;
    }

    public void setRent_money_day(int rent_money_day) {
        this.rent_money_day = rent_money_day;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == Car.class){
            Car car = (Car) obj;
            return car.getId().equals(this.getId());
        }
        if (obj == this){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public String toString() {
        return "车牌号：" + id + "，发动机号：" + engine + "，车名：" + name + "，颜色：" + color +
                "，出厂日期：" + product_date + "，行驶里程：" + miles + "，燃油类型：" + oil_type +
                "，租车押金：" + rent_security + "，日租金：" + rent_money_day;
    }
}
