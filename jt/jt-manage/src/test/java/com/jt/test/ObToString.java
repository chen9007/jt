package com.jt.test;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class ObToString {
		private static final ObjectMapper mp=new ObjectMapper();
	
	@Test
	public void testOB() throws Exception {
		ItemDesc des = new ItemDesc();
		des.setItemId(1L).setItemDesc("hello !").setCreated(new Date()).setUpdated(new Date());
		String des1 = mp.writeValueAsString(des);
		System.err.println(des1);
		ItemDesc d = mp.readValue(des1,ItemDesc.class);
		System.out.println(d.toString());
		
		
	}
	@Test
	public void testLi() throws Exception {
		ItemDesc des1 = new ItemDesc();
		des1.setItemId(1L).setCreated(new Date()).setUpdated(new Date());
		
		ItemDesc des2 = new ItemDesc();
		des2.setItemId(1L).setCreated(new Date()).setUpdated(new Date());
		ArrayList<Object> li = new ArrayList<>();
		li.add(des1);
		li.add(des2);
		String dv = mp.writeValueAsString(li);
		System.err.println(dv);
		 ArrayList lil = mp.readValue(dv,li.getClass());
		System.out.println(lil.toString());
		
		
		
	}
	
	
}
