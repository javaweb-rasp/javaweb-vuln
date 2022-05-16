package org.javaweb.vuln.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_XML_VALUE;

/**
 * 文件系统漏洞测试
 * Creator: yz
 * Date: 2020-05-03
 */
@RestController
@RequestMapping("/FileSystem/")
public class FileSystemController {

	private ResponseEntity<byte[]> fileInputStreamReadFile(String file) throws IOException {
		FileInputStream fis     = new FileInputStream(file);
		byte[]          content = IOUtils.toByteArray(fis);

		return new ResponseEntity<byte[]>(content, OK);
	}

	private ResponseEntity<byte[]> randomAccessFileReadFile(String file) throws IOException {
		int                   a     = -1;
		byte[]                bytes = new byte[1024];
		ByteArrayOutputStream baos  = new ByteArrayOutputStream();
		RandomAccessFile      raf   = new RandomAccessFile(file, "r");

		while ((a = raf.read(bytes)) != -1) {
			baos.write(bytes, 0, a);
		}

		return new ResponseEntity<byte[]>(baos.toByteArray(), OK);
	}

	private ResponseEntity<byte[]> filesReadAllBytes(String file) throws Exception {
		Class<?> pathsClass = Class.forName("java.nio.file.Paths");
		Class<?> pathClass  = Class.forName("java.nio.file.Path");
		Class<?> filesClass = Class.forName("java.nio.file.Files");

		File   filePath = new File(file);
		Object path     = pathsClass.getMethod("get", URI.class).invoke(null, filePath.toURI());
		byte[] bytes    = (byte[]) filesClass.getMethod("readAllBytes", pathClass).invoke(null, path);

		return new ResponseEntity<byte[]>(bytes, OK);
	}

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

	public Map<String, String> randomAccessFileWriteFile(String file, String content) throws IOException {
		File                filePath = new File(file);
		Map<String, String> data     = new LinkedHashMap<String, String>();
		data.put("path", filePath.getAbsolutePath());

		RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
		raf.write(content.getBytes("UTF-8"));
		raf.close();

		return data;
	}

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

