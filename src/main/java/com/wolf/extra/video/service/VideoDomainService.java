package com.wolf.extra.video.service;

import java.util.List;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.VideoDomain;

/**
 * 视频域名 服务方法
 * @author tianya
 *
 */
public interface VideoDomainService {

	public VideoDomain load(String domainId) throws VideoException;

	/**
	 * 保存域名
	 * @param domain
	 * @return
	 * @throws VideoException
	 */
	public VideoDomain save(VideoDomain domain) throws VideoException;

	/**
	 * 删除域名
	 * @param domainId
	 * @throws VideoException
	 */
	public void delete(String domainId) throws VideoException;

	/**
	 * 查询域名
	 * @return
	 * @throws VideoException
	 */
	public List<VideoDomain> findAll() throws VideoException;
}
