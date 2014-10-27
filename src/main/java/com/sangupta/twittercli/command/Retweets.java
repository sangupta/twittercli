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

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.twittercli.TwitterCommand;
import com.sangupta.twittercli.domain.TwitterStatusUpdate;

@Command(name = "retweets", description = "Show retweets of the current or given user")
public class Retweets extends TwitterCommand {

	@Override
	public void doCommand() {
		WebRequest request = WebInvoker.getWebRequest("https://api.twitter.com/1.1/statuses/retweets_of_me.json", WebRequestMethod.GET);
		user.signRequest(request);
		WebResponse response = WebInvoker.executeSilently(request);
		TwitterStatusUpdate[] updates = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(response.getContent(), TwitterStatusUpdate[].class);
		
		if(AssertUtils.isEmpty(updates)) {
			System.out.println("No retweets found");
			return;
		}
		
		for(TwitterStatusUpdate update : updates) {
			System.out.println("  @" + update.user.getScreenName());
			System.out.println("  " + update.text);
			System.out.println("  " + update.createdAt);
			System.out.println();
		}
	}

}
