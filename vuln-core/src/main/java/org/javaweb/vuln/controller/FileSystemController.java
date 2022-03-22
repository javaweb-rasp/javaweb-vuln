package org.javaweb.vuln.controller;

import org.javaweb.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 文件系统漏洞测试
 * Creator: yz
 * Date: 2020-05-03
 */
@Controller
@RequestMapping("/FileSystem/")
public class FileSystemController {

	@ResponseBody
	@RequestMapping("/ReadFile.php")
	public ResponseEntity<byte[]> readFile(String file) throws IOException {
		byte[] content = FileUtils.readFileToByteArray(new File(file));

		return new ResponseEntity<byte[]>(content, HttpStatus.OK);
	}

//	@ResponseBody
//	@RequestMapping("/ReadFileNIO.php")
//	public String readFileNIO(String file) throws IOException {
//		byte[] bytes = Files.readAllBytes(Paths.get(getDocumentRoot(request) + file));
//
//		return "<pre>" + new String(bytes) + "</pre>";
//	}

	@ResponseBody
	@RequestMapping("/WriteFile.php")
	public String writeFile(String f, String c) throws IOException {
		File file = new File(f);
		FileUtils.writeStringToFile(file, c);

		return file.getAbsoluteFile() + "\t" + file.exists();
	}

	@ResponseBody
	@RequestMapping("/DeleteFile.php")
	public String deleteFile(String file) {
		File deleteFile = new File(file);

		return "文件" + deleteFile + "删除:" + (deleteFile.delete() ? "成功!" : "失败!");
	}

	@ResponseBody
	@RequestMapping("/DeleteFileByFileSystem.php")
	public String deleteFileByFileSystem(String file) throws Exception {
		File deleteFile = new File(file);

		Method m = Class.forName("java.io.DefaultFileSystem").getMethod("getFileSystem");
		m.setAccessible(true);
		Object fs = m.invoke(null);
		Method m2 = fs.getClass().getMethod("delete", File.class);
		m2.setAccessible(true);

		return "文件" + deleteFile + "删除:" + (((Boolean) m2.invoke(fs, deleteFile)) ? "成功!" : "失败!");
	}

//	@ResponseBody
//	@RequestMapping("/CopyFileNIO.php")
//	public String copyFile(String source, String dest) throws IOException {
//		Path path = Files.copy(Paths.get(source), Paths.get(dest));
//
//		return "文件:" + path + "复制成功!";
//	}

	@ResponseBody
	@RequestMapping("/RenameFile.php")
	public String renameFile(String s, String d) {
		File f        = new File(s);
		File destFile = new File(d);

		return "文件重命名" + (f.renameTo(destFile) ? "成功!" : "失败!");
	}

	@ResponseBody
	@RequestMapping("/ListFile.php")
	public String listFile(String dir) {
		String[]      files = new File(dir).list();
		StringBuilder sb    = new StringBuilder("<pre>");

		for (String file : files) {
			sb.append(file + "\r\n");
		}

		return sb.append("</pre>").toString();
	}

}
