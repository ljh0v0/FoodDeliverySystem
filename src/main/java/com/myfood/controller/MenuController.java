package com.myfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myfood.common.R;
import com.myfood.dto.MenuDto;
import com.myfood.entity.Category;
import com.myfood.entity.Menu;
import com.myfood.service.CategoryService;
import com.myfood.service.MenuService;
import com.myfood.service.RequirementsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RequirementsService requirementsService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody MenuDto menuDto){
        menuService.saveWithRequirements(menuDto);
        return R.success("Save new items to the menu successfully");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Menu> pageInfo = new Page<>(page, pageSize);
        Page<MenuDto> menuDtoPage = new Page<>();

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Menu::getName, name);
        queryWrapper.orderByDesc(Menu::getUpdateTime);

        menuService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, menuDtoPage, "records");

        List<Menu> records = pageInfo.getRecords();
        List<MenuDto> list = records.stream().map((item) ->{
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(item, menuDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            menuDto.setCategoryName(categoryName);
            return menuDto;
        }).collect(Collectors.toList());

        menuDtoPage.setRecords(list);

        return R.success(menuDtoPage);
    }

    @GetMapping("/{id}")
    public R<MenuDto> getById(@PathVariable Long id){
        MenuDto menuDto = menuService.getByIdWithRequirements(id);
        return R.success(menuDto);
    }

    @PutMapping
    public R<String> update(@RequestBody MenuDto menuDto){
        menuService.updateWithRequirements(menuDto);
        return R.success("Update the menu successfully");
    }

    @GetMapping("/list")
    public R<List<Menu>> List(Menu menu){
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(menu.getCategoryId()!=null, Menu::getCategoryId, menu.getCategoryId());
        queryWrapper.eq(Menu::getStatus, 1);
        queryWrapper.orderByAsc(Menu::getSort).orderByDesc(Menu::getUpdateTime);

        List<Menu> list = menuService.list(queryWrapper);

        return R.success(list);
    }
}
