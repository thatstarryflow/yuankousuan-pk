package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

public class Touch implements Runnable{
    public static int SKIP = 0 , SX = 1  ,AGAIN = 2, DONOTING = 3;
    static JadbConnection jadb = new JadbConnection();
    static List<JadbDevice> devices = null;
    static JadbDevice device;

    public static void getDevice(int i){
        try { devices = jadb.getDevices();
        } catch (IOException | JadbException e) {e.printStackTrace(); }
        if (devices.size() == 0) throw new RuntimeException("No devices!");
        device =  devices.get(i);
        System.out.print("target device -> ");
        System.out.println(device);
    }

    public static void startTouch(int i,int sleep) {

        try {
           
            switch (i) {
                case 0:
                    // print(device.executeShell("input tap 970 120")); 
                    print(device.executeShell("input swipe 500 1200 500 1230"));
                    break;
                case 1:
                    print(device.executeShell("input tap 520 1540"));
                    break;
                case 2:
                    print(device.executeShell("input tap 760 1830"));
                    break;
                case 3:
                    break;
                default:
                    new RuntimeException("unkonw code").printStackTrace();;
                    break;
            }
          Thread.sleep(sleep);
        } catch (Exception e) { e.printStackTrace();}
       
    }


    public static void print (InputStream is) throws UnsupportedEncodingException{
        InputStreamReader isr =new InputStreamReader(is,"utf-8");
        BufferedReader br =new BufferedReader(isr);
        try {
            while ((br.read())!=-1) System.out.print(br.readLine());
        } catch (IOException e) {e.printStackTrace(); }
    }

    @Override
    public void run(){
        System.out.println("touch start !");
        System.out.println("#############");
        while (true) {
            switch (Main.done) {
                case 0:
                    startTouch(SKIP, 500);
                    break;
                case 1: case 2 :
                    startTouch(SX, 500);
                    startTouch(AGAIN, 500);
                    break;
                default:
                    startTouch(DONOTING, 200);
                    break;
            }
        }
        
    }

    
}