package com.bhaskerstreet.distributedbuild.service.httpClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpServiceImpl<T> implements HttpService<T>{


	@Autowired

	private RestTemplate restTemplate;

@Override
public void sendRequest(String url, HttpMethod method, HttpEntity requestEntity, Class responseType, Object... uriVariables) {

	restTemplate.exchange( url,  method,  requestEntity,  responseType, uriVariables);
}


@Override
public ResponseEntity<T> sendPOSTRequest(String url, HttpEntity requestEntity, Class responseType, Object... uriVariables) {

	System.out.println("request is being send to");
	
	RestTemplate restTemplate=new RestTemplate();
	return  restTemplate.exchange( url, HttpMethod.POST,  requestEntity,  responseType);
}
}
