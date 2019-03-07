package com.wolf.extra.video.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wolf.common.utils.FileUtil;
import com.wolf.cs.CSConfig;
import com.wolf.cs.ContentException;
import com.wolf.cs.entity.Dentry;
import com.wolf.cs.service.ContentService;
import com.wolf.extra.video.Status;
import com.wolf.extra.video.VideoException;
import com.wolf.extra.video.database.entity.Video;
import com.wolf.extra.video.database.entity.VideoDomain;
import com.wolf.extra.video.entity.ShortURL;
import com.wolf.extra.video.service.ShortURLService;
import com.wolf.extra.video.service.VideoDomainService;
import com.wolf.extra.video.service.VideoService;
import com.wolf.paging.Result;

/**
 * 视频
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    private Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private ContentService contentService; // cs服务

    @Autowired
    private VideoService videoService; // 视频服务

    @Autowired
    private ShortURLService shortURLService; // 短地址服务

    @Autowired
    private VideoDomainService domainService; // 域名服务

    @Value("${content.service.path.video}")
    private String mVideoPath; // 视频地址

    /**
     * 获取视频详情
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/{videoId}", method = RequestMethod.GET)
    public Video getVideo(@PathVariable("videoId") String videoId) {
        logger.info("Get video start, video id = " + videoId);
        Video video = null;
        try {
            video = videoService.load(videoId);
            if (video != null) {
                Dentry dentry = contentService.load(video.getDentryId());
                video.setDentry(dentry);
            }
        } catch (VideoException e) {
            e.printStackTrace();
            logger.error("Get video error, " + e);
        } catch (ContentException e) {
            e.printStackTrace();
            logger.error("Get video error, " + e);
        }
        return video;
    }

    @RequestMapping(value = "/{videoId}", method = RequestMethod.DELETE)
    public String deleteVideo(@PathVariable("videoId") String videoId) {
        logger.info("Delete video start, video id = " + videoId);
        try {
            boolean result = videoService.delete(videoId);
            if (result) {
                return "success";
            }
        } catch (VideoException e) {
            e.printStackTrace();
            logger.error("Get video error, " + e);
        }
        return "fail";
    }

    /**
     * 上架
     * @param dentryId
     * @return
     */
    @RequestMapping(value = "/{videoId}/action/onsale", method = RequestMethod.POST)
    public Video onSale(HttpServletRequest request, @PathVariable("videoId") String videoId,
            @RequestParam(value = "price", required = true) float price,
            @RequestParam(value = "domainId", required = true) String domainId) {
        logger.debug("Update order start. ");
        logger.debug("videoId = " + videoId);
        logger.debug("price = " + price);
        logger.debug("domainId = " + domainId);

        Video video = null;
        try {
            video = videoService.load(videoId);
            if (video != null) {
                VideoDomain domain = domainService.load(domainId);
                InetAddress address = null;
                try {
                    address = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                String hostAddress = address.getHostAddress();
                if (domain != null) {
                    hostAddress = domain.getDomain();
                }
                logger.debug("hostAddress = " + hostAddress);

                String videoUrl = "http://" + hostAddress + "/manager/order.html?videoId=" + video.getVideoId();
                // ShortURL shortURL = shortURLService.getShortURL(videoUrl);
                String shhortUrl = videoUrl;

                logger.debug("videoUrl = " + videoUrl);
                if (price == 0) {
                    video.setStatus(Status.ON_FREE); // 免费
                    video = videoService.onSaleFree(videoId, shhortUrl, hostAddress);
                }
                else {
                    video.setStatus(Status.ON_SALE); // 收费
                    video = videoService.onSale(videoId, shhortUrl, price, hostAddress);
                }
            }
        } catch (VideoException e) {
            e.printStackTrace();
            logger.error("Create order error, " + e);
        }
        return video;
    }

    /**
     * 下架
     * @param dentryId
     * @return
     */
    @RequestMapping(value = "/{videoId}/action/offsale", method = RequestMethod.POST)
    public Video offSale(@PathVariable("videoId") String videoId) {
        Video video = null;
        try {
            video = videoService.load(videoId);
            if (video != null) {
                video = videoService.offSale(videoId);
            }
        } catch (VideoException e) {
            e.printStackTrace();
        }
        return video;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<Video> listUser(HttpServletRequest request,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "status", required = false) String status) {
        logger.info("Query video list execute, offset = " + offset);
        logger.info("Query video list execute, limit = " + limit);
        Result<Video> result = null;
        try {
            result = videoService.query(offset, limit, status);
        } catch (VideoException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "file is empty";
        }

        // 存储视频文件到CS服务器
        String fileName = file.getOriginalFilename(); // 文件名
        String dentryId = UUID.randomUUID().toString().replaceAll("-", ""); // 文件UUID
        int fileType = CSConfig.DentryType.FILE; // 文件类型
        long fileSize = file.getSize(); // 文件大小
        String contentType = file.getContentType(); // mime
        String suffix = FileUtil.getSuffix(fileName); // 拓展名
        String filePath = mVideoPath + File.separator + dentryId + "_" + fileName; // 文件存储路径

        File target = new File(filePath);
        try {
            FileUtil.transferTo(file, target); // 保存文件

            Dentry dentry = new Dentry();
            dentry.setName(fileName); // 文件名
            dentry.setDentryId(dentryId); // 文件UUID
            dentry.setType(fileType); // 文件类型
            dentry.setMime(contentType); // 拓展名
            dentry.setSuffix(suffix); // 拓展名
            dentry.setSize(fileSize); // 文件大小
            dentry.setCreateTime(System.currentTimeMillis()); // 创建时间
            dentry.setPath(filePath); // 文件存储路径

            dentry = contentService.save(dentry);

            Video video = new Video();
            video.setDentryId(dentryId); // 文件UUID
            video.setVideoId(UUID.randomUUID().toString().replaceAll("-", ""));
            video.setName(fileName);
            video.setPrice(0f);
            video.setDomain("");
            video.setStatus(Status.OFF_SALE); // 默认：下架
            video.setCreateTime(System.currentTimeMillis()); // 创建时间
            videoService.save(video);

            return "success";
        } catch (IllegalStateException e) {// 文件存储失败
            e.printStackTrace();
        } catch (IOException e) {// 文件存储失败
            e.printStackTrace();
        } catch (ContentException e) {// dentry存储失败
            e.printStackTrace();
        } catch (VideoException e) { // 保存视频失败
            e.printStackTrace();
        }
        return "fail";
    }

}
