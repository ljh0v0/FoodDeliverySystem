package com.myfood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.common.CustomException;
import com.myfood.dto.ComboDto;
import com.myfood.entity.Combo;
import com.myfood.entity.ComboItems;
import com.myfood.mapper.ComboMapper;
import com.myfood.service.ComboItemsService;
import com.myfood.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComboServiceImpl extends ServiceImpl<ComboMapper, Combo> implements ComboService{

    @Autowired
    private ComboItemsService comboItemsService;

    @Override
    @Transactional
    public void saveWithComboItems(ComboDto comboDto) {

        this.save(comboDto);

        List<ComboItems> comboItems = comboDto.getComboItems();
        comboItems = comboItems.stream().map((item) ->{
            item.setComboId(comboDto.getId());
            return item;
        }).collect(Collectors.toList());

        comboItemsService.saveBatch(comboItems);
    }

    @Override
    @Transactional
    public void removeWithComboItems(List<Long> ids) {
        LambdaQueryWrapper<Combo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Combo::getId, ids);
        queryWrapper.eq(Combo::getStatus, 1);

        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("Cannot delete this combo since it is on stock");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<ComboItems> comboItemsQueryWrapper = new LambdaQueryWrapper<>();
        comboItemsQueryWrapper.in(ComboItems::getComboId, ids);
        comboItemsService.remove(comboItemsQueryWrapper);
    }
}
