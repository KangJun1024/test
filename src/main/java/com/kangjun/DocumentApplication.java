package com.kangjun;

import com.kangjun.interceptor.AccessLimitInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableScheduling
@SpringBootApplication
public class DocumentApplication extends WebMvcConfigurerAdapter {
	//
	public static void main(String[] args) {
		SpringApplication.run(DocumentApplication.class, args);
	}

	/**
	 * 跨域
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 接口防刷限流拦截器
		registry.addInterceptor(accessLimitInterceptor());

		super.addInterceptors(registry);
	}


	@Bean
	public AccessLimitInterceptor accessLimitInterceptor() {
		return new AccessLimitInterceptor();
	}

}
