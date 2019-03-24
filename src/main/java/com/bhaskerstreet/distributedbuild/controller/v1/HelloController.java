package com.bhaskerstreet.distributedbuild.controller.v1;


import com.bhaskerstreet.distributedbuild.configuration.AppConfig;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import com.fasterxml.jackson.core.JsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private AppConfig appConfig;

    @GetMapping(value = "/hello")
    @Async
    public String sayHello() {

        Machines machines=appConfig.getMachines();


        Server.startSocket();
        
        System.out.println("done---");
        

        return "hello from application";

    }

}
