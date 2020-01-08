package com.jt.service;

import java.util.List;

import com.jt.vo.EasyUITree;
import com.jt.vo.ItemCat;

public interface ItemCatService {

	ItemCat findCatById(Long itemCatId);

	List<EasyUITree> findItemCat(Long parentId);

	List<ItemCat> findItemCatList(Long parentId);

	List<EasyUITree> findItemCatCache(Long parentId);

}
