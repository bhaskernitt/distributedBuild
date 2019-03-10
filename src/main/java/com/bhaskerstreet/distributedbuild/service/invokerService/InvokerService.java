package com.bhaskerstreet.distributedbuild.service.invokerService;


import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import org.springframework.stereotype.Service;

@Service
public interface InvokerService {

	void invoke(Machine  machine);
}
