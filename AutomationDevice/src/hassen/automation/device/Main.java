package hassen.automation.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hassen.automation.common.Device;
import hassen.automation.common.Event;
import hassen.automation.common.Settings;

public class Main {

	private int position;
	private String [] args;
	private Document document;
	private BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
	
	private Main (String [] args) {
		this.args = args;
		document = new Document ();
	}

	private void processArgs () {
		position = 0;
		while (position != args.length) {
			String command = args[position++];
			if (command.equals("-s")) processSettings ();
			if (command.equals("-c")) processDevice ();
			if (command.equals("-d")) processDeclare ();
			if (command.equals("-u")) processUndeclare ();
			if (command.equals("-g")) processGo ();
		}
	}

	private void processCompositeMenu () throws IOException {
		System.out.println("[ O ] Send On");
		System.out.println("[ o ] Send Off");
		System.out.println("[ N ] Send Next");
		System.out.println("[ P ] Send Previous");
		System.out.println("[ Z ] Send Zero");
		System.out.println("[ # ] Send Value");
		System.out.print("What : ");
		int deviceID = document.getDevice().getId();
		Event event = new Event();
		while (true) {
			String kind = input.readLine();
			if (kind.equals("o")) event.setOffEvent(deviceID);
			if (kind.equals("O")) event.setOnEvent(deviceID);
			if (kind.equals("N")) event.setNextEvent(deviceID);
			if (kind.equals("P")) event.setPreviousEvent(deviceID);
			if (kind.equals("Z")) event.setZeroEvent(deviceID);
			try {
				int value = Integer.parseInt(kind);
				event.setValueEvent(deviceID, value);
			}
			catch (Exception e) {}
			document.send (event);
		}
	}

	private void processBinaryMenu () throws IOException {
		System.out.println("[ 1 ] Send On");
		System.out.println("[ 0 ] Send Off");
		System.out.print("What : ");
		int deviceID = document.getDevice().getId();
		Event event = new Event();
		while (true) {
			String kind = input.readLine();
			if (kind.equals("0")) event.setOffEvent(deviceID);
			if (kind.equals("1")) event.setOnEvent(deviceID);
			document.send (event);
		}
	}

	private void processEventMenu () throws IOException {
		System.out.println("[ N ] Send Next");
		System.out.println("[ P ] Send Previous");
		System.out.println("[ Z ] Send Zero");
		System.out.print("What : ");
		int deviceID = document.getDevice().getId();
		Event event = new Event();
		while (true) {
			String kind = input.readLine();
			if (kind.equals("N")) event.setNextEvent(deviceID);
			if (kind.equals("P")) event.setPreviousEvent(deviceID);
			if (kind.equals("Z")) event.setZeroEvent(deviceID);
			document.send (event);
		}
	}

	private void processRangeMenu () throws IOException {
		System.out.println("[ # ] Send Value");
		System.out.print("What : ");
		int deviceID = document.getDevice().getId();
		Event event = new Event();
		while (true) {
			String kind = input.readLine();
			try {
				int value = Integer.parseInt(kind);
				event.setValueEvent(deviceID, value);
			}
			catch (Exception e) {}
			document.send (event);
		}
	}

	private void processGo() {
		document.start();
		System.out.println (document.getDevice());
		System.out.println ("Ready to process event - press enter to start");
		try {
			input.readLine();
			if (document.getDevice().isInput()) {
				Device d = document.getDevice();
				if (d.isBinary()) processBinaryMenu();
				if (d.isEvent()) processEventMenu();
				if (d.isRange()) processRangeMenu();
				if (d.isComposite()) processCompositeMenu();
			}
		} catch (IOException e1) { }
	}
	
	private void processUndeclare() {
		document.undeclare();
	}
	
	private void processDeclare() {
		document.declare();
	}
	
	private void processDevice() {
		Device device = new Device ();
		int deviceID = Integer.parseInt(args[position++]);
		device.setId(deviceID);
		String type = args[position++];
		if (type.equals("I")) device.setInput();
		if (type.equals("O")) device.setOutput();
		String kind = args[position++];
		if (kind.equals("B")) device.setBinary();
		if (kind.equals("E")) device.setEvent();
		if (kind.equals("R")) {
			int min = Integer.parseInt(args[position++]);
			int max = Integer.parseInt(args[position++]);
			device.setRange(min, max);
		}
		if (kind.equals("C")) {
			int min = Integer.parseInt(args[position++]);
			int max = Integer.parseInt(args[position++]);
			device.setComposite(min, max);
		}
		document.setDevice(device);
	}

	private void processSettings() {
		String serverName = args[position++];
		int ctrlPort = Integer.parseInt(args[position++]);
		String multicastName = args[position++];
		int dataPort = Integer.parseInt(args[position++]);
		document.setSettings(new Settings (serverName, ctrlPort, multicastName, dataPort));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main (args);
		main.processArgs();
	}

}
