package org.javaweb.vuln.controller;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.mvel2.MVEL;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/Expression/")
public class ExpressionController {

	@PostMapping(value = "/ongl.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> ognl(@RequestBody Map<String, Object> map) throws OgnlException {
		Map<String, Object> data    = new LinkedHashMap<String, Object>();
		OgnlContext         context = new OgnlContext();
		String              exp     = (String) map.get("exp");

		data.put("data", Ognl.getValue(exp, context, context.getRoot()));

		return data;
	}

	@PostMapping(value = "/spEL.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> spel(@RequestBody Map<String, Object> map) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		String              exp  = (String) map.get("exp");
		data.put("data", new SpelExpressionParser().parseExpression(exp).getValue());

		return data;
	}

	@PostMapping(value = "/mvel.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> mvel(@RequestBody Map<String, Object> map) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		String              exp  = (String) map.get("exp");
		data.put("data", MVEL.eval(exp));

		return data;
	}

}
