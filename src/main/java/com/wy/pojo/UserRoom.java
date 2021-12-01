package com.wy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("user_room")
public class UserRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户
     */
    private Integer userId;

    /**
     * 房屋
     */
    private Integer roomId;

    /**
     * 入住时间
     */
    @TableField("inTime")
    private Date intime;

    /**
     * 离去时间
     */
    @TableField("outTime")
    private Date outtime;


    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Room room;
}
