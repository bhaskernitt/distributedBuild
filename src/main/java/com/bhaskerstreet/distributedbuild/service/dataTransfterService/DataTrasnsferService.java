package com.bhaskerstreet.distributedbuild.service.dataTransfterService;

public interface DataTrasnsferService {
	
	void initiateSocketConnection(Integer port);
	void acceprCOnnection(String host,Integer port);

}
