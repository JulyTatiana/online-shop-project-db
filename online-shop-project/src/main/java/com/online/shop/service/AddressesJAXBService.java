package com.online.shop.service;

import com.online.shop.model.AddressesList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class AddressesJAXBService {

    private static final String XML_PATH = "src/main/resources/xml/addresses.xml";
    private static final String XSD_PATH = "src/main/resources/xml/addresses.xsd";

    public AddressesList loadAddresses() {
        try {
            JAXBContext context = JAXBContext.newInstance(AddressesList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Validate with XSD before mapping
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(XSD_PATH));
            unmarshaller.setSchema(schema);

            AddressesList list = (AddressesList) unmarshaller.unmarshal(new File(XML_PATH));
            System.out.println("✅ XML validated successfully with XSD!");
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
