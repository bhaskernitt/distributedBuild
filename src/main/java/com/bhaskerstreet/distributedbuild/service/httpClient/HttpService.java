package com.bhaskerstreet.distributedbuild.service.httpClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public interface HttpService<T> {

	void sendRequest(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables);

	ResponseEntity<T> sendPOSTRequest(String url, @Nullable HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables);
}
