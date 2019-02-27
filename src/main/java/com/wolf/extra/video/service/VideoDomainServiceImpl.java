package com.wolf.extra.video.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.dao.VideoDomainDao;
import com.wolf.extra.video.database.entity.VideoDomain;

/**
 * 视频服务方法
 * @author tianya
 */
@Service("videoDomainService")
public class VideoDomainServiceImpl implements VideoDomainService {

	@Autowired
	private VideoDomainDao domainDao;

	@Override
	public VideoDomain load(String domainId) throws VideoException {
		return domainDao.load(domainId);
	}

	@Override
	public VideoDomain save(VideoDomain domain) throws VideoException {
		return domainDao.save(domain);
	}

	@Override
	public void delete(String domainId) throws VideoException {
		domainDao.delete(domainId);
	}

	@Override
	public List<VideoDomain> findAll() throws VideoException {
		return domainDao.findAll();
	}

}
