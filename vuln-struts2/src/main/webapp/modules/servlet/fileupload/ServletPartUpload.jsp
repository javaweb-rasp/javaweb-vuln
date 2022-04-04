<%@ page import="java.io.IOException" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String savePath = request.getServletContext().getRealPath("/");
    //获取form表单中Multipart请求的文件name属性
    Part part = request.getPart("file");
	if(part != null){
		out.print(uploadFile(part,savePath));
    }else {
		out.print(new String("失败".getBytes("utf8")));
    }


%>
<%!
    String uploadFile(Part part,String savePath) throws IOException {
        String header = part.getHeader("Content-Disposition");
        int start = header.lastIndexOf("=");
        String fileName = header.substring(start + 1)
                .replace("\"", "");
        if (fileName != null && !"".equals(fileName)) {
            part.write(savePath + "/" + fileName);
			return "上传成功";
        }
		return "上传失败";
    }
%>