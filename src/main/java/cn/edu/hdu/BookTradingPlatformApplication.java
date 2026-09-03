package cn.edu.hdu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.hdu.mapper")
public class BookTradingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookTradingPlatformApplication.class, args);
    }
}
