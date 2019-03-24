package com.bhaskerstreet.distributedbuild;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;
import java.nio.file.Files;

public class CLient {

	public static void main(String[] args) {
		try{      
			
			System.out.println();
			byte[] array = Files.readAllBytes(new File(""
					+ "D:\\eclipseWorkSpaceNew\\distributedBuild\\distributedBuildSetup\\distributedbuild-0.0.1-SNAPSHOT.jar").toPath());
			
			String senderHost="http://9ee3cdca.ngrok.io";
			Socket s=new Socket("tcp://0.tcp.eu.ngrok.io:13720",6666);  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			dout.writeUTF("Hello Server");  
			dout.flush();  
			dout.close();  
			s.close();  
			}catch(Exception e){
				System.out.println(e);}  

	}

}
