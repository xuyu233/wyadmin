package com.wy.config;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SendByEmailTools {

    @Resource
    JavaMailSender jms;

    public boolean send(String sender,String receiver,String title,String text){
        try {
            //建立邮件消息
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            //发送者
            System.out.println("发送者 ------------------"+sender);
            mainMessage.setFrom(sender);
            System.out.println("接收者 ------------------"+receiver);
            //接收者
            mainMessage.setTo(receiver);

            //发送的标题
            mainMessage.setSubject(title);
            //发送的内容
            mainMessage.setText(text);
            jms.send(mainMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
