package com.online.shop.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class XmlAddressSaxParser {

    public void parseXml(String filePath) {
        try {
            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) {
                System.err.println(" XML file not found: " + filePath);
                return;
            }

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            AddressHandler handler = new AddressHandler();
            saxParser.parse(xmlFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner handler class
    static class AddressHandler extends DefaultHandler {

        private String currentElement = "";
        private String addressId, userId, street, city;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            currentElement = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            String value = new String(ch, start, length).trim();
            if (value.isEmpty()) return;

            switch (currentElement) {
                case "address_id" -> addressId = value;
                case "user_id" -> userId = value;
                case "street" -> street = value;
                case "city" -> city = value;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equals("address")) {
                System.out.printf(" Address [ID=%s, User=%s, Street=%s, City=%s]%n",
                        addressId, userId, street, city);
            }
            currentElement = "";
        }
    }
}
