package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
		private Integer status;
		private String  msg;
		private Object  data;
		
		
		public static SysResult success() {
			return new SysResult(200,"恭喜您,成功啦!",null);
		}
		public static SysResult success(Object data) {
			return new SysResult(200,"恭喜您,成功啦!",data);
		}
		public static SysResult success(String msg,Object obj) {
			return new SysResult(200,"恭喜您,成功啦!",obj);
		}
		public static SysResult fail(){
			return new SysResult(201,"哎呀!执行失败",null);
		}
}
