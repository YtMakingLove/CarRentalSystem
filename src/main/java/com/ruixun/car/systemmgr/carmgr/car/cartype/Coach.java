package com.ruixun.car.systemmgr.carmgr.car.cartype;

import com.ruixun.car.systemmgr.carmgr.car.Car;

import java.time.LocalDate;
import java.util.Date;

/**
 * 载客车
 *
 * Coach类是对系统中租赁的“载客车的抽象”，该类继承了Car类，
 * 增加了一个载客数seatCount属性，还重写了父类中计算租金的方法，
 * 除了父类中基本的天数乘以日租金的基本租金外，还根据载客人数来额外增加租金，规则如下：
 *
 * 4座及4座以下，没有额外租金
 * 5座到7座，增收基本租金的20%
 * 8座到22座，增收基本租金的100%
 * 23座及以上，增收基本租金的200%
 *
 * @author yt
 * @date 2020/3/6 - 17:47
 */
public class Coach extends Car {
    /**
     * 载客数
     */
    private int seatCount;

    public Coach() {
    }

    public Coach(String id, String engine, String name, String color, LocalDate product_date, int miles,
                 String oil_type, int rent_security, int rent_money_day, String state, int seatCount) {
        super(id, engine, name, color, product_date, miles, oil_type, rent_security, rent_money_day, state);
        this.seatCount = seatCount;
    }

    /**
     * 基本租金+额外租金
     * @param days
     * @return
     */
    @Override
    public int calcRental(int days){
        if (seatCount >= 5 && seatCount <= 7){
            return (int) ((super.calcRental(days) * 0.2) + super.calcRental(days));
        }
        if (seatCount >= 8 && seatCount <= 22){
            return  ((super.calcRental(days) * 1) + super.calcRental(days));
        }
        if (seatCount >= 23){
            return  ((super.calcRental(days) * 2) + super.calcRental(days));
        }
        return super.calcRental(days);
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == Coach.class){
            Coach coach = (Coach) obj;
            return coach.getId().equals(this.getId());
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
        return super.toString() + "，载客数：" + seatCount + "，状况：" + super.getState();
    }
}
