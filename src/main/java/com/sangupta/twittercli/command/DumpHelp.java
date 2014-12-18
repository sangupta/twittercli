package com.sangupta.twittercli.command;

import io.airlift.command.Command;

@Command(name = "help", description = "Help for the dump commands")
public class DumpHelp implements Runnable {

	public void run() {
		System.out.println("The dump tool allows you to dump data from any given twitter account");
	}

}
