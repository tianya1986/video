package com.wolf.extra.video.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.VideoDomain;
import com.wolf.extra.video.service.VideoDomainService;

/**
 * 视频域名管理方法
 * @author tianya
 *
 */
@RestController
@RequestMapping("/domain")
public class VideoDomainController {

	@Autowired
	private VideoDomainService domainService; // 域名服务类

	/**
	 * 查询域名列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<VideoDomain> listDomain(HttpServletRequest request) {
		List<VideoDomain> result = null;
		try {
			result = domainService.findAll();
		} catch (VideoException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除域名
	 * @param domainId
	 * @return
	 */
	@RequestMapping(value = "/{domainId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("domainId") String domainId) {
		try {
			domainService.delete(domainId);
		} catch (VideoException e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}

	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public VideoDomain save(
			@RequestParam(value = "domain", required = true) String domain) {
		VideoDomain videoDomain = new VideoDomain();
		videoDomain.setDomain(domain);
		videoDomain.setDomainId(UUID.randomUUID().toString()
				.replaceAll("-", ""));
		videoDomain.setCreateTime(System.currentTimeMillis());
		try {
			return domainService.save(videoDomain);
		} catch (VideoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
