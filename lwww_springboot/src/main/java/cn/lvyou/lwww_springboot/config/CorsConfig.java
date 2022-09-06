package cn.lvyou.lwww_springboot.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter(){
        // 创建CorsConfiguration
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 配置信息
        // 允许任何请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法
        corsConfiguration.addAllowedMethod("*"); // 如果星号报错就用http://localhost:8080
        // 允许8080端口跨域
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.setAllowCredentials(true); // 对应前端的 withCredentials: true

        // 创建UrlBasedCorsConfigurationSource
        UrlBasedCorsConfigurationSource corsConfigurationSource=new UrlBasedCorsConfigurationSource();
//        注册跨域
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
//        返回CorsFilter
        return new CorsFilter(corsConfigurationSource);
    }
}
