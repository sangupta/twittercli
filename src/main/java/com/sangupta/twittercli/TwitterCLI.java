package com.sangupta.twittercli;

import io.airlift.command.Cli;
import io.airlift.command.Cli.CliBuilder;
import io.airlift.command.Help;
import io.airlift.command.ParseArgumentsUnexpectedException;

import com.sangupta.jerry.store.PropertiesUserLocalStore;
import com.sangupta.jerry.store.UserLocalStore;
import com.sangupta.twittercli.command.AuthorizeCommand;
import com.sangupta.twittercli.command.VersionCommand;
import com.sangupta.twittercli.command.WhoAmI;

public class TwitterCLI {
	
	public static final UserLocalStore LOCAL_STORE = new PropertiesUserLocalStore(null, "twittercli");
	
	public static void main(String[] args) {
		
		@SuppressWarnings("unchecked")
		CliBuilder<Runnable> builder = Cli.<Runnable>builder("t")
										  .withDescription("Command line Twitter client")
										  .withDefaultCommand(Help.class)
										  .withCommands(Help.class, AuthorizeCommand.class, VersionCommand.class, WhoAmI.class);
		
		
		Cli<Runnable> cliParser = builder.build();
		
		try {
			cliParser.parse(args).run();
		} catch(ParseArgumentsUnexpectedException e) {
			System.out.println("Invalid command, use '$ t help' for usage instructions!");
		}
	}

}