	public Map<String, String> fileDelete(String file) throws IOException {
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

	public Map<String, String[]> listFile(String dir) {
		String[] files = new File(dir).list();

		Map<String, String[]> data = new LinkedHashMap<String, String[]>();
		data.put("files", files);

		return data;
	}

	@GetMapping("/get/fileInputStreamReadFile.do")
	public ResponseEntity<byte[]> getFileInputStreamReadFile(String file) throws IOException {
		return fileInputStreamReadFile(file);
	}

	@PostMapping("/post/fileInputStreamReadFile.do")
	public ResponseEntity<byte[]> postFileInputStreamReadFile(String file) throws IOException {
		return fileInputStreamReadFile(file);
	}

	@PostMapping("/cookie/fileInputStreamReadFile.do")
	public ResponseEntity<byte[]> cookieFileInputStreamReadFile(
			@CookieValue(name = "file") String file) throws IOException {

		return fileInputStreamReadFile(file);
	}

	@PostMapping("/header/fileInputStreamReadFile.do")
	public ResponseEntity<byte[]> headerFileInputStreamReadFile(
			@RequestHeader(name = "file") String file) throws IOException {

		return fileInputStreamReadFile(file);
	}

	@PostMapping(value = "/xml/fileInputStreamReadFile.do", consumes = APPLICATION_XML_VALUE)
	public ResponseEntity<byte[]> xmlFileInputStreamReadFile(@RequestBody Map<String, Object> map) throws IOException {
		return fileInputStreamReadFile((String) map.get("file"));
	}

	@PostMapping(value = "/json/fileInputStreamReadFile.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> jsonFileInputStreamReadFile(@RequestBody Map<String, Object> map) throws IOException {
		return fileInputStreamReadFile((String) map.get("file"));
	}

	@PostMapping("/form/fileInputStreamReadFile.do")
	public ResponseEntity<byte[]> multipartFileInputStreamReadFile(MultipartFile file) throws IOException {
		return fileInputStreamReadFile(file.getOriginalFilename());
	}

	@GetMapping("/get/randomAccessFileReadFile.do")
	public ResponseEntity<byte[]> getRandomAccessFileReadFile(String file) throws IOException {
		return randomAccessFileReadFile(file);
	}

	@PostMapping("/post/randomAccessFileReadFile.do")
	public ResponseEntity<byte[]> postRandomAccessFileReadFile(String file) throws IOException {
		return randomAccessFileReadFile(file);
	}

	@PostMapping("/cookie/randomAccessFileReadFile.do")
	public ResponseEntity<byte[]> cookieRandomAccessFileReadFile(
			@CookieValue(name = "file") String file) throws IOException {

		return randomAccessFileReadFile(file);
	}

	@PostMapping("/header/randomAccessFileReadFile.do")
	public ResponseEntity<byte[]> headerRandomAccessFileReadFile(
			@RequestHeader(name = "file") String file) throws IOException {

		return randomAccessFileReadFile(file);
	}

	@PostMapping(value = "/xml/randomAccessFileReadFile.do", consumes = APPLICATION_XML_VALUE)
	public ResponseEntity<byte[]> xmlRandomAccessFileReadFile(
			@RequestBody Map<String, Object> map) throws IOException {

		return randomAccessFileReadFile((String) map.get("file"));
	}

	@PostMapping(value = "/json/randomAccessFileReadFile.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> jsonRandomAccessFileReadFile(
			@RequestBody Map<String, Object> map) throws IOException {

		return randomAccessFileReadFile((String) map.get("file"));
	}

	@PostMapping("/form/randomAccessFileReadFile.do")
	public ResponseEntity<byte[]> multipartRandomAccessFileReadFile(MultipartFile file) throws IOException {
		return randomAccessFileReadFile(file.getOriginalFilename());
	}

	@GetMapping("/get/filesReadAllBytes.do")
	public ResponseEntity<byte[]> getFilesReadAllBytes(String file) throws Exception {
		return filesReadAllBytes(file);
	}

	@PostMapping("/post/filesReadAllBytes.do")
	public ResponseEntity<byte[]> postFilesReadAllBytes(String file) throws Exception {
		return filesReadAllBytes(file);
	}

	@PostMapping("/cookie/filesReadAllBytes.do")
	public ResponseEntity<byte[]> cookieFilesReadAllBytes(@CookieValue(name = "file") String file) throws Exception {
		return filesReadAllBytes(file);
	}

	@PostMapping("/header/filesReadAllBytes.do")
	public ResponseEntity<byte[]> headerFilesReadAllBytes(@RequestHeader(name = "file") String file) throws Exception {
		return filesReadAllBytes(file);
	}

	@PostMapping(value = "/xml/filesReadAllBytes.do", consumes = APPLICATION_XML_VALUE)
	public ResponseEntity<byte[]> xmlFilesReadAllBytes(@RequestBody Map<String, Object> map) throws Exception {
		return filesReadAllBytes((String) map.get("file"));
	}

	@PostMapping(value = "/json/filesReadAllBytes.do", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> jsonFilesReadAllBytes(@RequestBody Map<String, Object> map) throws Exception {
		return filesReadAllBytes((String) map.get("file"));
	}

	@PostMapping("/form/filesReadAllBytes.do")
	public ResponseEntity<byte[]> multipartFilesReadAllBytes(MultipartFile file) throws Exception {
		return filesReadAllBytes(file.getOriginalFilename());
	}

	@GetMapping("/get/fileOutStreamWriteFile.do")
	public Map<String, String> getFileOutStreamWriteFile(String file, String content) throws Exception {
		return fileOutStreamWriteFile(file, content);
	}

	@PostMapping("/post/fileOutStreamWriteFile.do")
	public Map<String, String> postFileOutStreamWriteFile(String file, String content) throws Exception {
		return fileOutStreamWriteFile(file, content);
	}

	@PostMapping("/cookie/fileOutStreamWriteFile.do")
	public Map<String, String> cookieFileOutStreamWriteFile(
			@CookieValue(name = "file") String file,
			@CookieValue(name = "content") String content) throws Exception {

		return fileOutStreamWriteFile(file, content);
	}

	@PostMapping("/header/fileOutStreamWriteFile.do")
	public Map<String, String> headerFileOutStreamWriteFile(
			@RequestHeader(name = "file") String file,
			@RequestHeader(name = "content") String content) throws Exception {

		return fileOutStreamWriteFile(file, content);
	}

	@PostMapping(value = "/xml/fileOutStreamWriteFile.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String> xmlFileOutStreamWriteFile(@RequestBody Map<String, Object> map) throws Exception {
		return fileOutStreamWriteFile((String) map.get("file"), (String) map.get("content"));
	}

	@PostMapping(value = "/json/fileOutStreamWriteFile.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> jsonFileOutStreamWriteFile(@RequestBody Map<String, Object> map) throws Exception {
		return fileOutStreamWriteFile((String) map.get("file"), (String) map.get("content"));
	}

	@PostMapping("/form/fileOutStreamWriteFile.do")
	public Map<String, String> multipartFileOutStreamWriteFile(MultipartFile file) throws Exception {
		return fileOutStreamWriteFile(file.getOriginalFilename(), new String(file.getBytes()));
	}

	@GetMapping("/get/randomAccessFileWriteFile.do")
	public Map<String, String> getRandomAccessFileWriteFile(String file, String content) throws Exception {
		return randomAccessFileWriteFile(file, content);
	}

	@PostMapping("/post/randomAccessFileWriteFile.do")
	public Map<String, String> postRandomAccessFileWriteFile(String file, String content) throws Exception {
		return randomAccessFileWriteFile(file, content);
	}

	@PostMapping("/cookie/randomAccessFileWriteFile.do")
	public Map<String, String> cookieRandomAccessFileWriteFile(
			@CookieValue(name = "file") String file,
			@CookieValue(name = "content") String content) throws Exception {

		return randomAccessFileWriteFile(file, content);
	}

	@PostMapping("/header/randomAccessFileWriteFile.do")
	public Map<String, String> headerRandomAccessFileWriteFile(
			@RequestHeader(name = "file") String file,
			@RequestHeader(name = "content") String content) throws Exception {

		return randomAccessFileWriteFile(file, content);
	}

	@PostMapping(value = "/xml/randomAccessFileWriteFile.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String> xmlRandomAccessFileWriteFile(@RequestBody Map<String, Object> map) throws Exception {
		return randomAccessFileWriteFile((String) map.get("file"), (String) map.get("content"));
	}

	@PostMapping(value = "/json/randomAccessFileWriteFile.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> jsonRandomAccessFileWriteFile(@RequestBody Map<String, Object> map) throws Exception {
		return randomAccessFileWriteFile((String) map.get("file"), (String) map.get("content"));
	}

	@PostMapping("/form/randomAccessFileWriteFile.do")
	public Map<String, String> multipartRandomAccessFileWriteFile(MultipartFile file) throws Exception {
		return randomAccessFileWriteFile(file.getOriginalFilename(), new String(file.getBytes()));
	}

	@GetMapping("/get/filesWrite.do")
	public Map<String, String> getfilesWrite(String file, String content) throws Exception {
		return filesWrite(file, content);
	}

	@PostMapping("/post/filesWrite.do")
	public Map<String, String> postfilesWrite(String file, String content) throws Exception {
		return filesWrite(file, content);
	}

	@PostMapping("/cookie/filesWrite.do")
	public Map<String, String> cookiefilesWrite(
			@CookieValue(name = "file") String file,
			@CookieValue(name = "content") String content) throws Exception {

		return filesWrite(file, content);
	}

	@PostMapping("/header/filesWrite.do")
	public Map<String, String> headerfilesWrite(
			@RequestHeader(name = "file") String file,
			@RequestHeader(name = "content") String content) throws Exception {

		return filesWrite(file, content);
	}

	@PostMapping(value = "/xml/filesWrite.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String> xmlFilesWrite(@RequestBody Map<String, Object> map) throws Exception {
		return filesWrite((String) map.get("file"), (String) map.get("content"));
	}

	@PostMapping(value = "/json/filesWrite.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> jsonFilesWrite(@RequestBody Map<String, Object> map) throws Exception {
		return filesWrite((String) map.get("file"), (String) map.get("content"));
	}

	@PostMapping("/form/filesWrite.do")
	public Map<String, String> multipartfilesWrite(MultipartFile file) throws Exception {
		return filesWrite(file.getOriginalFilename(), new String(file.getBytes()));
	}

	@GetMapping("/get/deleteFile.do")
	public Map<String, String> getDeleteFile(String file) throws IOException {
		return fileDelete(file);
	}

	@PostMapping("/post/deleteFile.do")
	public Map<String, String> postDeleteFile(String file) throws IOException {
		return fileDelete(file);
	}

	@PostMapping("/cookie/deleteFile.do")
	public Map<String, String> cookieDeleteFile(@CookieValue(name = "file") String file) throws IOException {
		return fileDelete(file);
	}

	@PostMapping("/header/deleteFile.do")
	public Map<String, String> headerDeleteFile(@RequestHeader(name = "file") String file) throws IOException {
		return fileDelete(file);
	}

	@PostMapping(value = "/xml/deleteFile.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String> xmlDeleteFile(@RequestBody Map<String, Object> map) throws IOException {
		return fileDelete((String) map.get("file"));
	}

	@PostMapping(value = "/json/deleteFile.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> jsonDeleteFile(@RequestBody Map<String, Object> map) throws IOException {
		return fileDelete((String) map.get("file"));
	}

	@PostMapping("/form/deleteFile.do")
	public Map<String, String> multipartDeleteFile(MultipartFile file) throws IOException {
		return fileDelete(file.getOriginalFilename());
	}

	@GetMapping("/get/renameTo.do")
	public Map<String, String> getRenameTo(String file, String dest) throws Exception {
		return renameTo(file, dest);
	}

	@PostMapping("/post/renameTo.do")
	public Map<String, String> postRenameTo(String file, String dest) throws Exception {
		return renameTo(file, dest);
	}

	@PostMapping("/cookie/renameTo.do")
	public Map<String, String> cookieRenameTo(
			@CookieValue(name = "file") String file,
			@CookieValue(name = "dest") String dest) throws Exception {

		return renameTo(file, dest);
	}

	@PostMapping("/header/renameTo.do")
	public Map<String, String> headerRenameTo(
			@RequestHeader(name = "file") String file,
			@RequestHeader(name = "dest") String dest) throws Exception {

		return renameTo(file, dest);
	}

	@PostMapping(value = "/xml/renameTo.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String> xmlRenameTo(@RequestBody Map<String, Object> map) throws Exception {
		return renameTo((String) map.get("file"), (String) map.get("dest"));
	}

	@PostMapping(value = "/json/renameTo.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> jsonRenameTo(@RequestBody Map<String, Object> map) throws Exception {
		return renameTo((String) map.get("file"), (String) map.get("dest"));
	}

	@PostMapping("/form/renameTo.do")
	public Map<String, String> multipartRenameTo(MultipartFile file) throws Exception {
		return renameTo(file.getOriginalFilename(), new String(file.getBytes()));
	}

	@GetMapping("/get/filesCopy.do")
	public Map<String, String> getFilesCopy(String file, String dest) throws Exception {
		return filesCopy(file, dest);
	}

	@PostMapping("/post/filesCopy.do")
	public Map<String, String> postFilesCopy(String file, String dest) throws Exception {
		return filesCopy(file, dest);
	}

	@PostMapping("/cookie/filesCopy.do")
	public Map<String, String> cookieFilesCopy(
			@CookieValue(name = "file") String file,
			@CookieValue(name = "dest") String dest) throws Exception {

		return filesCopy(file, dest);
	}

	@PostMapping("/header/filesCopy.do")
	public Map<String, String> headerFilesCopy(
			@RequestHeader(name = "file") String file,
			@RequestHeader(name = "dest") String dest) throws Exception {

		return filesCopy(file, dest);
	}

	@PostMapping(value = "/xml/filesCopy.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String> xmlFilesCopy(@RequestBody Map<String, Object> map) throws Exception {
		return filesCopy((String) map.get("file"), (String) map.get("dest"));
	}

	@PostMapping(value = "/json/filesCopy.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String> jsonFilesCopy(@RequestBody Map<String, Object> map) throws Exception {
		return filesCopy((String) map.get("file"), (String) map.get("dest"));
	}

	@PostMapping("/form/filesCopy.do")
	public Map<String, String> multipartFilesCopy(MultipartFile file) throws Exception {
		return filesCopy(file.getOriginalFilename(), new String(file.getBytes()));
	}

	@GetMapping("/get/listFile.do")
	public Map<String, String[]> getListFile(String dir) {
		return listFile(dir);
	}

	@PostMapping("/post/listFile.do")
	public Map<String, String[]> postListFile(String dir) {
		return listFile(dir);
	}

	@PostMapping("/cookie/listFile.do")
	public Map<String, String[]> cookieListFile(@CookieValue(name = "dir") String dir) {
		return listFile(dir);
	}

	@PostMapping("/header/listFile.do")
	public Map<String, String[]> headerListFile(@RequestHeader(name = "dir") String dir) {
		return listFile(dir);
	}

	@PostMapping(value = "/xml/listFile.do", consumes = APPLICATION_XML_VALUE)
	public Map<String, String[]> xmlListFile(@RequestBody Map<String, Object> map) {
		return listFile((String) map.get("dir"));
	}

	@PostMapping(value = "/json/listFile.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, String[]> jsonListFile(@RequestBody Map<String, Object> map) {
		return listFile((String) map.get("dir"));
	}

	@PostMapping("/form/listFile.do")
	public Map<String, String[]> multipartListFile(MultipartFile file) {
		return listFile(file.getOriginalFilename());
	}

}
