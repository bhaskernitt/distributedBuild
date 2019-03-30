package com.bhaskerstreet.distributedbuild.configuration.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bhaskerstreet.distributedbuild.service.security.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SecurityFilter extends OncePerRequestFilter {

	
	private static final Logger logger=LogManager.getLogger();
	
	@Autowired
	private SecurityService SecurityFilter;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		long start=System.currentTimeMillis();
		
		Map<String,String> details=new HashMap<>();
		
		details.put("fromUrl", request.getHeader("host"));
		details.put("toUrl", new String(request.getRequestURL()));
		details.put("jwttoken", request.getHeader("jwttoken"));
		
		
		//SecurityFilter.validateJwt(details);
		
		filterChain.doFilter(request, response);
		
		logger.info(getLogString(request,response,start));
		
		
	}
	
	
	private String getLogString(HttpServletRequest request, HttpServletResponse response, long starttime) {
		
		String result="";
		
		try {
		ObjectMapper mapper =new ObjectMapper();
		
		ObjectNode node=mapper.createObjectNode();
		
		node.put("fromRequest", request.getHeader("host"));
		node.put("toUrl", new String(request.getRequestURL()));
		node.put("uri", request.getRequestURI());
		node.put("method", request.getMethod());
		node.put("status",response.getStatus());
		node.put("duration", System.currentTimeMillis()-starttime+" ms");
		node.put("jwttoken", request.getHeader("jwttoken"));
		
		 result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		
		
		}
		catch(Exception e) {
			
		}
		return result;
		
		
	}

	
}
