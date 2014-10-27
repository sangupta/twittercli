package com.sangupta.twittercli.command;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.satya.user.impl.TwitterUserProfile;
import com.sangupta.twittercli.TwitterCommand;

@Command(name = "follow", description = "Follow the given set of users")
public class Follow extends TwitterCommand {
	
	@Arguments(description = "List of users to follow", required = true)
	public List<String> users;

	@Override
	public void doCommand() {
		for(String user : users) {
			WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/friendships/create.json?screen_name=" + getScreenName(user), WebRequestMethod.POST);
			super.user.signRequest(request);
			WebResponse response = WebInvoker.executeSilently(request);
			if(response == null || !response.isSuccess()) {
				System.out.println("Unable to follow user: " + user);
			}
			
			TwitterUserProfile profile = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterUserProfile.class);
			System.out.println("Successfully followed user: " + profile.getName() + " (" + user + ")");
		}
	}

}
