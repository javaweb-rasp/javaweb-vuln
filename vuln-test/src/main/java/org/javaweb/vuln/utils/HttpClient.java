package org.javaweb.vuln.utils;

import org.apache.commons.io.IOUtils;
import org.javaweb.net.ChunkedInputStream;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpClient {

	/**
	 * 最大允许读取的Header长度
	 */
	private static final int MAX_HEADER_LENGTH = 1024 * 1000 * 4;

	private static String[] parseHost(String host, boolean isHttp) {
		String port = isHttp ? "80" : "443";

		String[] hostArray = host.split(":");

		if (hostArray.length == 2) {
			host = hostArray[0];
			port = hostArray[1];
		}

		return new String[]{host, port};
	}

	private static Map<String, String> parseHeader(DataInputStream dis) throws IOException {
		String              str;
		Map<String, String> header = new LinkedHashMap<String, String>();

		// 解析Header
		while ((str = dis.readLine()) != null && !"".equals(str)) {
			String[] strArray = str.split(":\\s+");

			if (strArray.length == 2) {
				header.put(strArray[0], strArray[1]);
			}
		}

		return header;
	}

	/**
	 * 解析body，自动替换\n为\r\n
	 *
	 * @param dis 输入流
	 * @return body
	 * @throws IOException IO异常
	 */
	public static byte[] parseBody(DataInputStream dis) throws IOException {
		int a;

		// 当前index的前一个字符
		int previous = 0;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		while ((a = dis.read()) != -1) {
			if (a == '\n' && previous != '\r') {
				// 替换\n为\r\n
				baos.write('\r');
			}

			baos.write(a);

			previous = a;
		}

		return baos.toByteArray();
	}

	/**
	 * 代理请求
	 *
	 * @param req    协议包
	 * @param isHttp 是否是HTTP协议
	 * @param data   响应Map
	 * @throws IOException IO异常
	 */
	public static void proxyRequest(String req, boolean isHttp, Map<String, Object> data) throws IOException {
		byte[]          bytes = req.getBytes();
		DataInputStream dis   = new DataInputStream(new ByteArrayInputStream(bytes));
		String          line  = dis.readLine();

		if (line != null) {
			Map<String, String> header = parseHeader(dis);
			byte[]              body   = parseBody(dis);
			String[]            hosts  = parseHost(header.get("Host"), isHttp);

			String contentLen = header.get("Content-Length");

			// 矫正Content-Length
			if (contentLen != null && Integer.parseInt(contentLen) > 0) {
				header.put("Content-Length", String.valueOf(body.length));
			}

			Socket socket;

			if (isHttp) {
				socket = new Socket(hosts[0], Integer.parseInt(hosts[1]));
			} else {
				SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				socket = factory.createSocket(hosts[0], Integer.parseInt(hosts[1]));
			}

			OutputStream out = socket.getOutputStream();

			// 写入协议头
			out.write((line + "\r\n").getBytes());

			// 写入Header
			for (String key : header.keySet()) {
				out.write((key + ": " + header.get(key) + "\r\n").getBytes());
			}

			// 写入Header换行
			out.write("\r\n".getBytes());

			// 写入body
			if (body.length > 0) {
				out.write(body);
			}

			out.flush();

//			if (isHttp) {
//				socket.shutdownOutput();
//			}

			InputStream         responseIn     = socket.getInputStream();
			DataInputStream     responseDis    = new DataInputStream(responseIn);
			String              status         = responseDis.readLine();
			Map<String, String> responseHeader = parseHeader(responseDis);
			InputStream         respIn         = parseResponseInputStream(responseIn, responseHeader);

			data.put("status", status);
			data.put("header", responseHeader);
			data.put("body", IOUtils.toString(respIn));

			socket.close();
		}
	}

	private static InputStream parseResponseInputStream(InputStream responseIn, Map<String, String> responseHeader) throws IOException {
		String      transferEncoding      = responseHeader.get("Transfer-Encoding");
		String      responseContentLength = responseHeader.get("Content-Length");
		InputStream in                    = new HttpInputStream(responseContentLength, responseIn);

		// 处理chunked
		if ("chunked".equalsIgnoreCase(transferEncoding)) {
			in = new ChunkedInputStream(new DataInputStream(in));

			return in;
		}

//		String contentEncoding = responseHeader.get("Content-Encoding");
//
//		if ("gzip".equalsIgnoreCase(contentEncoding)) {
//			in = new GZIPInputStream(in);
//		} else if ("deflate".equalsIgnoreCase(contentEncoding)) {
//			in = new DeflaterInputStream(in);
//		}

		return in;
	}

	static class HttpInputStream extends InputStream {

		private final InputStream in;

		private long maxLength = -1;

		private long readLength = 0;

		public HttpInputStream(String contentLength, InputStream in) {
			if (contentLength != null) {
				maxLength = Long.parseLong(contentLength);
			}

			this.in = in;
		}

		@Override
		public int read() throws IOException {
			if (maxLength == readLength) {
				return -1;
			}

			readLength++;
			return in.read();
		}

	}

}
