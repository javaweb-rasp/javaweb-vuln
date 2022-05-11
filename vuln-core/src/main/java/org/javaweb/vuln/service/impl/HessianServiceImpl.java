package org.javaweb.vuln.service.impl;

import org.javaweb.vuln.service.HessianService;
import org.springframework.stereotype.Component;

/**
 * @author su18
 */
@Component
public class HessianServiceImpl implements HessianService {

	@Override
	public String printObject(Object obj) {
		return obj.toString();
	}

}
