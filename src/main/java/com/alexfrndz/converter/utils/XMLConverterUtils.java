package com.alexfrndz.converter.utils;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class XMLConverterUtils {
    public static void objectToXML(Jaxb2Marshaller xmlMarshaller, String fileName, Object graph) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            xmlMarshaller.marshal(graph, new StreamResult(fos));
        } finally {
            if (fos != null) {
                fos.close();
            }

        }
    }

    public static Object xmlToObject(Jaxb2Marshaller unmarshaller, String fileName) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            return unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
            if (fis != null) {
                fis.close();
            }

        }
    }

}
