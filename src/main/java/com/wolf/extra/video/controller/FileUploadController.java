package com.wolf.extra.video.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return "file is empty";
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 文件存储路径
		String filePath = "D:/data/"
				+ UUID.randomUUID().toString().replaceAll("-", "") + "_"
				+ fileName;
//		logger.info("save file to:" + filePath);
		File dest = new File(filePath);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
}
