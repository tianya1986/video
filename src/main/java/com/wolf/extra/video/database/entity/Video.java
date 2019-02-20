package com.wolf.extra.video.database.entity;

/**
 * 视频信息
 */
public class Video {

    @SuppressWarnings("unused")
    private String _id;

    /**
     * 视频id （UUID）
     */
    private String videoId;

    /**
     * 文件id
     */
    private String dentryId;

    /**
     * 视频名称
     */
    private String name;

    /**
     * 价格
     */
    private float price;

    /**
     * 短地址
     */
    private String shortURL;

    /**
     * 视频状态
     */
    private int status;

    /**
     * 视频创建时间
     */
    private long createTime;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


}
