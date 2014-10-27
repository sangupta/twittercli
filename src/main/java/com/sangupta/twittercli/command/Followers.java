package com.sangupta.twittercli.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.airlift.command.Command;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.satya.user.impl.TwitterUserProfile;
import com.sangupta.twittercli.TwitterCommand;
import com.sangupta.twittercli.domain.TwitterUserList;

@Command(name = "followers", description = "Show all followers of a user")
public class Followers extends TwitterCommand {

	@Override
	public void doCommand() {
		List<TwitterUserProfile> userList = new ArrayList<TwitterUserProfile>();
		
		long cursor = -1;
		do {
			WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/followers/list.json?count=200&cursor=" + cursor, WebRequestMethod.GET);
			user.signRequest(request);
			WebResponse response = WebInvoker.executeSilently(request);
			TwitterUserList list = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterUserList.class);
			
			if(list != null) {
				userList.addAll(Arrays.asList(list.users));
			}
			
			if(list.nextCursor == 0) {
				break;
			}
			cursor = list.nextCursor;
		} while(true);
		
		for(TwitterUserProfile profile : userList) {
			System.out.print(profile.getScreenName() + "\t\t" + profile.getName() + "\t\t" + profile.getDescription());
			System.out.println();
		}
	}

}
