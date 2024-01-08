package cn.sunjinxin.savior.example.ateye;

import cn.sunjinxin.savior.core.anno.Savior;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sunjinxin
 * @since 2024/1/8 21:06
 */
@SpringBootApplication
@Savior
public class AteyeMain {
    public static void main(String[] args) {
        SpringApplication.run(AteyeMain.class, args);
    }
}