package com.bhaskerstreet.distributedbuild.configuration.machineConfig;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Machines {


    private List<Machine> machines;

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }
}
