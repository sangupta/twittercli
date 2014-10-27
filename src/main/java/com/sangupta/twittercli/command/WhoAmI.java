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

package com.sangupta.twittercli.command;

import io.airlift.command.Command;

import com.sangupta.satya.user.impl.TwitterUserProfile;
import com.sangupta.twittercli.TwitterCommand;

@Command(name = "whoami", description = "Display user profile info of currently signed-in user")
public class WhoAmI extends TwitterCommand {

	@Override
	public void doCommand() {
		TwitterUserProfile profile = (TwitterUserProfile) user.getUserProfile();
		
		System.out.println("ID: " + profile.getId());
		System.out.println("Since: " + profile.getCreatedAt());
		System.out.println("Screen Name: " + profile.getScreenName());
		System.out.println("Name: " + profile.getName());
		System.out.println("Followers: " + profile.getFollowersCount());
		System.out.println("Following: " + profile.getFriendsCount());
		System.out.println("Listed: " + profile.getListedCount());
		System.out.println("Favorites: " + profile.getFavouritesCount());
		System.out.println("Tweets: " + profile.getStatusesCount());
		System.out.println("Email: " + profile.getEmail());
		System.out.println("Bio: " + profile.getDescription());
		System.out.println("Location: " + profile.getLocation());
		System.out.println("URL: " + profile.getProfileLink());
	}

}
