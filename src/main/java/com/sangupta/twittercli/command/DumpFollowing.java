package com.sangupta.twittercli.command;

import io.airlift.command.Arguments;
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
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.DateUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.jerry.util.StringUtils;
import com.sangupta.satya.user.impl.TwitterUserProfile;
import com.sangupta.twittercli.TwitterCommand;
import com.sangupta.twittercli.domain.TwitterIDList;
import com.sangupta.twittercli.domain.TwitterUserList;

@Command(name = "following", description = "Show all users the current user is following")
public class DumpFollowing extends TwitterCommand {
	
	@Option(name = "-l", description = "The nested iteration level, defaults to ONE")
	public int level = 1;
	
	@Option(name = "-ov", description = "Dump only verified accounts")
	public boolean onlyVerified = false;
	
	@Option(name = "-i", description = "Fetch only Twitter ids")
	public boolean onlyIDs = false;
	
	@Arguments
	public String userName;

	private Set<TwitterUserProfile> userList = new HashSet<TwitterUserProfile>();
	
	private Set<String> userIDs = new HashSet<String>();
	
	@Override
	public void doCommand() {
		System.out.println("Creating dump of all friends...");
		
		int pageNumber = 1;
		long cursor = -1;
		do {
			String url;
			if(!this.onlyIDs) {
				url = "https://api.twitter.com/1.1/friends/list.json?count=200&cursor=" + cursor;
			} else {
				url = "https://api.twitter.com/1.1/friends/ids.json?count=5000&stringify_ids=true&cursor=" + cursor;
			}
			
			if(AssertUtils.isNotEmpty(this.userName)) {
				url = url + "&screen_name=" + this.userName;
			}
			
			WebRequest request = WebInvoker.getWebRequest(url, WebRequestMethod.GET);
			user.signRequest(request);
			WebResponse response = WebInvoker.executeSilently(request);
			
			if(response.getResponseCode() == 429) {
				// too many requests - let's wait
				String epoch = response.getHeaders().get("X-Rate-Limit-Reset");
				long millis = StringUtils.getLongValue(epoch, 0);

				if(AssertUtils.isEmpty(epoch) || millis == 0) {
					System.out.println("Waiting for a minute as we hit the rate limit on page: " + pageNumber);
					try {
						Thread.sleep(DateUtils.ONE_MINUTE);
					} catch (InterruptedException e) {
						// eat up
					}
					
					continue;
				}
				
				System.out.println("Waiting for " + (millis / DateUtils.ONE_SECOND) + " seconds as we hit rate limit on page: " + pageNumber);
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					// eat up
				}
				continue;
			}
			
			pageNumber++;
			String json = response.getContent();
			long nextCursor;
			if(!this.onlyIDs) {
				nextCursor = handleProfiles(json);
			} else {
				nextCursor = handleIDs(json);
			}
			
			if(nextCursor == 0) {
				break;
			}
			cursor = nextCursor;
		} while(true);
		
		if(!this.onlyIDs) {
			for(TwitterUserProfile profile : userList) {
				System.out.println(profile.getScreenName() + "," + profile.getName() + "," + profile.getDescription());
			}
		} else {
			for(String id : userIDs) {
				System.out.println(id);
			}
		}
	}

	private long handleIDs(String json) {
		TwitterIDList list = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(json, TwitterIDList.class);
		
		if(list != null && AssertUtils.isNotEmpty(list.ids)) {
			userIDs.addAll(list.ids);
		}
		
		return list.nextCursor;
	}

	private long handleProfiles(String json) {
		TwitterUserList list = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(json, TwitterUserList.class);
		
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
		
		return list.nextCursor;
	}

}
