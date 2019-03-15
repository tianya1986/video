package com.wolf.extra.video;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class VideoWebAppConfigurer implements WebMvcConfigurer {

	/**
	 * 在配置文件中配置的文件保存路径
	 */
	@Value("${content.service.path.video}")
	private String videRootPath;

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 文件最大KB,MB
		factory.setMaxFileSize(DataSize.ofGigabytes(20));
		// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofGigabytes(1));
		return factory.createMultipartConfig();
	}

	/**
	 * 
	 * 静态资源映射
	 * 
	 * addResourceHandler 资源url路径 addResourceLocations 资源路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (videRootPath.equals("") || videRootPath.equals("${cbs.imagesPath}")) {
			String imagesPath = VideoWebAppConfigurer.class.getClassLoader()
					.getResource("").getPath();
			System.out.print("1.上传配置类videRootPath==" + imagesPath + "\n");
			if (imagesPath.indexOf(".jar") > 0) {
				imagesPath = imagesPath
						.substring(0, imagesPath.indexOf(".jar"));
			} else if (imagesPath.indexOf("classes") > 0) {
				imagesPath = "file:"
						+ imagesPath
								.substring(0, imagesPath.indexOf("classes"));
			}
			imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))
					+ "/images/";
			videRootPath = imagesPath;
		}
		System.out.print("videRootPath=============" + videRootPath + "\n");
		// LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath============="+mImagesPath+"\n");
		registry.addResourceHandler("/resources/video/**")
				.addResourceLocations("file:/" + videRootPath);
		// TODO Auto-generated method stub
		System.out.print("2.上传配置类videRootPath==" + videRootPath + "\n");
	}
}
