package com.wolf.cs.service;

import java.util.List;

import com.wolf.cs.ContentException;
import com.wolf.cs.Dentry;

/**
 * 内容服务方法
 * 
 * 1. 保存文件
 * 2. 读取文件信息
 */
public interface ContentService {

    /**
     * <p>Description:   保存文件          </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param dentry 文件信息
     */
    public void save(Dentry dentry) throws ContentException;

    /**
     * <p>Description:   获取文件信息       </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @param dentryId 文件id
     */
    public Dentry load(String dentryId) throws ContentException;

    /**
     * <p>Description:   查询文件  </p>
     * <p>Create Time: 2019年2月20日   </p>
     * <p>Create author: wolf   </p>
     * @return
     * @throws ContentException
     */
    public List<Dentry> query() throws ContentException;
}
