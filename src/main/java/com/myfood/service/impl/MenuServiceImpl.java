package com.myfood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.dto.MenuDto;
import com.myfood.entity.Menu;
import com.myfood.entity.Requirements;
import com.myfood.mapper.MenuMapper;
import com.myfood.service.MenuService;
import com.myfood.service.RequirementsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RequirementsService requirementsService;

    @Transactional
    public void saveWithRequirements(MenuDto menuDto){
        this.save(menuDto);

        Long menuId = menuDto.getId();

        List<Requirements> requirements = menuDto.getRequirements();
        requirements = requirements.stream().map((item)->{
            item.setMenuId(menuId);
            return item;
        }).collect(Collectors.toList());

        requirementsService.saveBatch(requirements);
    }

    @Override
    public MenuDto getByIdWithRequirements(Long id) {
        Menu menu = this.getById(id);

        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(menu, menuDto);

        LambdaQueryWrapper<Requirements> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Requirements::getMenuId, id);
        List<Requirements> requirements = requirementsService.list(queryWrapper);

        menuDto.setRequirements(requirements);

        return menuDto;
    }

    @Override
    public void updateWithRequirements(MenuDto menuDto) {
        this.updateById(menuDto);

        LambdaQueryWrapper<Requirements> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Requirements::getMenuId, menuDto.getId());
        requirementsService.remove(queryWrapper);

        List<Requirements> requirements = menuDto.getRequirements();
        requirements = requirements.stream().map((item)->{
            item.setMenuId(menuDto.getId());
            return item;
        }).collect(Collectors.toList());

        requirementsService.saveBatch(requirements);
    }


}
