package com.jt.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
	
	@Autowired
	private CloseableHttpClient htClient;//从池中获取连接
	@Autowired
	private RequestConfig requestConfig; //控制请求超时时间
	
	/**
	 *   目的: 通过制定的url地址,获取服务器数据. String
	 *   参数: 1.url地址   ?key=value
	 *     2.封装用户参数.	 Map<String,String> params
	 *     3.设定字符集编码 charset
	 * 
	 * 请求方式:
	 * 		无参: http://manage.jt.com/xxxxx;
	 * 		有参: http://manage.jt.com/xxxx?key=value&key2=value2&....
	 */
	public String doGet(String url,Map<String,String> params,String charset) {
		
		//1.判断字符集编码是否有值.
		if(StringUtils.isEmpty(charset)) {
			
			charset = "UTF-8";
		}
		
		//2.判断是否有参数.
		if(params != null) {
			url += "?";
			for (Map.Entry<String,String> entry : params.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url += key+"="+value+"&";
			}
			//去除多余的&符.
			url = url.substring(0, url.length()-1);
		}
		
		//3.定义请求的类型
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		
		//4.发起请求获取结果.
		String result = null;
		try {
			CloseableHttpResponse response = htClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==200) {
				
				result = EntityUtils.toString(response.getEntity(),charset);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public String doGet(String url) {
		
		return doGet(url, null, null);
	}
	
	public String doGet(String url,Map<String,String> params) {
		
		return doGet(url, params, null);
	}
	
	public <T> T doGet(String url,Map<String,String> params,Class<T> targetClass,String charset) {
		
		String result = doGet(url,params);
		return ObjectMapperUtil.toObj(result, targetClass);
	}
	
	public <T> T doGet(String url,Class<T> targetClass) {
		
		String result = doGet(url);
		return ObjectMapperUtil.toObj(result, targetClass);
	}
	
}
