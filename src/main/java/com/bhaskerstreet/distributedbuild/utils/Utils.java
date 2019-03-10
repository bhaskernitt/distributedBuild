package com.bhaskerstreet.distributedbuild.utils;


import org.springframework.stereotype.Component;

@Component
public class Utils {

	public static String append(String delimiter,String ...object){


		return String.join(delimiter,object);
	}


	public static String getOsName(){

		return System.getProperty("os.name").toLowerCase();
	}
}
