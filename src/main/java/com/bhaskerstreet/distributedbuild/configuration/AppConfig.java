package com.bhaskerstreet.distributedbuild.configuration;


import com.bhaskerstreet.distributedbuild.configuration.filters.SecurityFilter;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machines;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {


    @Autowired
    private Machines machines;

    public Machines getMachines() {
        return machines;
    }

    public void setMachines(Machines machines) {
        this.machines = machines;
    }

    public  void loadMachineConfigFile() throws  Exception{



         this.machines=new ObjectMapper().readValue( AppConfig.class.getResourceAsStream("/config/machine.json"), Machines.class);





    }

@Bean
public RestTemplate restTemplate() {
	return new RestTemplate();
}


@Bean
@Order(1)
public SecurityFilter securityFilter() {
	return  new SecurityFilter();
}


}
