package com.bhaskerstreet.distributedbuild;

import com.bhaskerstreet.distributedbuild.configuration.AppConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	
	private static final Logger logger=LogManager.getLogger(DistributedbuildApplication.class);

    @Autowired
    private AppConfig appConfig;
    public static void main(String[] args) {
    	logger.info("Distributed build application");
        SpringApplication.run(DistributedbuildApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        appConfig.loadMachineConfigFile();


    }
    
    @PostConstruct
    public void startupApplication() {
    	try {
    		
    		logger.info(System.lineSeparator());
    		ClassLoader classLoader = getClass().getClassLoader();
    		StringBuilder result = new StringBuilder("");
    		
    		File file = new File(classLoader.getResource("startUpDesign").getFile());
    		try (Scanner scanner = new Scanner(file)) {
        		
    			
    			

    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				result.append(line).append("\n");
    			}

    			scanner.close();
    			logger.info("\n");
    			
    			logger.info(result.toString());

    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	
    	}
    	catch(Exception e) {
    		
    		System.out.println();
    	}
    	
    }

    @PreDestroy
    public void shutdownApplication() {
        // log shutdown
    }

    
}

