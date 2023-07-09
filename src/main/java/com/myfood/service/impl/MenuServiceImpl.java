package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.Menu;
import com.myfood.mapper.MenuMapper;
import com.myfood.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
