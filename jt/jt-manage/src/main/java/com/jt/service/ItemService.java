package com.jt.service;

import java.util.List;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemByPage(int page, int rows);

	int saveItem(Item item,ItemDesc desc);

	int updateItem(Item item,ItemDesc itemdesc);

	void deleteItem(Long[] ids);

	void instock(Long[] ids, Integer status);

	ItemDesc getDesc(Long itemId);

	Item findItemById(Long id);

	ItemDesc findItemDescById(Long itemId);

	
	
}
