package com.myfood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.common.CustomException;
import com.myfood.entity.Category;
import com.myfood.entity.Combo;
import com.myfood.entity.Menu;
import com.myfood.mapper.CategoryMapper;
import com.myfood.service.CategoryService;
import com.myfood.service.ComboService;
import com.myfood.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private MenuService menuService;

    @Autowired
    private ComboService comboService;

    @Override
    public void remove(Long id) {
        // if the category is related to some menu items, can't remove
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getCategoryId, id);
        int menuCount = menuService.count(menuLambdaQueryWrapper);
        if (menuCount > 0){
            throw new CustomException("The category can not be deleted since it is related to some menu items.");
        }

        // if the category is related to some combos, can't remove
        LambdaQueryWrapper<Combo> comboLambdaQueryWrapper = new LambdaQueryWrapper<>();
        comboLambdaQueryWrapper.eq(Combo::getCategoryId, id);
        int comboCount = comboService.count(comboLambdaQueryWrapper);
        if (comboCount > 0){
            throw new CustomException("The category can not be deleted since it is related to some combos.");
        }

        super.removeById(id);
    }
}
