package com.bhaskerstreet.distributedbuild.component;

import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.service.httpClient.HttpService;
import com.bhaskerstreet.distributedbuild.service.httpClient.HttpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.Callable;


public class Task<T> implements Callable<String> {

	/*@Autowired
	private HttpService httpService;
*/
	private String url;
	private HttpMethod method;
	private HttpEntity<?> requestEntity;
	private Class<T> responseType;
	private Object[] uriVariables;
	private Machine machine;

	public Task() {
	}

	public Task(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Machine machine,
			Object... uriVariables) {
		this.url = url;
		this.method = method;
		this.requestEntity = requestEntity;
		this.responseType = responseType;
		this.uriVariables = uriVariables;
		this.machine = machine;

	}

	public String call() throws Exception {

		/*
		 * httpService.sendPOSTRequest(Utils.append("/", machine.getHost(),
		 * environment.getProperty("remote.request.build.url")),
		 * 
		 * request, String.class)
		 */

		try {
			
			HttpService httpService=new HttpServiceImpl<>();
			
			System.out.println();
			httpService.sendPOSTRequest(this.url, this.requestEntity, this.responseType, String.class);
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

		return null;
	}

}
