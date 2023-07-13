package com.myfood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myfood.dto.ComboDto;
import com.myfood.entity.Combo;

import java.util.List;

public interface ComboService extends IService<Combo> {
    public void saveWithComboItems(ComboDto comboDto);
    public void removeWithComboItems(List<Long> ids);
}
