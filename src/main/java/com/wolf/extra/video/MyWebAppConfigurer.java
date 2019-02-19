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
public class MyWebAppConfigurer implements WebMvcConfigurer {

	/**
	 * 在配置文件中配置的文件保存路径
	 */
	@Value("${cbs.imagesPath}")
	private String mImagesPath;

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
		registry.addResourceHandler("/resources/video/**")
				.addResourceLocations("classpath:/static/video/");
		registry.addResourceHandler("/resources/bootstrap/4.0.0/**")
				.addResourceLocations("classpath:/static/bootstrap/4.0.0/");
		registry.addResourceHandler("/resources/jquery/3.2.1/**")
				.addResourceLocations("classpath:/static/jquery/3.2.1/");
		registry.addResourceHandler("/resources/jquery/3.3.1/**")
				.addResourceLocations("classpath:/static/jquery/3.3.1/");
		registry.addResourceHandler("/resources/popper/1.12.9/**")
				.addResourceLocations("classpath:/static/popper/1.12.9/");
		registry.addResourceHandler("/manager/**")
				.addResourceLocations("classpath:/static/manager/");
		registry.addResourceHandler("/resources/requirejs/**")
		.addResourceLocations("classpath:/static/requirejs/");

		if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
			String imagesPath = MyWebAppConfigurer.class.getClassLoader()
					.getResource("").getPath();
			System.out.print("1.上传配置类imagesPath==" + imagesPath + "\n");
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
			mImagesPath = imagesPath;
		}
		System.out.print("imagesPath=============" + mImagesPath + "\n");
		// LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath============="+mImagesPath+"\n");
		registry.addResourceHandler("/resources/images/**")
				.addResourceLocations("file:/" + mImagesPath);
		// TODO Auto-generated method stub
		System.out.print("2.上传配置类mImagesPath==" + mImagesPath + "\n");
	}

}
