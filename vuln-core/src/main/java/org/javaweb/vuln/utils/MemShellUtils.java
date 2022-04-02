package org.javaweb.vuln.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import org.javaweb.utils.IOUtils;

import java.lang.reflect.Modifier;

public class MemShellUtils {

	public static byte[] createServletClass(ClassLoader classLoader) throws Exception {
		// 使用Javassist创建类字节码
		ClassPool classPool = ClassPool.getDefault();
		classPool.insertClassPath(new LoaderClassPath(classLoader));
		CtClass ctServletClass = classPool.makeClass(
				"org.javaweb.servlet.HelloServlet", classPool.get("javax.servlet.http.HttpServlet")
		);

		CtClass[] args = new CtClass[]{
				classPool.get("javax.servlet.http.HttpServletRequest"),
				classPool.get("javax.servlet.http.HttpServletResponse")
		};

		CtMethod ctMethod = new CtMethod(CtClass.voidType, "service", args, ctServletClass);
		ctMethod.setModifiers(Modifier.PUBLIC);
		ctMethod.setBody("$2.getWriter().println($1.getRealPath(\"/\"));");


		ctServletClass.addMethod(ctMethod);

		// 生成类字节码
		byte[] bytes = ctServletClass.toBytecode();
		String s     = IOUtils.toString(bytes);
		// 释放资源
		ctServletClass.detach();

		return bytes;
	}

}
