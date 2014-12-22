package com.jacmobile.technews.networking;

import com.jacmobile.technews.networking.rss.entities.NewsItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by alex on 12/14/14.
 */
public class RssHandler extends DefaultHandler
{
    private StringBuffer buf;
    private ArrayList<NewsItem> feedItems;
    private NewsItem currentItem;
    private boolean parsingTitle;
    private boolean parsingDate;
    private boolean parsingLink;
    private boolean parsingDescription;

    private boolean inItem = false;
    private NewsItem item;


    public RssHandler() {
        //Initializes a new ArrayList that will hold all the generated RSS items.
        feedItems = new ArrayList<NewsItem>();
    }

    public ArrayList<NewsItem> getParsedItems()
    {
        return feedItems;
    }

    public NewsItem[] getArray()
    {
        NewsItem[] result = new NewsItem[feedItems.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = feedItems.get(i);
        return result;
    }
    //Called when an opening tag is reached, such as <item> or <title>
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("item"))
            currentItem = new NewsItem();
        else if (qName.equals("title"))
            parsingTitle = true;
        else if (qName.equals("link"))
            parsingLink = true;
        else if (qName.equals("pubDate"))
            parsingDate = true;
        else if (qName.equals("description"))
            parsingDescription = true;
        else if (qName.equals("media:thumbnail") || qName.equals("media:content") || qName.equals("image") || qName.equals("enclosure")) {
            if (attributes.getValue("url") != null) {
                currentItem.setImageUrl(attributes.getValue("url"));
            }
        }
    }

    //Called when a closing tag is reached, such as </item> or </title>
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("item")) {
            //End of an item so add the currentItem to the list of items.
            feedItems.add(currentItem);
            currentItem = null;
        } else if (qName.equals("title"))
            parsingTitle = false;
        else if (qName.equals("link"))
            parsingLink = false;
        else if (qName.equals("pubDate"))
            parsingDate = false;
        else if (qName.equals("description"))
            parsingDescription = false;
    }

    //Goes through character by character when parsing whats inside of a tag.
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentItem != null) {
            //If parsingTitle is true, then that means we are inside a <title> tag so the text is the title of an item.
            if (parsingTitle)
                currentItem.setTitle(new String(ch, start, length));
                //If parsingLink is true, then that means we are inside a <link> tag so the text is the link of an item.
            else if (parsingLink)
                currentItem.setLink(new String(ch, start, length));
                //If parsingDescription is true, then that means we are inside a <description> tag so the text is the description of an item.
            else if (parsingDescription)
                currentItem.setDescription(new String(ch, start, length));
            else if (parsingDate)
                currentItem.setDate(new String(ch, start, length));
        }
    }


//    //Called at the head of each new element
//    @Override
//    public void startElement(String uri, String name, String qName, Attributes attrs)
//    {
//        if ("channel".equals(name)) {
//            feedItems = new ArrayList<NewsItem>();
//        } else if ("item".equals(name)) {
//            item = new NewsItem();
//            inItem = true;
//        } else if ("title".equals(name) && inItem) {
//            buf = new StringBuffer();
//        } else if ("link".equals(name) && inItem) {
//            buf = new StringBuffer();
//        } else if ("description".equals(name) && inItem) {
//            buf = new StringBuffer();
//        } else if ("pubDate".equals(name) && inItem) {
//            buf = new StringBuffer();
//        } else if ("media:content".equals(name) && inItem) {
//            buf = new StringBuffer();
//        }
//    }
//
//    //Called at the tail of each element end
//    @Override
//    public void endElement(String uri, String name, String qName)
//    {
//        if ("item".equals(name)) {
//            feedItems.add(item);
//            inItem = false;
//        } else if ("title".equals(name) && inItem) {
//            item.title = buf.toString();
//        } else if ("link".equals(name) && inItem) {
//            item.link = buf.toString();
//        } else if ("description".equals(name) && inItem) {
//            item.description = buf.toString();
//        } else if ("pubDate".equals(name) && inItem) {
//            item.date = buf.toString();
//        } else if ("media:content".equals(name) && inItem) {
//            item.content = buf.toString();
//        }
//
//        buf = null;
//    }
//
//    //Called with character data inside elements
//    @Override
//    public void characters(char ch[], int start, int length)
//    {
//        if (buf != null) {
//            for (int i = start; i < start + length; i++) {
//                buf.append(ch[i]);
//            }
//        }
//    }
}
