package com.jt.util;

import java.io.IOException;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
		private static final ObjectMapper mapper=new ObjectMapper();
		public static String toJson(Object obj) {
			String json=null;
			try {
			 json = mapper.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			return json;
		}
		public static <T> T toObj(String json,Class<T> targetClass) {
			T t=null;
			try {
			
					t = mapper.readValue(json,targetClass);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			return  t;
		}
	public static <T> T toObject(String json,Class<T> targetClass) {

			T t = null;
			try {
				t = mapper.readValue(json, targetClass);
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			return t;
			
		}
		
		
}
