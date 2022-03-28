package org.javaweb.vuln.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.*;

import static com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		Set<String> exclusionSet = new LinkedHashSet<>();
//
//		// 配置需要被移除的消息转换器
//		exclusionSet.add(MappingJackson2HttpMessageConverter.class.getName());
//
//		for (Iterator<HttpMessageConverter<?>> iterator = converters.iterator(); iterator.hasNext(); ) {
//			HttpMessageConverter<?> converter = iterator.next();
//			String                  className = converter.getClass().getName();
//
//			if (exclusionSet.contains(className)) {
//				iterator.remove();
//			}
//		}
//	}
//
//	/**
//	 * FastJson初始化配置
//	 *
//	 * @return 结果
//	 */
//	@Bean
//	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//		FastJsonHttpMessageConverter fastConverter  = new FastJsonHttpMessageConverter4();
//		FastJsonConfig               fastJsonConfig = new FastJsonConfig();
//
//		fastJsonConfig.setSerializerFeatures(WriteMapNullValue, DisableCircularReferenceDetect);
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//
//		List<MediaType> mediaTypes = new ArrayList<>();
//		mediaTypes.add(APPLICATION_JSON);
//		fastConverter.setSupportedMediaTypes(mediaTypes);
//
//		return fastConverter;
//	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(false)
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.exposedHeaders("ContentType", "Authorization", "Content-Disposition");
	}

}

