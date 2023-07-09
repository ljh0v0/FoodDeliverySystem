package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.Combo;
import com.myfood.mapper.ComboMapper;
import com.myfood.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComboServiceImpl extends ServiceImpl<ComboMapper, Combo> implements ComboService{
}
