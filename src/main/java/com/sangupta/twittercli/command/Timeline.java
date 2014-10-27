package com.sangupta.twittercli.command;

import io.airlift.command.Command;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.twittercli.TwitterCommand;
import com.sangupta.twittercli.domain.TwitterStatusUpdate;

@Command(name = "timeline", description = "Show 20 recent tweets for the current user")
public class Timeline extends TwitterCommand {

	@Override
	public void doCommand() {
		WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/statuses/home_timeline.json", WebRequestMethod.GET);
		user.signRequest(request);
		WebResponse response = WebInvoker.executeSilently(request);
		TwitterStatusUpdate[] updates = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterStatusUpdate[].class);
		
		if(AssertUtils.isEmpty(updates)) {
			System.out.println("No tweets found");
			return;
		}
		
		System.out.println();
		for(TwitterStatusUpdate update : updates) {
			System.out.println("  @" + update.user.getScreenName());
			System.out.println("  " + update.text);
			System.out.println("  " + update.createdAt);
			System.out.println();
		}
	}

}
