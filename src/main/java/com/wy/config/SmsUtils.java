package com.wy.config;


import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.tea.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.wy.prop.SmsProperties;
import com.wy.util.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 短信类服务
 */
@Component
@EnableConfigurationProperties(value = SmsProperties.class)
public class SmsUtils {

    @Resource
    private SmsProperties smsProperties;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    //设置前缀
    private static final String KEY_PREFIX = "sms:phone:";
    private static final String KEY_CODE_PREFIX = "sms:code:phone:";
    private static final long SMS_MIN_INTERVAL_IN_MILLIS = 600000; //60秒

   public String sendSms(String phone){
       String key = KEY_PREFIX+phone;
       String keyCode = KEY_CODE_PREFIX+phone;

       String code = "";

       SendSmsResponse sendSmsResponse = null;
       System.out.println("发送短信成功执行到此处！");
       try {

           System.out.println("短信服务key"+smsProperties.getAccessKeyId());
           System.out.println("短信服务密钥"+smsProperties.getAccessKeySecret());

           com.aliyun.dysmsapi20170525.Client client = SmsUtils.createClient(smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
           //控制验证码发送频率
           //生成时间缓存redis
           String keyTime = KEY_PREFIX+phone;
           String time = stringRedisTemplate.opsForValue().get(keyTime);

           //String last = stringRedisTemplate.opsForValue().get(key);
           if (StringUtils.isNotBlank(time)){
               //发送频率过快 拦截此次请求
               System.out.println("短信验证码发送频率过快，已拦截！");
               return "发送频率过快";
           }

           //通过Number工具类获取验证码随机数
           code = NumberUtils.generateCode(6);
           System.out.println("生成的验证码："+code);
           SendSmsRequest sendSmsRequest = new SendSmsRequest()
               .setPhoneNumbers(phone)
               .setSignName(smsProperties.getSignName())
               .setTemplateCode(smsProperties.getVerifyCodeTemplate())
               .setTemplateParam("{\"code\":\"" + code + "\"}");

           //真实发送短信代码 测试时注释 ↓
           sendSmsResponse = client.sendSms(sendSmsRequest);
           System.out.println("sendSmsResponse = " + sendSmsResponse);

           //存储验证码发送时间 到期时间1分钟
           stringRedisTemplate.opsForValue().set(keyTime,String.valueOf(System.currentTimeMillis()),1,TimeUnit.MINUTES);

           //将验证码存储到redis 到期时间5分钟
           stringRedisTemplate.opsForValue().set(keyCode,code,5, TimeUnit.MINUTES);

       } catch (Exception e) {
           e.printStackTrace();
       }
       //return sendSmsResponse.getBody().getCode();
       return code;
   }


}
