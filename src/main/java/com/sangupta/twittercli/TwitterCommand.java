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

package com.sangupta.twittercli;

import com.sangupta.satya.AuthProvider;
import com.sangupta.satya.AuthenticatedUser;
import com.sangupta.satya.user.PreAuthenticatedUser;

public abstract class TwitterCommand implements Runnable {
	
	protected AuthenticatedUser user = null;
	
	public void run() {
		String apiKey = TwitterCLI.LOCAL_STORE.get(TwitterCLIConstants.API_KEY);
		String apiSecret = TwitterCLI.LOCAL_STORE.get(TwitterCLIConstants.API_SECRET);
		String token = TwitterCLI.LOCAL_STORE.get("user.key");
		String secret = TwitterCLI.LOCAL_STORE.get("user.secret");
		
		this.user = new PreAuthenticatedUser(AuthProvider.Twitter, apiKey, apiSecret, token, secret);
		
		doCommand();
	}
	
	public abstract void doCommand();

	protected String getScreenName(String user) {
		if(user.startsWith("@")) {
			return user.substring(1);
		}
		
		return user;
	}

}
