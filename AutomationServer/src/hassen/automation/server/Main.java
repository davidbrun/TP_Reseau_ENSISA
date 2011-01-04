package hassen.automation.server;

import hassen.automation.common.Settings;

public class Main {

	private int position;
	private String [] args;
	private Document document;
	
	private Main (String [] args) {
		this.args = args;
		document = new Document ();
	}

	private void processArgs () {
		position = 0;
		while (position != args.length) {
			String command = args[position++];
			if (command.equals("-s")) processSettings ();
			if (command.equals("-g")) processGo ();
		}
	}

	private void processGo() {
		document.start();
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
