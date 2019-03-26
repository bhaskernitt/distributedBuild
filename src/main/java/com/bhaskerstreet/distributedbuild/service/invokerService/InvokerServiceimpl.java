package com.bhaskerstreet.distributedbuild.service.invokerService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Machine;
import com.bhaskerstreet.distributedbuild.configuration.machineConfig.Projects;
import com.bhaskerstreet.distributedbuild.service.httpClient.HttpService;
import com.bhaskerstreet.distributedbuild.service.httpClient.HttpServiceImpl;
import com.bhaskerstreet.distributedbuild.utils.Utils;

@Service
public class InvokerServiceimpl implements InvokerService {

	@Override
	public void invoke(Machine machine, String host) {

		Process process = null;
		BufferedReader stdInput = null;
		BufferedReader stdErrorInput = null;
		String osName;

		

		try {

			ProcessBuilder processBuilder = new ProcessBuilder();

			URL resource;

			osName = Utils.getOsName();
			System.out.println("os found is " + osName + "...");

			for (Projects projects : machine.getProjects()) {

				System.out.println(System.getProperty("user.dir"));
				try {
					if (osName.startsWith("windows")) {
						// -- Windows --
						System.out.println("windows system found ");
						// resource = getClass().getResource("/scripts/script.bat");
						processBuilder.command(
								/* resource.toURI().getPath() */System.getProperty("user.dir")
										+ "\\doNotDelete\\scripts\\script.bat",
								projects.getArtifactId(), projects.getPath(), projects.getPath().substring(0, 1));
					} else {
						// -- Linux --
						System.out.println("linux system found");
						// resource = InvokerServiceimpl.class.getResource("/scripts/script.sh");
						processBuilder.command("/bin/bash",
								System.getProperty("user.dir") + "/doNotDelete/scripts/script.sh",
								projects.getPath());
					}

					System.out.println("process is ready to runn....");
					process = processBuilder.start();
					stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = stdInput.readLine();
					while (line != null) {
						System.out.println(line);
						line = stdInput.readLine();
					}

					stdErrorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					line = stdErrorInput.readLine();
					while (line != null) {
						System.out.println(line);
						line = stdErrorInput.readLine();
					}

				} catch (Exception e) {
					System.out.println("exception caught");
					System.out.println(e.getMessage());
				}

				finally {
					stdInput.close();
					process.destroyForcibly();

					// if(process.getErrorStream()==null) {
					initiateSocketConnectionOnSuccessBuild(osName, projects, machine.getInitiatorHostAddress());
					// }

				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	private void initiateSocketConnectionOnSuccessBuild(String osName, Projects projects, String senderHost) {
		try {

			byte[] array = Files.readAllBytes(new File(projects.getCopyBuildFromPath())
							.toPath());

			HttpService client = new HttpServiceImpl();

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		
			headers.add("Content-Type", "text/plain");
			headers.add("filepath",projects.getCopyBuildToPath());

			HttpEntity<?> request = new HttpEntity<>(array, headers);

			long startTime = System.currentTimeMillis();

			System.out.println("senderHost :  "+senderHost);
			ResponseEntity responseEntity = client.sendPOSTRequest(senderHost + "/copyBuild", request, String.class);

			long endTime = System.currentTimeMillis();

			long duration = (endTime - startTime) / (1000 * 60); // divide by 1000 000 to get milliseconds.
			System.out.println("[ time taken " + duration + " mints  ]" + responseEntity.getBody());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
