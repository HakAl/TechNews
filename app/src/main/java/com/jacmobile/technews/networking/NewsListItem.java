package com.jacmobile.technews.networking;

/**
 *  http://www.forbes.com/technology/feed/
 *    <item>
        <link>http://www.forbes.com/sites/tiffanypham/2014/12/13/how-she-did-it-soraya-darabi-and-maxine-bedat-launching-zady-the-whole-foods-of-fashion/</link>
        <title>How She Did It: Soraya Darabi and Maxine Bédat on Launching Zady, the Whole Foods of Fashion</title>
        <description>Soraya Darabi &amp;amp; Maxine B&amp;eacute;dat are the Founders&amp;nbsp;of Zady, also known as &quot;The Whole Foods of Fashion.&quot; By focusing on honesty and quality in manufacturing, Zady provides unparalleled access to&amp;nbsp;stylish, yet&amp;nbsp;sustainably produced products made from high-quality raw materials. For&amp;nbsp;each product, Zady tells the story&amp;nbsp;of how it was made, down to how the&amp;nbsp;raw materials were acquired, aiming to&amp;nbsp;facilitate&amp;nbsp;a&amp;nbsp;closer connection&amp;nbsp;between people and our&amp;nbsp;belongings.</description>
        <dc:creator>Tiffany Pham</dc:creator>
        <guid>http://www.forbes.com/sites/tiffanypham/2014/12/13/how-she-did-it-soraya-darabi-and-maxine-bedat-launching-zady-the-whole-foods-of-fashion/</guid>
        <pubDate>Sat, 13 Dec 2014 22:15:00 GMT</pubDate>
        <dc:date>2014-12-13T17:15:00Z</dc:date>
        <media:content url="http://blogs-images.forbes.com/tiffanypham/files/2014/12/Soraya-and-Maxine-approved-for-press.jpg">
        <media:thumbnail url="http://blogs-images.forbes.com/tiffanypham/files/2014/12/Soraya-and-Maxine-approved-for-press.jpg"/>
         </media:content>
      </item>

 http://feeds.bbci.co.uk/news/technology/rss.xml

 <title>BBC News - Technology</title>
 <link>http://www.bbc.co.uk/news/technology/#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>
 <description>The latest stories from the Technology section of the BBC News web site.</description>
 <language>en-gb</language>
 <lastBuildDate>Fri, 12 Dec 2014 17:49:39 GMT</lastBuildDate>
 <copyright>Copyright: (C) British Broadcasting Corporation, see http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.</copyright>
 <image>
 <url>http://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif</url>
 <title>BBC News - Technology</title>
 <link>http://www.bbc.co.uk/news/technology/#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>
 <width>120</width>
 <height>60</height>
 </image>
 <ttl>15</ttl>
 <atom:link href="http://feeds.bbci.co.uk/news/technology/rss.xml" rel="self" type="application/rss+xml"/>
 <item>
 <title>Facebook ponders 'dislike' function</title>
 <description>Facebook is thinking about adding a way to "dislike" posts on its site, something users have requested in their droves.</description>
 <link>http://www.bbc.co.uk/news/technology-30447565#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>
 <guid isPermaLink="false">http://www.bbc.co.uk/news/technology-30447565</guid>
 <pubDate>Fri, 12 Dec 2014 12:54:40 GMT</pubDate>
 <media:thumbnail width="66" height="49" url="http://news.bbcimg.co.uk/media/images/79694000/jpg/_79694144_144539910(1).jpg"/>
 <media:thumbnail width="144" height="81" url="http://news.bbcimg.co.uk/media/images/79694000/jpg/_79694145_144539910(1).jpg"/>
 </item>
 * Created by alex on 12/13/14.
 */
public class NewsListItem
{
    private String url;
    private String imageUrl;
    private String title;
    private String description;
    private String date;
    private int imageWidth;
    private int imageHeight;
}
