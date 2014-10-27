package com.sangupta.twittercli.command;

import io.airlift.command.Command;

import com.sangupta.twittercli.TwitterCLI;

@Command(name = "version", description = "Show application version")
public class VersionCommand implements Runnable {
	
	public void run() {
		final Package pkg = TwitterCLI.class.getPackage();
		System.out.println("Twitter CLI version " + pkg.getImplementationVersion());
	}

}
