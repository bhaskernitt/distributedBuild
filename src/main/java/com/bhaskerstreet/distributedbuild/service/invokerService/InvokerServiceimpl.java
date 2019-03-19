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
		System.out.println("os found is " +   osName+"...");

		for (Projects projects:machine.getProjects()) {
			   if (osName.startsWith("windows")) {
			      // -- Windows --
				   
				  
				   System.out.println("........");
				   System.out.println(getClass().getResource("/scripts/script.bat"));
			      resource = getClass().getResource("/scripts/script.bat");
			      System.out.println(resource.toURI().getPath()+"-----");
			      try {
			    	System.out.println("artifact "  +projects.getArtifactId());
			    	System.out.println("project path   "+projects.getPath());
			    	}
			      catch(Exception e) {
			    	  System.out.println("exception caught");
			      }//D:\eclipseWorkSpaceNew\distributedBuild\target\distributedBuildSetup\doNotDelete\scripts
			      processBuilder.command( /*resource.toURI().getPath()*/System.getProperty("user.dir")+"\\doNotDelete\\scripts\\script.bat", projects.getArtifactId(),projects.getPath(),projects.getPath().substring(0, 1));
			   	} else {
			      // -- Linux --
			      System.out.println("linux system found");
			      resource = InvokerServiceimpl.class.getResource("/scripts/script.sh");
			      processBuilder.command("/bin/bash", System.getProperty("user.dir")+"\\doNotDelete\\scripts\\script.sh",projects.getPath());
			      }
			   
			   System.out.println("process is ready to runn....");
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
