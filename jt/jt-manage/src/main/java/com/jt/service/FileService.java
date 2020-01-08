package com.jt.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.Image;

public interface FileService {

	Image upload(MultipartFile fileImage);

}
