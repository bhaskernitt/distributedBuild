package com.bhaskerstreet.distributedbuild.controller.v1.build;


import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import com.bhaskerstreet.distributedbuild.service.BuildProcessService.BuildProcessService;
import com.bhaskerstreet.distributedbuild.service.BuildProcessService.BuildProcessServiceimpl;
import com.bhaskerstreet.distributedbuild.service.dataTransfterService.DataTransferServiceImpl;
import com.bhaskerstreet.distributedbuild.service.dataTransfterService.DataTrasnsferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildController {


	/*@Autowired
	private BuildProcessService buildProcessService;*/



	

@PostMapping(value = "/startBuild")
public String startBuild(@RequestBody Machines machines) throws Exception{

	Runnable task = () -> {
	    
	    
	    DataTrasnsferService dataTrasnsferService=new DataTransferServiceImpl();
	    
	    dataTrasnsferService.initiateSocketConnection(machines.getMachines().stream().filter(x -> x.getType().equalsIgnoreCase("local"))
        .findFirst()
        .orElse(null).getFileTransferPort());
	};

	
	
	BuildProcessService buildProcessService=new BuildProcessServiceimpl();
	buildProcessService.process(machines);
	
	task.run();

	Thread thread = new Thread(task);
	thread.start();
	
	

return "build request processed successfully";

}
}
