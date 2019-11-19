package org.traffic.ldms_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class DeviceServer {
	public static void main(String[] args) {
		Socket client = null;
		ServerSocket serverSocket = null;
		BufferedReader reader = null;
		OutputStream output = null;
		try {
			serverSocket = new ServerSocket(82);

			while (true) {
				client = serverSocket.accept();
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

				String fileName = reader.readLine();
				String[] arr = fileName.split(" ");
				// System.out.println(arr[1]);

				if (arr[1] != null) {
					lightOnOff();
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void lightOnOff() {
		/*
		 * try { for(int i=0;i<3;i++) { System.out.println("LED媛� 諛섏쭩�엯�땲�떎");
		 * Thread.sleep(500); } } catch (Exception e) {
		 * 
		 * }
		 */

		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "MyLED", PinState.HIGH);
		pin.setShutdownOptions(true, PinState.LOW);
		try {
			System.out.println("--> GPIO state should be: ON");

			Thread.sleep(5000);
			pin.low();

			System.out.println("--> GPIO state should be: OFF");

			Thread.sleep(5000);
			pin.toggle();

			System.out.println("--> GPIO state should be: ON");

			Thread.sleep(5000);
			pin.toggle();

			System.out.println("--> GPIO state should be: OFF");

			Thread.sleep(5000);

			System.out.println("--> GPIO state should be: ON for only 1 second");

			gpio.shutdown();

			System.out.println("Exiting ControlGpioExample");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
