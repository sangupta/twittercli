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
import com.sangupta.jerry.util.UriUtils;
import com.sangupta.twittercli.TwitterCommand;
import com.sangupta.twittercli.domain.TwitterStatusUpdate;

@Command(name = "update", description = "Update the user status")
public class Update extends TwitterCommand {
	
	@Arguments(description = "Message to set")
    public List<String> messages;

	@Override
	public void doCommand() {
		if(AssertUtils.isEmpty(messages)) {
			System.out.println("No message to update as status... exiting!");
			return;
		}

		StringBuilder builder = new StringBuilder(1024);
		for(int index = 0; index < messages.size(); index++) {
			if(index > 0) {
				builder.append(' ');
			}
			builder.append(messages.get(index));
		}
		String message = builder.toString();
		
		WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/statuses/update.json?status=" + UriUtils.encodeURIComponent(message), WebRequestMethod.POST);
		user.signRequest(request);
		WebResponse response = WebInvoker.executeSilently(request);
		
		TwitterStatusUpdate update = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterStatusUpdate.class);
		System.out.println("Tweet successfully posted with ID " + update.idStr + " by @" + update.user.getScreenName());
	}

}
