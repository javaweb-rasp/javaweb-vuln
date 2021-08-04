package org.javaweb.vuls.utils;

import org.apache.commons.codec.binary.Base64;
import org.javaweb.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class CompressUtils {

	public static GZIPInputStream gzipDecompress(String str) throws IOException {
		byte[]               bytes = Base64.decodeBase64(str);
		ByteArrayInputStream in    = new ByteArrayInputStream(bytes);

		return new GZIPInputStream(in);
	}

	public static void main(String[] args) {
		String str = "H4sIAAAAAAAAAKtWLy1OLcpLzE1Vt1JQr6xS11FQL0gsLi7PL0oBiRgaGZuYmqnXAgBhg6EBKAAAAA==";

		try {
			System.out.println(IOUtils.toString(gzipDecompress(str)));;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
