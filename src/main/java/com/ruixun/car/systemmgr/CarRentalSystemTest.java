package com.ruixun.car.systemmgr;



import java.io.IOException;
import java.text.ParseException;

/**
 * @author yt
 * @date 2020/3/6 - 20:11
 */
public class CarRentalSystemTest {
    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
        try {
            systemManager.work();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("用户信息读写异常！");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
