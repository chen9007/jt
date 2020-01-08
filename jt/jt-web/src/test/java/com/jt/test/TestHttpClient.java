package com.jt.test;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	
	/*302已缓存，400参数异常，406参数转化异常*/
	
		@Autowired
		private CloseableHttpClient htClient;//池中获取请求
		@Autowired
		private RequestConfig  conf;//控制请求时间
		@Test
		public void testGet() throws Exception {
			String url="http://manage.jt.com/web/item/findItemById?itemId=1474391991";
			HttpGet get=new HttpGet(url);
			HttpClient client=HttpClients.createDefault();
			HttpResponse response = client.execute(get);
			int code = response.getStatusLine().getStatusCode();
			if (200==code) {
				String result=EntityUtils.toString(response.getEntity());
				System.out.println(result);
				
			}
		}
		@Test
		public void test02() throws Exception {
			String url="http://manage.jt.com/web/item/findItemDescById?itemId=1474391991";
			HttpGet get=new HttpGet(url);
			get.setConfig(conf);
			//HttpClient client=HttpClients.createDefault();
			HttpResponse response =htClient.execute(get);
			int code = response.getStatusLine().getStatusCode();
			if (200==code) {
				String result=EntityUtils.toString(response.getEntity());
				System.out.println(result);
				
			}
		}
		@Autowired
		HttpClientService cli;
		@Test
		public void test03() throws Exception {
			String url="http://manage.jt.com/web/item/findItemDescById";
			HashMap<String, String> mp = new HashMap<String,String>();
			mp.put("itemId", "1474391991");
			String result = cli.doGet(url, mp, null);
			System.err.println(result);
	
		}
		
		
}
