package com.bhaskerstreet.distributedbuild.service.security;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
	
	boolean validateJwt(Map<String,String> details);

}
