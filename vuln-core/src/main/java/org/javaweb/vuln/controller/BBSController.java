package org.javaweb.vuln.controller;

import org.javaweb.utils.StringUtils;
import org.javaweb.vuln.commons.APIMapping;
import org.javaweb.vuln.commons.PageRequestProxy;
import org.javaweb.vuln.commons.ResultInfo;
import org.javaweb.vuln.dao.SysArticleDAO;
import org.javaweb.vuln.dao.SysCommentDAO;
import org.javaweb.vuln.dao.SysConfigDAO;
import org.javaweb.vuln.dao.SysUserDAO;
import org.javaweb.vuln.entity.SysArticle;
import org.javaweb.vuln.entity.SysComment;
import org.javaweb.vuln.entity.SysUser;
import org.javaweb.vuln.repository.SysArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.javaweb.vuln.commons.Constants.SESSION_USER;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

@RestController
public class BBSController {

	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private SysArticleDAO sysArticleDAO;

	@Autowired
	private SysCommentDAO sysCommentDAO;

	@Autowired
	private SysConfigDAO sysConfigDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SysArticleRepository sysArticleRepository;

	@RequestMapping(value = "/login.do", method = POST, consumes = APPLICATION_JSON_VALUE)
	public ResultInfo<SysUser> login(@RequestBody SysUser user, WebRequest request) {
		ResultInfo<SysUser> result  = new ResultInfo<SysUser>();
		SysUser             sysUser = sysUserDAO.getSysUserByUsername(user.getUsername());

		if (sysUser != null) {
			SysUser loginUser = sysUserDAO.login(user.getUsername(), user.getPassword());

			if (loginUser != null) {
				request.setAttribute(SESSION_USER, loginUser, SCOPE_SESSION);
				result.setValid(true);
				result.setData(loginUser);
			} else {
				result.setData(sysUser);
				result.setMsg("登陆失败，密码错误!");
			}
		} else {
			result.setMsg("登陆失败，该用户不存在!");
		}

		return result;
	}

	@RequestMapping("/register.do")
	public ResultInfo<SysUser> register(SysUser user) {
		ResultInfo<SysUser> result = new ResultInfo<SysUser>();
		SysUser             u      = sysUserDAO.getSysUserByUsername(user.getUsername());

		if (isNotEmpty(user.getUsername()) && isNotEmpty(user.getPassword())) {
			if (u == null) {
				if (sysUserDAO.register(user) > 0) {
					result.setValid(true);
					result.setMsg("用户注册成功!");
				} else {
					result.setData(u);
					result.setMsg("用户注册失败!");
				}
			} else {
				result.setMsg("用户注册失败，该用户已存在!");
			}
		} else {
			result.setMsg("用户注册失败，账号或密码不能为空!");
		}

		return result;
	}

	@PostMapping(value = "/upload.php")
	public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws Exception {
		File uploadDir  = new File("UploadImages");
		File uploadFile = new File(uploadDir, file.getOriginalFilename());

		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		copyInputStreamToFile(file.getInputStream(), uploadFile);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("url", "/download.php?fileName=" + uploadFile.getName());
		jsonMap.put("status", 0);
		jsonMap.put("msg", 0);

		return jsonMap;
	}

	@RequestMapping("/download.php")
	public HttpEntity<byte[]> downloadFile(String fileName) throws IOException {
		File        uploadDir    = new File("UploadImages");
		File        downloadFile = new File(uploadDir, fileName);
		byte[]      bytes        = readFileToByteArray(downloadFile);
		HttpHeaders header       = new HttpHeaders();

		header.setContentType(APPLICATION_OCTET_STREAM);
		header.set(CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.setContentLength(bytes.length);

		return new HttpEntity<byte[]>(bytes, header);
	}

	@RequestMapping("/user/logout.php")
	public ResultInfo<Object> logout(WebRequest request) throws IOException {
		request.removeAttribute(SESSION_USER, SCOPE_SESSION);

		return new ResultInfo<Object>("退出成功!", true);
	}

	@RequestMapping("/addComments.do")
	public ResultInfo<?> addComments(SysComment comment,
	                                 @SessionAttribute(value = SESSION_USER, required = false) Object sessionUser) {

		ResultInfo<?> resultInfo = new ResultInfo<Object>();

		if (isNotEmpty(comment.getCommentContent())) {
//			if (sessionUser != null) {
//				SysUser u = (SysUser) sessionUser;
//				comment.setCommentUserId(u.getUserId());
//				comment.setCommentAuthor(u.getUsername());
//			} else {
//				comment.setCommentUserId(0L);
//				comment.setCommentAuthor("guest");
//			}

			if (sysCommentDAO.addSysComments(comment)) {
				resultInfo.setValid(true);
			}
		} else {
			resultInfo.setMsg("评论内容不能为空!");
		}

		return resultInfo;
	}

	@RequestMapping("/getUserByName.do")
	public Map<String, Object> getUserByName(String username) {
		// 存在SQL注入的查询语句
		String sql = "select * from sys_user where id = 1 and username = '" + username + "' " +
				"and password != '" + StringUtils.getCurrentTime() + "'";

		// 查询数据库记录
		return jdbcTemplate.queryForMap(sql);
	}

	@RequestMapping(value = "/getSysUserByEmail.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> getSysUserByEmail(@RequestBody Map<String, Object> map) {
		return jdbcTemplate.queryForMap("select * from sys_user where email = ? ", map.get("email"));
	}

	@RequestMapping(value = "/getSysUserByName.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> getSysUserByName(@RequestBody Map<String, Object> map) {
		return jdbcTemplate.queryForMap("select * from sys_user where username = '" + map.get("username") + "'");
	}

	@RequestMapping("/addArticle.do")
	public ResultInfo<?> addArticle(SysArticle article, @SessionAttribute(SESSION_USER) Object sessionUser) {
		ResultInfo<?> resultInfo = new ResultInfo<Object>();

		if (sessionUser != null) {
			SysUser u = (SysUser) sessionUser;
//			article.setUserId(u.getUserId());
			article.setAuthor(u.getUsername());

			if (sysArticleDAO.addArticle(article)) {
				resultInfo.setValid(true);
			} else {
				resultInfo.setMsg("添加文章失败!");
			}
		} else {
			resultInfo.setMsg("未检测到用户登陆信息，请重新登陆！");
		}

		return resultInfo;
	}

	@APIMapping(value = "/getArticles.do")
	public Page<SysArticle> getArticles(@RequestBody Map<String, Object> map) {
		return sysArticleDAO.getSysArticleList(PageRequestProxy.of(map));
	}

	@APIMapping(value = "/getArticle.do")
	public SysArticle getArticle(@RequestBody Map<String, Object> map) {
		return sysArticleDAO.getSysArticle((String) map.get("id"));
	}

	@APIMapping(value = "/getSysArticleTops.do")
	public List<SysArticle> getSysArticleTops(@RequestBody Map<String, Object> map) {
		return sysArticleDAO.getSysArticleTops(10);
	}

	@APIMapping(value = "/jdbc/getArticleByAuthor.do")
	public List<Map<String, Object>> jdbcGetArticleByAuthor(@RequestBody Map<String, Object> map) {
		String sql = "select * from sys_article where author = ? ";

		return jdbcTemplate.queryForList(sql, map.get("author"));
	}

	@APIMapping(value = "/jpa/getArticleByAuthor.do")
	public List<SysArticle> jpaGetArticleById(@RequestBody Map<String, Object> map) {
		return sysArticleRepository.findSysArticleByAuthor((String) map.get("author"));
	}

}
