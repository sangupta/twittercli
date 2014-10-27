package com.sangupta.twittercli.command;

import io.airlift.command.Command;

import com.sangupta.jerry.oauth.domain.KeySecretPair;
import com.sangupta.jerry.oauth.domain.TokenAndUrl;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.ConsoleUtils;
import com.sangupta.satya.AuthConfig;
import com.sangupta.satya.AuthManager;
import com.sangupta.satya.AuthPermissions;
import com.sangupta.satya.AuthProvider;
import com.sangupta.satya.AuthenticatedUser;
import com.sangupta.twittercli.TwitterCLI;
import com.sangupta.twittercli.TwitterCLIConstants;

@Command(name = "authorize", description = "Authorize the application for use")
public class AuthorizeCommand implements Runnable {

	public void run() {
		String apiKey = TwitterCLI.LOCAL_STORE.get(TwitterCLIConstants.API_KEY);
		String apiSecret = TwitterCLI.LOCAL_STORE.get(TwitterCLIConstants.API_SECRET);
		
		if(AssertUtils.isEmpty(apiKey) || AssertUtils.isEmpty(apiSecret)) {
			System.out.println("Welcome! Before you can use t, you'll first need to register an");
			System.out.println("application with Twitter. Just follow the steps below:");
			System.out.println("  1. Sign in to the Twitter Application Management site and click");
			System.out.println("     \"Create New App\".");
			System.out.println("  2. Complete the required fields and submit the form.");
			System.out.println("     Note: Your application must have a unique name.");
			System.out.println("  3. Go to the Permissions tab of your application, and change the");
			System.out.println("     Access setting to \"Read, Write and Access direct messages\".");
			System.out.println("  4. Go to the API Keys tab to view the consumer key and secret,");
			System.out.println("     which you'll need to copy and paste below when prompted.");
			System.out.println();
			System.out.println("Press [Enter] to open the Twitter Developer site.");
			
			System.out.println();
			apiKey = ConsoleUtils.readLine("Enter your API key: ", true);
			if(AssertUtils.isEmpty(apiKey)) {
				System.out.println("No API key was entered... exiting!");
				return;
			}
			
			apiSecret = ConsoleUtils.readLine("Enter your API secret: ", true);
			if(AssertUtils.isEmpty(apiSecret)) {
				System.out.println("No API secret was entered... exiting!");
				return;
			}
			
			TwitterCLI.LOCAL_STORE.put(TwitterCLIConstants.API_KEY, apiKey);
			TwitterCLI.LOCAL_STORE.put(TwitterCLIConstants.API_SECRET, apiSecret);
		}
		
		System.out.println("In a moment, you will be directed to the Twitter app authorization page.");
		System.out.println("Perform the following steps to complete the authorization process:");
		System.out.println("  1. Sign in to Twitter.");
		System.out.println("  2. Press \"Authorize app\".");
		System.out.println("  3. Copy and paste the supplied PIN below when prompted.");
		System.out.println();
		System.out.println("Press [Enter] to open the Twitter app authorization page.");
		System.out.println();
		
		AuthConfig config = new AuthConfig();
		
		KeySecretPair twitter = new KeySecretPair(apiKey, apiSecret);
		config.addConfig(AuthProvider.Twitter, twitter, "");
		AuthManager.loadConfig(config);
		
		TokenAndUrl tokenAndUrl = AuthManager.getAuthRedirectURL(AuthProvider.Twitter, "oob", AuthPermissions.BASIC_PROFILE);
		System.out.println("Login URL: " + tokenAndUrl);
		
		String pin = ConsoleUtils.readLine("Enter the supplied PIN: ", true);
		AuthenticatedUser user = AuthManager.authenticateUser(AuthProvider.Twitter, pin, tokenAndUrl);
		if(user == null) {
			System.out.println("Unable to fetch user profile for provided authentication... exiting!");
			return;
		}
		
		TwitterCLI.LOCAL_STORE.put("user.key", user.getUserAccessPair().getKey());
		TwitterCLI.LOCAL_STORE.put("user.secret", user.getUserAccessPair().getSecret());
		TwitterCLI.LOCAL_STORE.put("user.name", user.getUserProfile().getDisplayName());
	}
	
}
