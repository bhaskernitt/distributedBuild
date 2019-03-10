package com.bhaskerstreet.distributedbuild.service.invokerService;

import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Projects;
import com.bhaskerstreet.distributedbuild.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;


@Service
public class InvokerServiceimpl implements InvokerService {


@Override
public void invoke(Machine machine) {

	Process process = null;

	try {

		ProcessBuilder processBuilder = new ProcessBuilder();

		URL resource ;

		String osName = Utils.getOsName();
		for (Projects projects:machine.getProjects()) {
			   if (osName.startsWith("windows")) {
			      // -- Windows --
			      resource = InvokerServiceimpl.class.getResource("/scripts/script.bat");
			      processBuilder.command( Paths.get(resource.toURI()).toFile().getAbsolutePath(), projects.getArtifactId(),projects.getPath());
			   	} else {
			      // -- Linux --
			      System.out.println("linux system found");
			      resource = InvokerServiceimpl.class.getResource("/scripts/script.sh");
			      processBuilder.command("/bin/bash", Paths.get(resource.toURI()).toFile().getAbsolutePath(), projects.getArtifactId(),projects.getPath());
			      }
			   process = processBuilder.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = stdInput.readLine();
				while (line != null) {
					System.out.println(line);
					line = stdInput.readLine();
				}

				stdInput.close();
				process.destroyForcibly();
		}
		

	} catch (Exception e) {
		System.out.println(e.getMessage());
		process.destroyForcibly();
	}

}
}
