package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.BasePojo;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper idm;
	@Override
	public EasyUITable findItemByPage(int page, int rows) {
		int total
		  = itemMapper.selectCount(null);
		int start=(page-1)*rows;
		List<Item> items = itemMapper.findItemByPage(start,rows);
		return new EasyUITable(total, items);
		
	}
	//@Override
	public EasyUITable findItemByPage1(int page, int rows) {
		Page<Item> pg = new Page<>(page,rows);
		QueryWrapper<Item> qw = new QueryWrapper<>();
		qw.orderByDesc("updated");
		IPage<Item> ipage = itemMapper.selectPage(pg, qw);
		Integer count = Integer.valueOf(""+ipage.getTotal());
		List<Item> records = ipage.getRecords();
		return new EasyUITable(count, records);
		
	}
	@Override
	@Transactional
	public int saveItem(Item item,ItemDesc desc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		int i = itemMapper.insert(item);
		ItemDesc des = new ItemDesc();
		
		desc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getUpdated());
		idm.insert(desc);
		System.err.println("--------------------------------"+desc);
		return i;
	}
	@Override
	@Transactional
	public int updateItem(Item item,ItemDesc itemdesc) {
		item.setUpdated(new Date());
		UpdateWrapper<Item> wp = new UpdateWrapper<>();
		wp.eq("id", item.getId());
		int l = itemMapper.update(item, wp);
		itemdesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		idm.updateById(itemdesc);
		
		return l;
	}   
	@Override
	@Transactional
	public void deleteItem(Long[] ids) {
		/*
		 * for (Long l : ids) { itemMapper.deleteById(l); }
		 */
		List<Long> li = Arrays.asList(ids);
		itemMapper.deleteBatchIds(li);
		idm.deleteBatchIds(li);
	}
	@Override
	public void instock(Long[] ids, Integer status) {
		Item item = new Item();
		Item s = (Item) item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> wp = new  UpdateWrapper<>();
		wp.in("id", Arrays.asList(ids));
		itemMapper.update(s, wp);
		
	}
	@Override
	public ItemDesc getDesc(Long id) {
		ItemDesc itemDesc= idm.selectById(id);
		return itemDesc;
	}
	@Override
	public Item findItemById(Long id) {
		Item item = itemMapper.selectById(id);
		
		return item;
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc des = idm.selectById(itemId);
		return des;
	}
	
	
	
	
	
}
