package com.ruixun.car.systemmgr.carmgr.car.cartype;

import com.ruixun.car.systemmgr.carmgr.car.Car;

import java.time.LocalDate;
import java.util.Date;

/**
 * 货车
 *
 * Truck类是对系统中租赁的“载货车的抽象”，该类继承了Car类，增加了一个载货数weight属性，
 * 还重写了父类中计算租金的方法，除了父类中基本的天数乘以日租金的基本租金外，
 * 还根据载货吨数来额外增加租金，规则如下：
 *
 * 1吨及1吨以下，没有额外租金
 * 1吨以上到3吨以下，增收基本租金的50%
 * 3吨以上到5吨以下，增收基本租金的100%
 * 5吨以上到10吨以下，增收基本租金的200%
 * 10吨以上，增收基本租金的400%
 *
 * @author yt
 * @date 2020/3/6 - 17:48
 */
public class Truck extends Car {

    /**
     * 载货数
     */
    private int weight;

    public Truck() {
    }

    public Truck(String id, String engine, String name, String color, LocalDate product_date, int miles,
                 String oil_type, int rent_security, int rent_money_day, String state, int weight) {
        super(id, engine, name, color, product_date, miles, oil_type, rent_security, rent_money_day, state);
        this.weight = weight;
    }

    /**
     * 基本租金+额外租金
     * @param days
     * @return
     */
    @Override
    public int calcRental(int days){
        if (weight > 1 && weight < 3){
            return (int) ((super.calcRental(days) * 0.5) + super.calcRental(days));
        }
        if (weight >= 3 && weight < 5){
            return  ((super.calcRental(days) * 1) + super.calcRental(days));
        }
        if (weight >= 5 && weight < 10){
            return  ((super.calcRental(days) * 2) + super.calcRental(days));
        }
        if (weight >= 10){
            return  ((super.calcRental(days) * 4) + super.calcRental(days));
        }
        return super.calcRental(days);
    }




    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == Truck.class){
            Truck truck = (Truck) obj;
            return truck.getId().equals(this.getId());
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
        return super.toString() + "，载货量：" + weight + "，状况：" + super.getState();

    }
}
