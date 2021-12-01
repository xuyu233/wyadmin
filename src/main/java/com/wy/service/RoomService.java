package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 
 */
public interface RoomService extends IService<Room> {

    Page<Room> getAllRooms(Long page, Long limit);

    int addRoom(Room room);

    int updateRoom(Room room);

    int delRoom(int id);

    Page<Room> findRoom(Long page, Long limit, String name);

    List<Room> getAllFreeRooms(int danyuan_id);

    int getCount();

    int getFreeCount();
}
