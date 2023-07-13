package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.ComboItems;
import com.myfood.mapper.ComboItemsMapper;
import com.myfood.service.ComboItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComboItemsServiceImpl extends ServiceImpl<ComboItemsMapper, ComboItems> implements ComboItemsService{
}
