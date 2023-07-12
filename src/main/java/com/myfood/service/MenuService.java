package com.myfood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myfood.dto.MenuDto;
import com.myfood.entity.Menu;

public interface MenuService extends IService<Menu> {
    public void saveWithRequirements(MenuDto menuDto);
    public MenuDto getByIdWithRequirements(Long id);
    public void updateWithRequirements(MenuDto menuDto);
}
