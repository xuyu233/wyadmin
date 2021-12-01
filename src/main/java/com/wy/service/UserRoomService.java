package com.wy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.pojo.UserRoom;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 */
public interface UserRoomService extends IService<UserRoom> {

    Page<UserRoom> findRoomRecordById(int id, Long page, Long limit);

    int stopRoomByUserId(int id);

    int findRoom(Integer userId);

    void outRoom(Integer userId);

    int fenpei(Integer userId, Integer roomId);
}
