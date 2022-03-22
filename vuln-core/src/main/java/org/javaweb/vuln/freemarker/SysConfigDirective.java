package org.javaweb.vuln.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.javaweb.vuln.context.SpringContext;
import org.javaweb.vuln.dao.SysConfigDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.javaweb.utils.DirectiveUtils.*;

public class SysConfigDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		SysConfigDAO        sysConfigDAO = SpringContext.getBean("sysConfigDAO");
		Map<String, Object> configMap    = sysConfigDAO.getSysConfig();

		if (configMap != null) {
			Map<String, TemplateModel> paramsMap = new HashMap<String, TemplateModel>();

			for (String key : configMap.keySet()) {
				paramsMap.put(key, getDefaultObjectWrapper().wrap(configMap.get(key)));
			}

			Map<String, TemplateModel> origMap = addParamsToVariable(env, paramsMap);
			body.render(env.getOut());
			removeParamsFromVariable(env, paramsMap, origMap);
		}
	}

}