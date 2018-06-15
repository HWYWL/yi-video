package com.yi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static com.yi.controller.BasicController.FILESPACE;

/**
 * 把资源目录映射为文件服务器
 * @author YI
 * @date 2018-6-12 15:50:54
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 文件服务器地址转发
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("file:" + FILESPACE);
	}

	@Bean
	public ApiInterceptor apiInterceptor(){
		return new ApiInterceptor();
	}

	/**
	 * 拦截器注册 设置拦截接口
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor()).addPathPatterns("/user/**")
				.addPathPatterns("/video/upload", "/video/uploadCover", "/video/userLike", "/video/userUnLike", "/video/saveComment")
				.addPathPatterns("/bgm/**")
				.excludePathPatterns("/user/queryPublisher");

		super.addInterceptors(registry);
	}
}
