package com.myfood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myfood.entity.Combo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComboMapper extends BaseMapper<Combo> {
}
