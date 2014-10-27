package com.sangupta.twittercli.domain;

import com.sangupta.satya.user.impl.TwitterUserProfile;

public class TwitterUserList {
	
	public TwitterUserProfile[] users;
	
	public long nextCursor;
	
	public String nextCursorStr;
	
	public long previousCursor;
	
	public String previousCursorStr;

}
