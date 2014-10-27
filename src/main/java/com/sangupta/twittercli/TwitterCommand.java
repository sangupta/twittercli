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

}
