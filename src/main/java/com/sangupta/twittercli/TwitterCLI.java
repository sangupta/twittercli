/**
 *
 * twittercli - Command line power tool for Twitter
 * Copyright (c) 2014, Sandeep Gupta
 * 
 * http://sangupta.com/projects/twittercli
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.twittercli;

import io.airlift.command.Cli;
import io.airlift.command.Cli.CliBuilder;
import io.airlift.command.Help;
import io.airlift.command.ParseArgumentsUnexpectedException;

import com.sangupta.jerry.store.PropertiesUserLocalStore;
import com.sangupta.jerry.store.UserLocalStore;
import com.sangupta.twittercli.command.Authorize;
import com.sangupta.twittercli.command.Followers;
import com.sangupta.twittercli.command.Mentions;
import com.sangupta.twittercli.command.Retweets;
import com.sangupta.twittercli.command.RetweetsOfMe;
import com.sangupta.twittercli.command.Timeline;
import com.sangupta.twittercli.command.Update;
import com.sangupta.twittercli.command.Users;
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
										  .withCommands(Help.class, Authorize.class, Version.class, 
												  		WhoAmI.class, Followers.class, Update.class, 
												  		WhoIs.class, Users.class, Retweets.class, 
												  		RetweetsOfMe.class, Mentions.class, Timeline.class);
		
		
		Cli<Runnable> cliParser = builder.build();
		
		try {
			cliParser.parse(args).run();
		} catch(ParseArgumentsUnexpectedException e) {
			System.out.println("Invalid command, use '$ t help' for usage instructions!");
		}
	}

}
