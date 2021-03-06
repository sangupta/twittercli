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
