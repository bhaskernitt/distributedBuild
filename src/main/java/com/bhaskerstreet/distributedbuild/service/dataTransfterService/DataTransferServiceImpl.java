package com.bhaskerstreet.distributedbuild.service.dataTransfterService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Service;

@Service
public class DataTransferServiceImpl implements DataTrasnsferService {
	
	
	@Override
	public void initiateSocketConnection(Integer port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			Socket s = ss.accept();// establishes connection
			DataInputStream dis = new DataInputStream(s.getInputStream());
			String str = (String) dis.readUTF();
			System.out.println("message= " + str);
			ss.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	@Override
	public void acceprCOnnection(String host,Integer port) {
		
		try {
		Socket s=new Socket(host,port);  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		dout.writeUTF("Hello Server");  
		dout.flush();  
		dout.close();  
		s.close();  
		}catch(Exception e)
	{
		System.out.println(e);
	}
	}

}
