package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.mapper.AdminMapper;
import com.wy.pojo.Gonggao;
import com.wy.mapper.GonggaoMapper;
import com.wy.service.GonggaoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 */
@Service
public class GonggaoServiceImpl extends ServiceImpl<GonggaoMapper, Gonggao> implements GonggaoService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Page<Gonggao> getAllGonggaos(Long page, Long limit) {
        Page<Gonggao> gonggaoPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Gonggao>().orderByDesc(Gonggao::getUpdatetime));
        bindData(gonggaoPage.getRecords());
        return gonggaoPage;
    }

    private void bindData(List<Gonggao> records) {
        records.forEach(gonggao -> {
            gonggao.setCreateadmin(adminMapper.selectById(gonggao.getCreateby()));
            gonggao.setUpdateadmin(adminMapper.selectById(gonggao.getUpdateby()));
        });
    }

    @Override
    public List<Gonggao> getAllShowGonggaos() {
        return baseMapper.selectList(new LambdaQueryWrapper<Gonggao>().like(Gonggao::getStatus,0).orderByDesc(Gonggao::getUpdatetime));
    }

    @Override
    public int addGonggao(Gonggao gonggao) {
        return baseMapper.insert(gonggao);
    }

    @Override
    public int updateGonggao(Gonggao gonggao) {
        return baseMapper.updateById(gonggao);
    }

    @Override
    public int delGonggao(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Gonggao> findGonggao(Long page, Long limit, String name) {
        Page<Gonggao> gonggaoPage = baseMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Gonggao>().like(Gonggao::getTitle,name).orderByDesc(Gonggao::getUpdatetime));
        bindData(gonggaoPage.getRecords());
        return gonggaoPage;
    }

    @Override
    public Gonggao getGonggao() {
        List<Gonggao> gonggaos = baseMapper.selectList(new LambdaQueryWrapper<Gonggao>().like(Gonggao::getStatus, 0).orderByDesc(Gonggao::getUpdatetime));
        if (!gonggaos.isEmpty()){
            return gonggaos.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return baseMapper.selectCount(null);
    }
}
