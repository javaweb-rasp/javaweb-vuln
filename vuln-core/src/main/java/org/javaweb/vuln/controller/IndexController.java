package org.javaweb.vuln.controller;

import org.javaweb.utils.StringUtils;
import org.javaweb.vuln.commons.ResultInfo;
import org.javaweb.vuln.dao.SysArticleDAO;
import org.javaweb.vuln.dao.SysCommentDAO;
import org.javaweb.vuln.dao.SysUserDAO;
import org.javaweb.vuln.entity.SysArticle;
import org.javaweb.vuln.entity.SysComment;
import org.javaweb.vuln.entity.SysUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.javaweb.utils.StringUtils.isNotEmpty;
import static org.javaweb.vuln.commons.Constants.SESSION_USER;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

/**
 * 演示环境首页漏洞
 * Creator: yz
 * Date: 2019-08-29
 */
@Controller
public class IndexController {

	@Resource
	private SysUserDAO sysUserDAO;

	@Resource
	private SysArticleDAO sysArticleDAO;

	@Resource
	private SysCommentDAO sysCommentDAO;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = {"/", "/index.php"})
	public String indexPage() {
		return "/index.html";
	}

	@RequestMapping("/login.php")
	public String loginPage(@SessionAttribute(value = SESSION_USER, required = false) Object sessionUser) {
		if (sessionUser == null) {
			return "/html/user/login.html";
		}

		return "/html/user/home.html";
	}

	@RequestMapping("/reg.php")
	public String regPage() {
		return "/html/user/reg.html";
	}

	@RequestMapping("/forget.php")
	public String forgetPage() {
		return "/html/user/forget.html";
	}

	@RequestMapping("/addArticle.php")
	public String addArticlePage() {
		return "/html/jie/add.html";
	}

	@RequestMapping("/user/index.do")
	public String userHomePage() {
		return "/html/user/home.html";
	}

	@RequestMapping("/getUserById.do")
	public ModelAndView getUserById(String id, ModelAndView mv) {
		mv.addObject("userInfo", sysUserDAO.getSysUserById(id));
		mv.setViewName("/html/user/user.html");

		return mv;
	}

	@ResponseBody
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

	@ResponseBody
	@RequestMapping("/user/logout.php")
	public ResultInfo<Object> logout(WebRequest request) throws IOException {
		request.removeAttribute(SESSION_USER, SCOPE_SESSION);

		return new ResultInfo<Object>("退出成功!", true);
	}

	@ResponseBody
	@RequestMapping("/getUserByName.do")
	public Map<String, Object> getUserByName(String username) {
		// 存在SQL注入的查询语句
		String sql = "select * from sys_user where id = 1 and username = '" + username + "' " +
				"and password != '" + StringUtils.getCurrentTime() + "'";

		// 查询数据库记录
		return jdbcTemplate.queryForMap(sql);
	}

	@ResponseBody
	@RequestMapping(value = "/getSysUserByEmail.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> getSysUserByEmail(@RequestBody Map<String, Object> map) {
		return jdbcTemplate.queryForMap("select * from sys_user where email = ? ", map.get("email"));
	}

	@ResponseBody
	@RequestMapping(value = "/getSysUserByName.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> getSysUserByName(@RequestBody Map<String, Object> map) {
		return jdbcTemplate.queryForMap("select * from sys_user where username = '" + map.get("username") + "'");
	}

	@ResponseBody
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

	@RequestMapping("/index.do")
	public ModelAndView index(ModelAndView mv) {
		mv.addObject("articleList", sysArticleDAO.getSysArticleList());
		mv.addObject("articleTopList", sysArticleDAO.getSysArticleTopList(10));
		mv.setViewName("/html/index.html");

		return mv;
	}

	@RequestMapping("/getArticle.do")
	public ModelAndView getArticle(String articleId, ModelAndView mv) {
		mv.addObject("article", sysArticleDAO.getSysArticle(articleId));
		mv.addObject("articleTopList", sysArticleDAO.getSysArticleTopList(10));
		mv.setViewName("/html/jie/detail.html");

		return mv;
	}

	@ResponseBody
	@RequestMapping("/addArticle.do")
	public ResultInfo<?> addArticle(SysArticle article, @SessionAttribute(SESSION_USER) Object sessionUser) {
		ResultInfo<?> resultInfo = new ResultInfo<Object>();

		if (sessionUser != null) {
			SysUser u = (SysUser) sessionUser;
			article.setUserId(u.getId());
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

	@ResponseBody
	@RequestMapping("/addComments.do")
	public ResultInfo<?> addComments(SysComment comment,
	                                 @SessionAttribute(value = SESSION_USER, required = false) Object sessionUser) {

		ResultInfo<?> resultInfo = new ResultInfo<Object>();

		if (isNotEmpty(comment.getCommentContent())) {
			if (sessionUser != null) {
				SysUser u = (SysUser) sessionUser;
				comment.setCommentUserId(u.getId());
				comment.setCommentAuthor(u.getUsername());
			} else {
				comment.setCommentUserId(0L);
				comment.setCommentAuthor("guest");
			}

			if (sysCommentDAO.addSysComments(comment)) {
				resultInfo.setValid(true);
			}
		} else {
			resultInfo.setMsg("评论内容不能为空!");
		}

		return resultInfo;
	}

	@ResponseBody
	@RequestMapping(value = "/upload.php", method = POST)
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

}
