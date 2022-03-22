package org.javaweb.vuln.controller;

import org.javaweb.utils.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/CMD/")
public class CMDController {

	@RequestMapping("/ping.do")
	public @ResponseBody String ping(String ip) throws Exception {
		Process process = new ProcessBuilder("/bin/sh", "-c", "ping -c 1 " + ip).start();
		return IOUtils.toString(process.getInputStream());
	}

	@RequestMapping("/cmd.do")
	public @ResponseBody String cmd(String cmd) throws Exception {
		return IOUtils.toString(Runtime.getRuntime().exec(cmd).getInputStream());
	}

}
