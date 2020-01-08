package com.jt.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.Image;
@RestController
public class FileController {
			@RequestMapping("/file")
			public String file(MultipartFile fileImage) throws  Exception {
				System.out.println(1111111111);
				File file2 = new File("d:/images/");
				if (!file2.exists()) {
					file2.mkdirs();
				}  
				String name = fileImage.getOriginalFilename();
				String filePath="d:/images/"+name;
				fileImage.transferTo(new File(filePath));
				System.out.println("上传成功");
				return "successfulwww!!!!";
			}
			@Autowired
			private FileService fileService;
			@RequestMapping("/pic/upload")
			public Image upload(MultipartFile uploadFile) throws  Exception {
				System.out.println(111);
				Image up = fileService.upload(uploadFile);
				
				
				return up;
			}
}
