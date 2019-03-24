package com.bhaskerstreet.distributedbuild.controller.v1.build;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import com.bhaskerstreet.distributedbuild.service.BuildProcessService.BuildProcessService;

@RestController
public class BuildController {

	@Autowired
	private BuildProcessService buildProcessService;

	@PostMapping(value = "/startBuild")
	public String startBuild(@RequestBody Machines machines,@RequestHeader MultiValueMap<String, String> headers) throws Exception {

		// BuildProcessService buildProcessService = new BuildProcessServiceimpl();
		buildProcessService.process(machines,headers.toSingleValueMap().get("host"));

		return "build request processed successfully";

	}

	@PostMapping(value = "/build")
	public String build(@RequestBody Machine machine, @RequestHeader MultiValueMap<String, String> headers) {

		System.out.println(machine);
		buildProcessService.process(machine);

		System.out.println("request rcvd : " + machine);

		return "build request processed successfully";

	}

	@PostMapping(value = "/copyBuild")
	public String sayHello(@RequestHeader MultiValueMap<String, String> headers, @RequestBody byte[] body) {

		String message = "FILE COPIED SUCCESSFULLY......";
		try {
			Files.write(Paths.get(headers.toSingleValueMap().get("filepath")), body);
		}

		catch (Exception e) {

			System.out.println("unable to copy generated build");
			message = "unable to copy build file";

		}

		return message;

	}
}
