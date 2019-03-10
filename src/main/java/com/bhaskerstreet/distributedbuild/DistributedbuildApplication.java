package com.bhaskerstreet.distributedbuild;

import com.bhaskerstreet.distributedbuild.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication(scanBasePackages = {
        "com.bhaskerstreet.distributedbuild.controller",
        "com.bhaskerstreet.distributedbuild.dao",
        "com.bhaskerstreet.distributedbuild.model",
        "com.bhaskerstreet.distributedbuild.repository",
        "com.bhaskerstreet.distributedbuild.service",
        "com.bhaskerstreet.distributedbuild.utils",
        "com.bhaskerstreet.distributedbuild.configuration",
		"com.bhaskerstreet.distributedbuild.component"
})

@Async
public class DistributedbuildApplication implements ApplicationRunner {


    @Autowired
    private AppConfig appConfig;
    public static void main(String[] args) {
        SpringApplication.run(DistributedbuildApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        appConfig.loadMachineConfigFile();


    }
}

