package com.bhaskerstreet.distributedbuild.controller.v1.build;


import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import com.bhaskerstreet.distributedbuild.service.BuildProcessService.BuildProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildController {


	@Autowired
	private BuildProcessService buildProcessService;



	@PostMapping(value = "/build1")
	public String build(@RequestBody Machine machine){

		System.out.println(machine);
		buildProcessService.process(machine);

		System.out.println("request rcvd : "+machine);



		return "build request processed successfully";

	}

@PostMapping(value = "/startBuild")
public String startBuild(@RequestBody Machines machines) throws Exception{

	buildProcessService.process(machines);

return "build request processed successfully";

}
}
