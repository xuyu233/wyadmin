package com.wy.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@ConfigurationProperties("wy.email")
public class EmailProperties {
    private String sender; //发送人邮箱
    private String title; //邮件标题
    private String text; //邮件内容

}
