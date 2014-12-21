package com.jacmobile.technews.networking;

import android.sax.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * http://www.forbes.com/technology/feed/
 *
 * Created by acorll on 12/21/14.
 */
public class ForbesTechRssHandler extends DefaultHandler
{
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
