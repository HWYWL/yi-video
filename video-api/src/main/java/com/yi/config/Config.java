package com.yi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 配置文件
 * @author YI
 * @date 2018-10-11 21:02:13
 */
@MapperScan(basePackages="com.yi.mapper")
@ComponentScan(basePackages= {"com.yi", "org.n3r.idworker"})
@Configuration
public class Config {

}
