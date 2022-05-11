package org.javaweb.vuln.controller;

import org.javaweb.vuln.service.HessianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

/**
 * @author su18
 */
@Configuration
@ComponentScan
public class HessianController {

	@Autowired
	private HessianService hessianService;

	@Bean(name = "/HessianService")
	public HessianServiceExporter hessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(hessianService);
		exporter.setServiceInterface(HessianService.class);

		return exporter;
	}

}