package com.wolf.cs;

import java.io.Serializable;

/**
 * <p>Title: 视频对象        </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2019     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2019年2月20日           </p>
 * @author Administrator
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class Dentry implements Serializable {

    private static final long serialVersionUID = -4822035542190053356L;

    private String _id;

    /**
     * 目录项id（UUID）
     */
    private String dentryId;

    /**
     * 目录项名（对象一般包括扩展名）
     */
    private String name;

    /**
     * 目录项路径（对象包括扩展名）
     */
    private String path;

    /**
     * 类型
     * 0=目录 1=对象 2=连接对象
     */
    private int type;

    /**
     * 文件长度
     */
    private long size;

    /**
     * MIME
     */
    private String mime;

    /**
     * 扩展名(小写)
     */
    private String suffix;

    /**
     * 创建时间
     */
    private long createTime;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDentryId() {
        return dentryId;
    }

    public void setDentryId(String dentryId) {
        this.dentryId = dentryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
