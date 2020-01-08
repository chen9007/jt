package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.Image;



@Service
@PropertySource("classpath:/Image.properties")
public class FileServiceImpl implements FileService{
				
				
		@Value("${image.localDir}")
		private String localDir;
		@Value("${image.httpUrl}")
		private String  httpUrl;
		String url;
		int h;
		int w;
								
				
	@Override
	public Image upload(MultipartFile fil) {
		String finalPath;
		String ss;
		String dirPath;
		
		String name0 = fil.getOriginalFilename();
		String name = name0.toLowerCase();
		if (!name.matches("^.+\\.(png|jpg|jpeg|gif)$")) {
			return Image.fail();
		}
		try {
			System.out.println(222);
			BufferedImage r= ImageIO.read(fil.getInputStream());
			System.out.println(r);
			 h = r.getHeight();
			 w = r.getWidth();
			 System.out.println(h+"----------//////------------"+w);
			if (h==0||h==0) {
				return Image.fail();
			}
			
			String s=new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
			 dirPath=localDir +s;
			File file = new File(dirPath);
			if (!file.exists()) {
				file.mkdirs();
				
			}
			String string = UUID.randomUUID().toString();
			int index=name0.lastIndexOf(".");
			String type=name.substring(index);
			 ss=string+type;
			 finalPath=dirPath+ss;
			fil.transferTo(new File(finalPath));
			//url="http://localhost:8091/"+ss;
			      
			url=httpUrl+s+ss;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(finalPath);
		System.err.println(url);
		return new Image(0,url, w, h);	
	}

}
