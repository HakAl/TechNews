package com.jacmobile.technews.networking;

/**
 * Created by User on 7/12/2014.
 */
public class RssUrls
{
    //normal handler media:content
    private static final String FORBES_TECH = "http://www.forbes.com/technology/feed/";
    private static final String NY_TIMES_TECH = "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
    private static final String PC_WORLD = "http://feeds.pcworld.com/pcworld/latestnews";
    private static final String TECH_WORLD = "http://rss.feedsportal.com/c/270/f/3547/index.rss";
    private static final String TECH_REPUBLIC = "http://www.techrepublic.com/rssfeeds/articles/latest/";
    private static final String ABC_TECH = "http://feeds.abcnews.com/abcnews/technologyheadlines";
    private static final String CNN_TECH = "http://rss.cnn.com/rss/cnn_tech.rss";
    private static String[] RSS_FEED_URLS = { FORBES_TECH, NY_TIMES_TECH, PC_WORLD, TECH_WORLD, TECH_REPUBLIC, ABC_TECH, CNN_TECH };

    public static String[] getRssUrls()
    {
        return RSS_FEED_URLS.clone();
    }

    //Image link in description
    String WIRED = "http://feeds.wired.com/wired/index";
    String WIRED_UK = "http://www.wired.co.uk/rss";

    //UNTESTED
//    public static String WIRED_RSS = "http://feeds.wired.com/wired/index";
//    public static String NY_TIMES_TECH_RSS = "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
//    public static String TECH_CRUNCH_ALL_RSS = "http://feeds.feedburner.com/TechCrunch/";
//    public static String TECH_DIRT_RSS = "http://feeds.feedburner.com/techdirt/feed";
//    public static String HACKER_NEWS_RSS = "http://feeds.feedburner.com/hacker-news-feed";
//    public static String HACK_A_DAY_RSS = "http://feeds2.feedburner.com/hackaday/LgoM";
}
