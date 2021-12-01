package com.wy.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

/**
 * 加密类
 */
public class CodecUtils {

    /**
     * MD5加密
     *
     * @param data
     * @param salt
     * @return
     */
    public static String md5Hex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    /**
     * sha加密
     *
     * @param data
     * @param salt
     * @return
     */
    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    /**
     * 生成随机盐
     *
     * @return
     */
    public static String generateSalt() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

//    public static void main(String[] args) throws Exception {
//        // System.out.println(generateSalt());
//
//        // System.out.println(md5Hex("123456","6aec1d3b6a734417a1891339337830a1"));
//
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/wuye", "root", "root");
//        PreparedStatement ps = connection.prepareStatement("select * from user");
//        ResultSet rs = ps.executeQuery();
//        int i = 0;
//        while (rs.next()) {
//            PreparedStatement p = connection.prepareStatement("update user set sex=?,salt=?,password=? where id=?");
//            String s = generateSalt();
//            p.setObject(1,i%2==0?0:1);
//            p.setObject(2,s);
//            p.setObject(3,md5Hex(rs.getString(3),s));
//            p.setObject(4,rs.getInt(1));
//            p.executeUpdate();
//            i++;
//        }
}
