package com.jacmobile.technews.networking;

/**
 * Created by User on 7/12/2014.
 */
public class RssUrls
{
    public static String LINK_URL_KEY = "linkUrl";
    public static String RSS_URL_KEY = "rssUrl";

    public static String WIRED_RSS = "http://feeds.wired.com/wired/index";
    public static String NY_TIMES_TECH_RSS = "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
    public static String TECH_CRUNCH_ALL_RSS = "http://feeds.feedburner.com/TechCrunch/";
    public static String TECH_DIRT_RSS = "http://feeds.feedburner.com/techdirt/feed";
    public static String HACKER_NEWS_RSS = "http://feeds.feedburner.com/hacker-news-feed";
    public static String HACK_A_DAY_RSS = "http://feeds2.feedburner.com/hackaday/LgoM";

    private static String[] RSS_FEED_TITLES = {
                                                "NY Times Tech",
                                                "Tech Crunch",
                                                "Tech Dirt",
                                                "Hacker News",
                                                "Wired",
                                                "Hack A Day"};
    public static String[] getRssTitles()
    {
        return RSS_FEED_TITLES.clone();
    }
}
