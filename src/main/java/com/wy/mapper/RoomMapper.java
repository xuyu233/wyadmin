package com.wy.mapper;

import com.wy.pojo.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 */
public interface RoomMapper extends BaseMapper<Room> {


    @Select("SELECT room.id,room.name,room.danyuan_id,room.`status` FROM room,user,user_room WHERE room.id = room_id and user_id = user.id and user_room.outTime is NULL and user_id = #{userId}")
    List<Room> selectRoomByUser(Integer userId);
}
