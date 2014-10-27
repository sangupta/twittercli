package com.sangupta.twittercli;

import io.airlift.command.Cli;
import io.airlift.command.Cli.CliBuilder;
import io.airlift.command.Help;
import io.airlift.command.ParseArgumentsUnexpectedException;

import com.sangupta.jerry.store.PropertiesUserLocalStore;
import com.sangupta.jerry.store.UserLocalStore;
import com.sangupta.twittercli.command.Authorize;
import com.sangupta.twittercli.command.Followers;
import com.sangupta.twittercli.command.Update;
import com.sangupta.twittercli.command.Version;
import com.sangupta.twittercli.command.WhoAmI;
import com.sangupta.twittercli.command.WhoIs;

public class TwitterCLI {
	
	public static final UserLocalStore LOCAL_STORE = new PropertiesUserLocalStore(null, "twittercli");
	
	public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		CliBuilder<Runnable> builder = Cli.<Runnable>builder("t")
										  .withDescription("Command line Twitter client")
										  .withDefaultCommand(Help.class)
										  .withCommands(Help.class, Authorize.class, Version.class, WhoAmI.class, Followers.class, Update.class, WhoIs.class);
		
		
		Cli<Runnable> cliParser = builder.build();
		
		try {
			cliParser.parse(args).run();
		} catch(ParseArgumentsUnexpectedException e) {
			System.out.println("Invalid command, use '$ t help' for usage instructions!");
		}
	}

}
