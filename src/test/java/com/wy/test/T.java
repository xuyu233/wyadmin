package com.wy.test;



import org.junit.Test;


public class T {

    @Test
    public void testFormat() {
        String str = "亲爱的%s,感谢您注册使用智慧管家，您的账号是：%s,您的账号密码默认为：123456，请尽快前往首页（http://120.27.136.9）进行修改哟。";

        String username = "xuyu233";
        String phone = "18871206860";

        String format = String.format(str, username, phone);
        System.out.println("format = " + format);


    }



}
