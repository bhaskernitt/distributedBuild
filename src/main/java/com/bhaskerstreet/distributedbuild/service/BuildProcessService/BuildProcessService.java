package com.bhaskerstreet.distributedbuild.service.BuildProcessService;


import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import org.springframework.stereotype.Service;

@Service
public interface BuildProcessService {

void process(Machine machine);

void process(Machines machines) throws Exception;

void transferBuild(Machine machine) throws Exception;
}
