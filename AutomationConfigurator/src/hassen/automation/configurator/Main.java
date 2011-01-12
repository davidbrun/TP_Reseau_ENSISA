package hassen.automation.configurator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import hassen.automation.common.Device;
import hassen.automation.common.Mapping;
import hassen.automation.common.SessionConfigurator;
import hassen.automation.common.Settings;

public class Main {

	private int position;
	private String [] args;
	private Document document;
	private boolean loop;
	private BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
	private SessionConfigurator session;
	
	private Main (String [] args) {
		this.args = args;
		document = new Document ();
	}

	private void processArgs () {
		position = 0;
		while (position != args.length) {
			String command = args[position++];
			if (command.equals("-s")) processSettings ();
			if (command.equals("-m")) processAddAutoMapping ();
		}
	}

	private SessionConfigurator getSessionConfigurator () {
		if (session == null) session = new SessionConfigurator (document.getSettings());
		return session;
	}

	private void printMappings (Vector<Mapping> mappings) {
		if (mappings == null) System.out.println("null");
		else {
			if (mappings.size() == 0) System.out.println("Empty");
			int i=0;
			for (Mapping m : mappings) {
				System.out.print (" [ ");
				System.out.print (i);
				System.out.print (" ] ");
				System.out.println(m.toString());
				++i;
			}
		}
	}

	private void printDevices (Vector<Device> devices) {
		if (devices == null) System.out.println("null");
		else {
			int i=0;
			if (devices.size() == 0) System.out.println("Empty");
			for (Device d : devices) {
				System.out.print (" [ ");
				System.out.print (i);
				System.out.print (" ] ");
				System.out.println(d.toString());
				++i;
			}
		}
	}

	private void processListMappings () {
		Vector<Mapping> mappings = getSessionConfigurator().getMaps();
		printMappings (mappings);
	}

	private void processEraseMapping () {
		Vector<Mapping> mappings = getSessionConfigurator().getMaps();
		printMappings (mappings);
		//boolean success = getSessionConfigurator().eraseMap(getInteger(mappings.size()));
		boolean success = getSessionConfigurator().eraseMap(mappings.get(getInteger(mappings.size())).getId());
		if (success) System.out.println("Entry erased");
		else System.out.println("Error in erasing");
	}

	private void processClearMappings () {
		boolean success = getSessionConfigurator().clearMaps();
		if (success) System.out.println("Map cleared");
		else System.out.println("Error in clearing");
	}

	private int getInteger (int max) {
		int number = -1;
		while (number == -1) {
			System.out.print("Chose # : ");
			try {
				String text = input.readLine();
				number = Integer.parseInt(text);
				if (number >= max) number = -1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return number;
	}
	
	private Device selectDevice (Vector<Device> devices) {
		if (devices == null) {
			System.out.println("Error in device list");
			return null;
		}
		printDevices (devices);
		return devices.get(getInteger(devices.size()));
	}

	private void processAddMapping () {
		Device from = selectDevice (getSessionConfigurator().getInputs());
		Device to = selectDevice (getSessionConfigurator().getOutputs());
		if (from != null && to != null) {
			Mapping mapping = new Mapping (from, to);
			boolean success = getSessionConfigurator().addMap(mapping);
			if (success) System.out.println("Mapping added");
			else System.out.println("Error in adding");
		}
	}

	private void processAddAutoMapping () {
		Device from = getSessionConfigurator().getInputs().get(0);
		Device to = getSessionConfigurator().getOutputs().get(0);
		if (from != null && to != null) {
			Mapping mapping = new Mapping (from, to);
			boolean success = getSessionConfigurator().addMap(mapping);
			if (success) System.out.println("Mapping added");
			else System.out.println("Error in adding");
		}
	}

	private void processQuit () {
		loop = false;
	}

	private void displayMenu () {
		System.out.println("[ L ] List mappings");
		System.out.println("[ E ] Erase mappings");
		System.out.println("[ C ] Clear mappings");
		System.out.println("[ A ] Add mappings");
		System.out.println("[ M ] Add auto mappings");
		System.out.println("[ Q ] Quit");
		System.out.print("What : ");
	}

	private void startUI() {
		loop = true;
		try {
			while (loop) {
				displayMenu ();
				String text = input.readLine();
				char first = Character.toUpperCase(text.charAt(0)); 
				switch(first) {
				case 'L': processListMappings (); break;
				case 'E': processEraseMapping (); break;
				case 'C': processClearMappings (); break;
				case 'A': processAddMapping(); break;
				case 'M': processAddAutoMapping(); break;
				case 'Q': processQuit (); break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		main.startUI();
	}

}
