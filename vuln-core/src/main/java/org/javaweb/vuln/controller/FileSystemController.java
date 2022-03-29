package org.javaweb.vuln.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

/**
 * 文件系统漏洞测试
 * Creator: yz
 * Date: 2020-05-03
 */
@Controller
@RequestMapping("/FileSystem/")
public class FileSystemController {

	@ResponseBody
	@RequestMapping("/fileInputStreamReadFile.do")
	public ResponseEntity<byte[]> fileInputStreamReadFile(String file) throws IOException {
		FileInputStream fis     = new FileInputStream(file);
		byte[]          content = IOUtils.toByteArray(fis);

		return new ResponseEntity<byte[]>(content, OK);
	}

	@ResponseBody
	@RequestMapping("/randomAccessFileReadFile.do")
	public ResponseEntity<byte[]> randomAccessFileReadFile(String file) throws IOException {
		int                   a     = -1;
		byte[]                bytes = new byte[1024];
		ByteArrayOutputStream baos  = new ByteArrayOutputStream();
		RandomAccessFile      raf   = new RandomAccessFile(file, "r");

		while ((a = raf.read(bytes)) != -1) {
			baos.write(bytes, 0, a);
		}

		return new ResponseEntity<byte[]>(baos.toByteArray(), OK);
	}

	@ResponseBody
	@RequestMapping("/filesReadAllBytes.do")
	public ResponseEntity<byte[]> filesReadAllBytes(String file) throws Exception {
		Class<?> pathsClass = Class.forName("java.nio.file.Paths");
		Class<?> pathClass  = Class.forName("java.nio.file.Path");
		Class<?> filesClass = Class.forName("java.nio.file.Files");

		File   filePath = new File(file);
		Object path     = pathsClass.getMethod("get", URI.class).invoke(null, filePath.toURI());
		byte[] bytes    = (byte[]) filesClass.getMethod("readAllBytes", pathClass).invoke(null, path);

		return new ResponseEntity<byte[]>(bytes, OK);
	}

	@ResponseBody
	@RequestMapping("/fileOutStreamWriteFile.do")
	public Map<String, String> fileOutStreamWriteFile(String file, String content) throws IOException {
		File                filePath = new File(file);
		Map<String, String> data     = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content.getBytes("UTF-8"));
		fos.flush();
		fos.close();

		return data;
	}

	@ResponseBody
	@RequestMapping("/randomAccessFileWriteFile.do")
	public Map<String, String> randomAccessFileWriteFile(String file, String content) throws IOException {
		File                filePath = new File(file);
		Map<String, String> data     = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());

		RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
		raf.write(content.getBytes("UTF-8"));
		raf.close();

		return data;
	}

	@ResponseBody
	@RequestMapping("/filesWrite.do")
	public Map<String, String> filesWrite(String file, String content) throws Exception {
		Class<?> pathsClass           = Class.forName("java.nio.file.Paths");
		Class<?> pathClass            = Class.forName("java.nio.file.Path");
		Class<?> filesClass           = Class.forName("java.nio.file.Files");
		Class<?> openOptionClass      = Class.forName("[Ljava.nio.file.OpenOption;");
		Class<?> openOptionArrayClass = Class.forName("java.nio.file.OpenOption");
		Object   openOptionArray      = Array.newInstance(openOptionArrayClass, 0);

		File                filePath = new File(file);
		Map<String, String> data     = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());

		byte[] bytes       = content.getBytes("UTF-8");
		Object path        = pathsClass.getMethod("get", URI.class).invoke(null, filePath.toURI());
		Method writeMethod = filesClass.getMethod("write", pathClass, byte[].class, openOptionClass);
		writeMethod.invoke(null, path, bytes, openOptionArray);

		return data;
	}

	@ResponseBody
	@RequestMapping("/deleteFile.do")
	public Map<String, String> deleteFile(String file) throws IOException {
		File filePath = new File(file);

		if (!filePath.exists()) {
			filePath.createNewFile();
		}

		boolean delete = filePath.delete();

		Map<String, String> data = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());
		data.put("msg", "删除" + (delete ? "成功！" : "失败！"));

		return data;
	}

	@ResponseBody
	@RequestMapping("/renameTo.do")
	public Map<String, String> renameTo(String file, String dest) throws IOException {
		File filePath = new File(file);
		File destFile = new File(dest);

		if (!filePath.exists()) {
			filePath.createNewFile();
		}

		boolean renamed = filePath.renameTo(destFile);

		Map<String, String> data = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());
		data.put("destPath", destFile.getAbsolutePath());
		data.put("msg", "文件重命名" + (renamed ? "成功！" : "失败！"));

		return data;
	}

	@ResponseBody
	@RequestMapping("/filesCopy.do")
	public Map<String, String> filesCopy(String file, String dest) throws Exception {
		Class<?> pathsClass           = Class.forName("java.nio.file.Paths");
		Class<?> pathClass            = Class.forName("java.nio.file.Path");
		Class<?> filesClass           = Class.forName("java.nio.file.Files");
		Class<?> copyOptionClass      = Class.forName("[Ljava.nio.file.CopyOption;");
		Class<?> copyOptionArrayClass = Class.forName("java.nio.file.CopyOption");
		Object   copyOptionArray      = Array.newInstance(copyOptionArrayClass, 0);

		File filePath = new File(file);
		File destFile = new File(dest);

		if (!filePath.exists()) {
			filePath.createNewFile();
		}

		if (destFile.exists()) {
			destFile.delete();
		}

		Object sourcePath = pathsClass.getMethod("get", URI.class).invoke(null, filePath.toURI());
		Object destPath   = pathsClass.getMethod("get", URI.class).invoke(null, destFile.toURI());
		Method copyMethod = filesClass.getMethod("copy", pathClass, pathClass, copyOptionClass);
		Object path       = copyMethod.invoke(null, sourcePath, destPath, copyOptionArray);

		Map<String, String> data = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());
		data.put("destPath", destFile.getAbsolutePath());
		data.put("msg", "文件复制成功！");

		return data;
	}

	@ResponseBody
	@RequestMapping("/listFile.do")
	public Map<String, String[]> listFile(String dir) {
		String[] files = new File(dir).list();

		Map<String, String[]> data = new LinkedHashMap<String, String[]>();
		data.put("files", files);

		return data;
	}

}
