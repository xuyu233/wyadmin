package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.DanyuanMapper;
import com.wy.pojo.Room;
import com.wy.mapper.RoomMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Resource
    private DanyuanMapper danyuanMapper;

    @Override
    public Page<Room> getAllRooms(Long page, Long limit) {
        Page<Room> roomPage = baseMapper.selectPage(new Page<>(page, limit), null);
        bindData(roomPage.getRecords());
        return roomPage;
    }

    private void bindData(List<Room> records) {
        records.forEach(room -> {
            room.setDanyuan(danyuanMapper.selectById(room.getDanyuanId()));
        });
    }

    @Override
    public int addRoom(Room room) {
        return baseMapper.insert(room);
    }

    @Override
    public int updateRoom(Room room) {
        return baseMapper.updateById(room);
    }

    @Override
    public int delRoom(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Room> findRoom(Long page, Long limit, String name) {
        Page<Room> roomPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Room>().like(Room::getStatus, name));
        bindData(roomPage.getRecords());
        return roomPage;
    }

    @Override
    public List<Room> getAllFreeRooms(int danyuan_id) {
        return baseMapper.selectList(new LambdaQueryWrapper<Room>().eq(Room::getStatus,0).like(Room::getDanyuanId,danyuan_id));
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public int getFreeCount() {
        return baseMapper.selectCount(new LambdaQueryWrapper<Room>().eq(Room::getStatus,"0"));
    }
}
