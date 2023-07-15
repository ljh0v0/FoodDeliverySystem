package com.myfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myfood.common.R;
import com.myfood.dto.ComboDto;
import com.myfood.entity.Category;
import com.myfood.entity.Combo;
import com.myfood.service.CategoryService;
import com.myfood.service.ComboItemsService;
import com.myfood.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/combo")
@Slf4j
public class ComboController {

    @Autowired
    private ComboService comboService;

    @Autowired
    private ComboItemsService comboItemsService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody ComboDto comboDto) {
        comboService.saveWithComboItems(comboDto);
        return R.success("Save new combo successfully");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Combo> pageInfo = new Page<>(page, pageSize);
        Page<ComboDto> comboDtoPage = new Page<>();

        LambdaQueryWrapper<Combo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Combo::getName, name);
        queryWrapper.orderByDesc(Combo::getUpdateTime);

        comboService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, comboDtoPage, "records");
        List<Combo> records = pageInfo.getRecords();

        List<ComboDto> list = records.stream().map((item) -> {
            ComboDto comboDto = new ComboDto();
            BeanUtils.copyProperties(item, comboDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                comboDto.setCategoryName(categoryName);
            }
            return comboDto;
        }).collect(Collectors.toList());

        comboDtoPage.setRecords(list);

        return R.success(comboDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        comboService.removeWithComboItems(ids);

        return R.success("Delete combo successfully");
    }

    @GetMapping("/list")
    public R<List<Combo>> list(Combo combo) {
        LambdaQueryWrapper<Combo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(combo.getCategoryId() != null, Combo::getCategoryId, combo.getCategoryId());
        queryWrapper.eq(combo.getStatus() != null, Combo::getStatus, combo.getStatus());
        queryWrapper.orderByDesc(Combo::getUpdateTime);

        List<Combo> list = comboService.list(queryWrapper);

        return R.success(list);
    }
}
