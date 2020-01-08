package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jt.annotation.CacheAs;
import com.jt.mapper.ItemCatMapper;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import com.jt.vo.ItemCat;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
			@Autowired
			ItemCatMapper mapper;
			@Autowired(required = false)
			private Jedis jedis;
			@Override
			public ItemCat findCatById(Long itemCatId) {
				
				return mapper.selectById(itemCatId);
			}
			@Override
			public List<ItemCat> findItemCatList(Long parentId) {
				
				
				QueryWrapper<ItemCat> qw = new QueryWrapper<>();
				qw.eq("parent_id", parentId);
				List<ItemCat> sl = mapper.selectList(qw);
				return sl;
			}
			@Override
			@CacheAs
			public List<EasyUITree> findItemCat(Long parentId) {
				List<ItemCat> catList = findItemCatList(parentId);
				List<EasyUITree> treeList = new ArrayList<>(catList.size());
				for (ItemCat it : catList) {
					Long id = it.getId();
					
					String state = it.getIsParent()?"closed":"open";
					String text = it.getName();
					EasyUITree tree = new EasyUITree(id, text, state);
					treeList.add(tree);
					
				}
				return treeList;
			}
	
			@Override
			public List<EasyUITree> findItemCatCache(Long parentId) {
				
				String json;
				List<EasyUITree> treeli = new ArrayList<>();
				String key="ITEMCAT::"+parentId;
				
				String value=jedis.get(key);
				if (StringUtils.isEmpty(value)) { 
					treeli = findItemCat(parentId);
				    json =ObjectMapperUtil.toJson(treeli);
				    jedis.set(key, json);
				    System.err.println("我是第一次!!");
					}else {
	
						treeli=ObjectMapperUtil.toObj(value, treeli.getClass());
						
						System.err.println("我是老司机!");
						
					}
				//jedis.flushAll();
				return treeli;
			}

}
