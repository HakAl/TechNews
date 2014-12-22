package com.jacmobile.technews.networking;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

import com.jacmobile.technews.networking.rss.channels.ForbesTechChannel;
import com.jacmobile.technews.networking.rss.entities.Items;
import com.jacmobile.technews.networking.rss.entities.NewsItem;
import com.squareup.otto.Bus;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * http://www.forbes.com/technology/feed/
 * <p/>
 * Created by acorll on 12/21/14.
 */
@Singleton
public class ForbesTechRssHandler extends DefaultHandler
{
    @Inject Bus bus;

    private ForbesTechChannel channel;
    private Items items;
    private NewsItem item;

    public ForbesTechRssHandler()
    {
        items = new Items();
    }

    public void onStart()
    {
        bus.register(this);
    }

    public void onStop()
    {
        bus.unregister(this);
    }

    /**
     * <rss
     * xmlns:dc="http://purl.org/dc/elements/1.1/"
     * xmlns:media="http://search.yahoo.com/mrss/"
     * xmlns:forbes="http://forbes.com/rss/" version="2.0">
     * <channel>
     * <link>http://www.forbes.com/technology/feed2/</link>
     * <title>Forbes - Tech</title>
     * <description>Forbes - Tech</description>
     * <item>
     * <link>http://www.forbes.com/sites/erikkain/2014/12/20/last-minute-video-game-gift-guide-christmas-2014/</link>
     * <title>Last Minute Video Game Gift Guide For Slackers (Christmas 2014)</title>
     * <description>The best video games and video game accessories for the procrastinating holiday shopper.</description>
     * <dc:creator>Erik Kain</dc:creator>
     * <guid>http://www.forbes.com/sites/erikkain/2014/12/20/last-minute-video-game-gift-guide-christmas-2014/</guid>
     * <pubDate>Sat, 20 Dec 2014 20:40:00 GMT</pubDate>
     * <dc:date>2014-12-20T15:40:00Z</dc:date>
     * <media:content url="http://blogs-images.forbes.com/erikkain/files/2014/12/Santa-e1419108380202-300x169.jpg">
     * <media:thumbnail url="http://blogs-images.forbes.com/erikkain/files/2014/12/Santa-e1419108380202-300x169.jpg"/>
     * </media:content>
     * </item>
     */


    public ForbesTechChannel parse(String rss)
    {
        RootElement root = new RootElement("rss");
        Element chanElement = root.getChild("channel");
        Element chanTitle = chanElement.getChild("title");
        Element chanLink = chanElement.getChild("link");
        Element chanDescription = chanElement.getChild("description");
        Element chanLastBuildDate = chanElement.getChild("lastBuildDate");

        Element chanItem = chanElement.getChild("item");
        Element itemTitle = chanItem.getChild("title");
        Element itemDescription = chanItem.getChild("description");
        Element itemLink = chanItem.getChild("link");
        Element itemDate = chanItem.getChild("pubDate");

//        media:content
//          media:thumbnail

        Element itemContent = chanItem.getChild("media:content");
        Element itemImageUrl = itemContent.getChild("media:thumbnail");

        chanElement.setStartElementListener(new StartElementListener()
        {
            public void start(Attributes attributes)
            {
                channel = new ForbesTechChannel();
            }
        });

        // Listen for the end of a text element and set the text as our channel's title.
        chanTitle.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                channel.setTitle(body);
            }
        });
        chanLink.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                channel.setLink(body);
            }
        });
        chanDescription.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                channel.setDescription(body);
            }
        });
        chanLastBuildDate.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                channel.setLastBuildDate(body);
            }
        });

        // On every <item> tag occurrence we create a new Item object.
        chanItem.setStartElementListener(new StartElementListener()
        {
            public void start(Attributes attributes)
            {
                item = new NewsItem();
            }
        });

        // On every </item> tag occurrence we add the current Item object to the Items container.
        chanItem.setEndElementListener(new EndElementListener()
        {
            public void end()
            {
                items.add(item);
            }
        });

        itemTitle.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                item.setTitle(body);
            }
        });
        itemDescription.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                item.setDescription(body);
            }
        });
        itemLink.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                item.setLink(body);
            }
        });
        itemImageUrl.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                item.setImageUrl(body);
            }
        });
        itemDate.setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                item.setDate(body);
            }
        });

        // here we actually parse the InputStream and return the resulting Channel object.
        try {
            Xml.parse(rss, root.getContentHandler());
            busChannel(channel);
            Log.wtf("CHANNEL ITEMS", channel.getItems().size()+"");
            return channel;
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    void busChannel(ForbesTechChannel channel)
    {
        bus.register(this);
        bus.post(channel);
        bus.unregister(this);
    }

    @Override
    public void startDocument() throws SAXException
    {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException
    {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
    }
}
