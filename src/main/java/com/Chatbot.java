package com;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatbot {
	private static final boolean TRACE_MODE = false;
	static String botName="super";

	public static void main(String[] args) {
		try {

			String resourcesPath = getResourcesPath();
			MagicBooleans.trace_mode = TRACE_MODE;

			System.out.println("Please enter your name:");
			String userName=IOUtils.readInputTextLine();
			System.out.println("Initializing bot....");
			Bot bot = new Bot(botName, resourcesPath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();
			System.out.println("Enter bot name....");
			botName=IOUtils.readInputTextLine();
			String textLine = "";

			while(true) {
				System.out.print(userName +": ");
				textLine = IOUtils.readInputTextLine();
				if ((textLine == null) || (textLine.length() < 1))
					textLine = MagicStrings.null_input;
				if (textLine.equals("q")) {
					System.exit(0);
				} else if (textLine.equals("wq")) {
					bot.writeQuit();
					System.exit(0);
				} else {
					String request = textLine;
					if (MagicBooleans.trace_mode)
						System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
					String response = chatSession.multisentenceRespond(request);
					while (response.contains("&lt;"))
						response = response.replace("&lt;", "<");
					while (response.contains("&gt;"))
						response = response.replace("&gt;", ">");
					System.out.println(botName +": " + response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

}
