package com.wolf.extra.video.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wolf.extra.video.database.entity.User;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.extra.video.service.VideoService;

@RestController
@RequestMapping("/video")
public class VideoController {

	@Autowired
	private VideoService videoService;

	@Value("${cbs.imagesPath}")
	private String mImagesPath;

	@RequestMapping("/play")
	public ModelAndView getUser() {
		return new ModelAndView("/video/play");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Video> listUser(Model model) {
		List<Video> list = videoService.findAll();
		model.addAttribute("videos", list);
		return list;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView goUpdate() {
		return new ModelAndView("video/upload");
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return "file is empty";
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 文件存储路径
		String filePath = mImagesPath
				+ UUID.randomUUID().toString().replaceAll("-", "") + "_"
				+ fileName;
		// logger.info("save file to:" + filePath);
		File dest = new File(filePath);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);

			Video video = new Video();
			video.setName(dest.getName());
			video.setPath(filePath);
			video.setLength(file.getSize());
			videoService.save(video);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

}
