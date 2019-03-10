package com.bhaskerstreet.distributedbuild.service.BuildProcessService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.bhaskerstreet.distributedbuild.component.Task;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import com.bhaskerstreet.distributedbuild.service.httpClient.HttpService;
import com.bhaskerstreet.distributedbuild.service.invokerService.InvokerService;
import com.bhaskerstreet.distributedbuild.utils.Utils;

@Service
public class BuildProcessServiceimpl<T> implements BuildProcessService {

	private Environment environment;
	@Autowired
	private HttpService httpService;
	@Autowired
	private InvokerService invokerService;

	@Autowired
	public BuildProcessServiceimpl(Environment environment) {
		this.environment = environment;

	}

	@Override
	public void process(Machine machine) {

		invokerService.invoke(machine);

	}

	@Override
//@Async("asyncExecutor")
	public void process(Machines machines) throws Exception {

		List<Machine> listMachines = machines.getMachines();
		ExecutorService executor = Executors.newFixedThreadPool(10);

		long start=System.nanoTime();
		
		for (Machine machine : listMachines) {
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.set("Content-Type", "application/json");
			HttpEntity<?> request = new HttpEntity<>(machine, headers);

			Task<String> task = new Task(
					Utils.append("/", machine.getHost(), environment.getProperty("remote.request.build.url")),
					HttpMethod.POST, request, String.class,machine);

			
			executor.submit(task);

			System.out.println("task submitted....");

			
			/* MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
			  headers.set("Content-Type", "application/json"); HttpEntity<?> request = new
			  HttpEntity<>(machine, headers); 
			  httpService.sendPOSTRequest(Utils.append("/",
			  machine.getHost(), environment.getProperty("remote.request.build.url")),
			  request, String.class);*/
			 
		}
		
		long end=System.nanoTime();
		
		
		
		System.out.println((end-start)/1000000);

	}

}
