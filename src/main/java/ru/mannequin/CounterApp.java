package ru.mannequin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CounterApp {

    public static void main(String[] args) {
//        log.debug("Это main");
        SpringApplication.run(CounterApp.class);
    }
}