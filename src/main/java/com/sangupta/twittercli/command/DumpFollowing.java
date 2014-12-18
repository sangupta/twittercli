package com.sangupta.twittercli.command;

import io.airlift.command.Command;
import io.airlift.command.Option;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.satya.user.impl.TwitterUserProfile;
import com.sangupta.twittercli.TwitterCommand;
import com.sangupta.twittercli.domain.TwitterUserList;

@Command(name = "following", description = "Show all users the current user is following")
public class DumpFollowing extends TwitterCommand {
	
	@Option(name = "-l", description = "The nested iteration level, defaults to ONE")
	public int level = 1;
	
	@Option(name = "-ov", description = "Dump only verified accounts")
	public boolean onlyVerified = false;

	@Override
	public void doCommand() {
		System.out.println("Creating dump of all friends...");
		
		Set<TwitterUserProfile> userList = new HashSet<TwitterUserProfile>();
		
		long cursor = -1;
		do {
			WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/friends/list.json?count=200&cursor=" + cursor, WebRequestMethod.GET);
			user.signRequest(request);
			WebResponse response = WebInvoker.executeSilently(request);
			TwitterUserList list = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterUserList.class);
			
			if(list != null) {
				if(!onlyVerified) {
					userList.addAll(Arrays.asList(list.users));
				} else {
					for(TwitterUserProfile u : list.users) {
						if(u.isVerified()) {
							userList.add(u);
						}
					}
				}
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
