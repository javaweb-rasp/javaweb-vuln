package org.javaweb.vuls.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Action(value = "vuls", results = {@Result(location = "/vuls.jsp")})
public class TestActionAnnotation extends ActionSupport {

	public String execute() {
		return SUCCESS;
	}

}