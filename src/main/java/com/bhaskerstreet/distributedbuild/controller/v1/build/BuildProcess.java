package com.bhaskerstreet.distributedbuild.controller.v1.build;

import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.service.BuildProcessService.BuildProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildProcess {

@Autowired
private BuildProcessService buildProcessService;



@PostMapping(value = "/build")
public String build(@RequestBody Machine machine){

	System.out.println(machine);
	buildProcessService.process(machine);

	System.out.println("request rcvd : "+machine);



	return "build request processed successfully";

}
}
