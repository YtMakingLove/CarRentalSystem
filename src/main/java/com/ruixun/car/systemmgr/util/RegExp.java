package com.ruixun.car.systemmgr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类，用于验证用户注册时输入的用户名和密码是否合法
 * @author yt
 * @date 2020/3/6 - 20:38
 */
public class RegExp {

    /**
     * 验证输入的用户名是否合法
     * @param userName
     * @return
     */
    public static boolean userNameVerification(String userName){
        //校验正则表达式,用户名必须是6-12位的字母、数字或下划线组成
        String regEx = "[\\w]{6,12}";
        //编译正则表达式，获得表达式对象
        Pattern pattern = Pattern.compile(regEx);
        //校验emailStr是否满足定义的正则表达式的规则
        Matcher matcher = pattern.matcher(userName);
        //返回校验结果，符合返回true，不符合返回false
        return matcher.matches();
    }

    /**
     * 验证密码是否合法
     *
     * @param userPassword
     * @return
     */
    public static boolean userPasswordVerification(String userPassword){
        //校验正则表达式,密码必须是8-20位的字母、数字或下划线组成
        String regEx = "[\\w]{8,20}";
        //编译正则表达式，获得表达式对象
        Pattern pattern = Pattern.compile(regEx);
        //校验emailStr是否满足定义的正则表达式的规则
        Matcher matcher = pattern.matcher(userPassword);
        //返回校验结果，符合返回true，不符合返回false
        return matcher.matches();
    }
}
