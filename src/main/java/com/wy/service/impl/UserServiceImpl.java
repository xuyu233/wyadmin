package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.common.CodecUtils;
import com.wy.config.SendByEmailTools;
import com.wy.config.SmsUtils;
import com.wy.mapper.CarMapper;
import com.wy.mapper.DanyuanMapper;
import com.wy.mapper.RoomMapper;
import com.wy.pojo.Car;
import com.wy.pojo.Room;
import com.wy.pojo.User;
import com.wy.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.prop.EmailProperties;
import com.wy.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
@EnableConfigurationProperties(EmailProperties.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private CarMapper carMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private DanyuanMapper danyuanMapper;

    @Resource
    private SmsUtils smsUtils;

    @Resource
    private EmailProperties emailProperties;

    @Resource
    private SendByEmailTools sendByEmailTools;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis短信验证码前缀
     */
    private static final String KEY_PREFIX = "sms:code:phone:";


    @Override
    public Page<User> getUsers(Long page, Long limit) {
        Page<User> userPage = baseMapper.selectPage(new Page<>(page, limit), null);
        bindData(userPage.getRecords());
        return userPage;
    }

    private void bindData(List<User> records) {
        records.forEach(user -> {
            List<Car> cars = carMapper.selectCarByUser(user.getId());
            if(!cars.isEmpty()){
                user.setCar(cars.get(0));
            }
            List<Room> rooms = roomMapper.selectRoomByUser(user.getId());
            if (!rooms.isEmpty()) {
                rooms.get(0).setDanyuan(danyuanMapper.selectById(rooms.get(0).getDanyuanId()));
                user.setRoom(rooms.get(0));
            }
        });
    }



    @Override
    public int addUser(User user) {
        Integer integer = baseMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getPhone, user.getPhone()));
        if (integer != 0){
            return 0;
        }

        String key= KEY_PREFIX+user.getPhone();
        String cacheCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis内验证码："+cacheCode);
        System.out.println("输入的验证码："+user.getCode());
        if (!StringUtils.equals(user.getCode(),cacheCode)){
            return -1;
        }



        String salt = CodecUtils.generateSalt();
        user.setStatus(1);
        user.setSalt(salt);
        user.setPassword(CodecUtils.md5Hex("123456",salt));
        return baseMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return baseMapper.updateById(user);
    }

    @Override
    public int delUser(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<User> findUser(Long page, Long limit, String name) {
        return baseMapper.selectPage(new Page<>(page,limit),new LambdaQueryWrapper<User>().like(User::getUsername,name));
    }

    @Override
    public User loginByPassword(String phone, String password) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone));
        if(StringUtils.equals(CodecUtils.md5Hex(password,user.getSalt()),user.getPassword())){
            return user;
        }
        return null;
    }

    @Override
    public int updatePass(Integer id, String newPsw) {
        User user = new User();
        user.setId(id);
        User selectUser = baseMapper.selectById(id);

        user.setPassword(CodecUtils.md5Hex(newPsw,selectUser.getSalt()));
        return baseMapper.updateById(user);
    }

    @Override
    public User getUserById(Integer id) {
        User user = baseMapper.selectById(id);
        bindData(Arrays.asList(user));
        return user;
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @Override
    public String sendCode(String phone) {
        String s = smsUtils.sendSms(phone);
        if (StringUtils.isNotBlank(s)){
            return s;
        }
        return s;
    }

    /**
     * 发送提示邮件
     * @param user
     * @return
     */
    @Override
    public boolean sendEmail(User user) {
        //分别获取发件人邮箱 邮件标题 邮件内容
        String sender = emailProperties.getSender();
        String title = emailProperties.getTitle();
        String text = emailProperties.getText();

        System.out.println(title);

        //通过占位符填充邮件内容 （1.用户名 2.手机号）
        String sendText = String.format(text, user.getUsername(),user.getPhone());

        System.out.println("sendText = " + sendText);

        //调用发送邮件工具类 传入发件人 收件人 邮件标题 邮件内容
        boolean result = sendByEmailTools.send(sender, user.getEmail(), title, sendText);

        if (result){
            System.out.println("邮箱发送成功");
        }else{
            System.out.println("邮箱发送失败");
        }
        return false;
    }

    @Override
    public int findUserByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        return baseMapper.selectCount(queryWrapper);
    }

}
