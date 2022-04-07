package org.javaweb.vuln.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngineManager;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/ScriptEngine/")
public class ScriptEngineController {

	@RequestMapping("/scriptEngineEval.do")
	public Map<String, String> scriptEngineEval(String exp) throws Exception {
		Map<String, String> data = new LinkedHashMap<String, String>();
		if (exp != null) {
			Object eval = new ScriptEngineManager(null)
					.getEngineByName("js").eval(exp);
			data.put("result", eval.toString());
		} else {
			data.put("result", "参数不能为空！");
		}
		return data;
	}

}
