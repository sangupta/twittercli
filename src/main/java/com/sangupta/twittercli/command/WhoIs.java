package com.sangupta.twittercli.command;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.satya.user.impl.TwitterUserProfile;
import com.sangupta.twittercli.TwitterCommand;

@Command(name = "whois", description = "Display given users profile info")
public class WhoIs extends TwitterCommand {
	
	@Arguments(description = "List of users to fetch profile for")
	public List<String> users;

	@Override
	public void doCommand() {
		if(AssertUtils.isEmpty(users)) {
			System.out.println("Atleast one user must be specified... exiting!");
			return;
		}
		
		if(users.size() > 1) {
			System.out.println("Currently only one user is supported");
			return;
		}
		
		WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/users/show.json?screen_name=" + users.get(0), WebRequestMethod.GET);
		user.signRequest(request);
		WebResponse response = WebInvoker.executeSilently(request);
		TwitterUserProfile profile = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterUserProfile.class);
		
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
