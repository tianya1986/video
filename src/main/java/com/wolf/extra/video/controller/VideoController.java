package com.wolf.extra.video.controller;

import com.wolf.extra.video.database.entity.User;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.extra.video.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController {

	@Autowired
	private VideoService videoService;

	@RequestMapping("/{id}")
	public String getUser(@PathVariable Integer id, Model model) {
		model.addAttribute("user", new User(id, "张三", 20, "中国广州"));
		return "/video/detail";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listUser(Model model) {
		List<Video> list = videoService.findAll();
		model.addAttribute("videos", list);
		return "video/list";
	}

}
