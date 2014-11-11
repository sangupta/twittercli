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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebRequestMethod;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.print.ConsoleTable;
import com.sangupta.jerry.print.ConsoleTable.ConsoleTableLayout;
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
		
		ConsoleTable table = new ConsoleTable(ConsoleTableLayout.MULTI_LINE);
		table.addHeaderRow("S.No.", "Screen Name", "Full Name", "Bio");

		int count = 1;
		for(TwitterUserProfile profile : userList) {
			table.addRow(count++, profile.getScreenName(), profile.getName(), profile.getDescription());
		}
		
		table.setColumnSize(1, 15);
		table.setColumnSize(2, 25);
		table.setColumnSize(3, 40);
		
		table.write(System.out);
	}

}
