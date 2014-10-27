package com.sangupta.twittercli.domain;

import com.sangupta.satya.user.impl.TwitterUserProfile;

public class TwitterStatusUpdate {
	
	public String createdAt;
	
	public long id;
	
	public String idStr;
	
	public String text;
	
	public String source;
	
	public boolean truncated;
	
	public TwitterUserProfile user;
	
	public long retweetCount;
	
	public long favoriteCount;
	
	public boolean favorited;
	
	public boolean retweeted;
	
	public String lang;

}
