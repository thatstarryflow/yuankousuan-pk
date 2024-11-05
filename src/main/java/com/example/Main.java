package com.example;

import java.util.Arrays;
import java.util.List;

import io.appium.mitmproxy.InterceptedMessage;
import io.appium.mitmproxy.MitmproxyJava;
import io.appium.mitmproxy.InterceptedMessage.Response;

import com.google.gson.*;


/**
 * Main
 */
public class Main {
	private static MitmproxyJava proxy;
	static String requestUrl;
	static String posturl = "https://xyks.yuanfudao.com/leo-math/android/exams?";
	static String okUrl = "https://xyks.yuanfudao.com/leo-star/android/exercise/rank/list";


	static String  tmp;
	static Response resp ;
	static String tempok;

	static int done = 3;


	public static void main(String[] args) {

		Touch.getDevice(0);
		new Thread(new Touch()).start();
		
		
		letGo();
		// try {
		// 	System.out.println(changeA(new String(Files.readAllBytes(Paths.get("t.json")))));
		// } catch (IOException e) {

		// 	e.printStackTrace();
		// }

		
	}


	public static String changeA(String res){

		JsonObject body = JsonParser.parseString(res).getAsJsonObject();
		JsonArray que = body.get("questions").getAsJsonArray();
	
		JsonObject que0 = que.get(0).getAsJsonObject();

		que0.addProperty("content", "1=\\square");
		que0.addProperty("answer", "1");
		que0.addProperty("status", 1);
		que0.addProperty("userAnswer", "1");
		que0.add("answers", JsonParser.parseString("[\"1\"]").getAsJsonArray());
		body.add("questions", JsonParser.parseString("["+que0+"]").getAsJsonArray());
		body.addProperty("questionCnt", 1);

		return body.toString();
	}


	public static void letGo() {
	
		// List<InterceptedMessage> messages = new ArrayList<InterceptedMessage>();

		//optional, default port is 8080
		int mitmproxyPort = 8090;

		//optional, you can pass null if no extra params
		List<String> extraMitmproxyParams = Arrays.asList("-q");

		// remember to set local OS proxy settings in the Network Preferences
		proxy = new MitmproxyJava("C:\\Users\\ci\\Path\\Python\\3.13.0\\Scripts\\mitmdump.exe", (InterceptedMessage m) -> {

			requestUrl = m.getRequest().getUrl();
			if(requestUrl.startsWith(posturl)){
				done = 0;
				resp = m.getResponse();
				tmp = new String(resp.getBody());
				tempok = changeA(tmp);
				// tempok = tmp;
				resp.setBody(tempok.getBytes());
				
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println(tmp);
				System.out.println("--------------------------------------------");
				System.out.println(tempok);
			
			}else if ( requestUrl.startsWith(okUrl)) {
				done = 2;
				System.out.println("*******************************************");
				System.out.println("okok");
			}
			

			// messages.add(m);
			return m;
		}, mitmproxyPort, extraMitmproxyParams);

		try {
			proxy.start();
		} catch (Exception e) { e.printStackTrace(); } 
		
			
		// do stuff

		// try {
		// 	proxy.stop();
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
	}



}