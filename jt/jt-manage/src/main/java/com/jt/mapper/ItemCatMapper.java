package com.jt.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.vo.ItemCat;

public interface ItemCatMapper extends BaseMapper<ItemCat>{
    @Select("")
	void selectCatById();

}
