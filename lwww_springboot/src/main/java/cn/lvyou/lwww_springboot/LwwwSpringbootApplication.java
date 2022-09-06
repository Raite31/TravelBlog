package cn.lvyou.lwww_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.lvyou.lwww_springboot.mapper")
public class LwwwSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LwwwSpringbootApplication.class, args);
    }

}
