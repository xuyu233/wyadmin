package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.RoomMapper;
import com.wy.mapper.UserMapper;
import com.wy.pojo.Room;
import com.wy.pojo.UserRoom;
import com.wy.mapper.UserRoomMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.service.UserRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 */
@Service
public class UserRoomServiceImpl extends ServiceImpl<UserRoomMapper, UserRoom> implements UserRoomService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoomMapper roomMapper;

    @Override
    public Page<UserRoom> findRoomRecordById(int id, Long page, Long limit) {
        Page<UserRoom> userRoomPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<UserRoom>().eq(UserRoom::getRoomId, id));
        bindData(userRoomPage.getRecords());
        return userRoomPage;
    }

    private void bindData(List<UserRoom> records) {
        records.forEach(userRoom -> {
            userRoom.setUser(userMapper.selectById(userRoom.getUserId()));
            userRoom.setRoom(roomMapper.selectById(userRoom.getRoomId()));
        });
    }

    @Override
    public int stopRoomByUserId(int id) {
        List<UserRoom> userRooms = baseMapper.selectList(new LambdaQueryWrapper<UserRoom>().eq(UserRoom::getUserId, id).isNull(UserRoom::getOuttime));
        if(userRooms.isEmpty()){
            return 1;
        }
        Room room = new Room();
        room.setId(userRooms.get(0).getRoomId());
        room.setStatus("0");
        roomMapper.updateById(room);
        return baseMapper.update(null,new LambdaUpdateWrapper<UserRoom>().eq(UserRoom::getUserId,id).isNull(UserRoom::getOuttime).set(UserRoom::getOuttime,new Date()));
    }

    @Override
    public int findRoom(Integer userId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<UserRoom>().eq(UserRoom::getUserId, userId).isNull(UserRoom::getOuttime));
    }

    @Override
    public void outRoom(Integer userId) {
        List<UserRoom> userRooms = baseMapper.selectList(new LambdaQueryWrapper<UserRoom>().eq(UserRoom::getUserId, userId).isNull(UserRoom::getOuttime));
        Room room = new Room();
        room.setId(userRooms.get(0).getRoomId());
        room.setStatus("0");
        roomMapper.updateById(room);
        baseMapper.update(null,new LambdaUpdateWrapper<UserRoom>().eq(UserRoom::getUserId,userId).isNull(UserRoom::getOuttime).set(UserRoom::getOuttime,new Date()));
    }

    @Override
    public int fenpei(Integer userId, Integer roomId) {
        Room room = new Room();
        room.setId(roomId);
        room.setStatus("1");
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userId);
        userRoom.setRoomId(roomId);
        userRoom.setIntime(new Date());
        return baseMapper.insert(userRoom);
    }
}
