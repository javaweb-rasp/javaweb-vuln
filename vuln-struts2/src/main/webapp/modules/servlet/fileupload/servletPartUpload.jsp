<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%
    String savePath = request.getServletContext().getRealPath("/");
    //获取form表单中Multipart请求的文件name属性
    Part part = request.getPart("file");
    if (part != null) {
        out.print(uploadFile(part, savePath));
    }
%>
<%!
    JSON uploadFile(Part part, String savePath) throws IOException {
        Map<String, Object> data   = new LinkedHashMap<String, Object>();
        String              header = part.getHeader("Content-Disposition");
        int                 start  = header.lastIndexOf("=");
        String fileName = header.substring(start + 1)
                .replace("\"", "");
        if (fileName != null && !"".equals(fileName)) {
            part.write(savePath + "/" + fileName);
            data.put("file", savePath + "/" + fileName);
            data.put("msg", "上传成功");
            return (JSON) JSON.toJSON(data);
        }
        data.put("msg", "上传失败");
        return (JSON) JSON.toJSON(data);
    }
%>