package com.wolf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(
//			SpringApplicationBuilder builder) {
//		return builder.sources(VideoApplication.class);
////		return builder.configure(VideoApplication.class);
//	}

}
