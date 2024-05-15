package id.ac.ui.cs.advprog.besell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BeSellApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeSellApplication.class, args);
    }

}
