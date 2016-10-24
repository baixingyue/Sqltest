<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" import="java.sql.*"%>

<%
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="loginUser" class="javabean.loginUsers" scope="page" />
<jsp:useBean id="userConn" class="javabean.userConn" scope="page" />
<jsp:setProperty property="*" name="loginUser"/>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
		//如果用户是普通用户则跳转到普通用户界面
		if (userConn.usersLogin(loginUser)&&userConn.sqlValidate(loginUser)) {
			session.setAttribute("loginUser", loginUser.getUsername());
			request.getRequestDispatcher("login_success.jsp").forward(request, response);
		}
		//既不是管理员又不是普通用户
		else {
			session.setAttribute("loginUser", loginUser.getUsername());
			response.sendRedirect("login_failure.jsp");
		}
	
%>
<%
	out.print(loginUser.getUsername() + loginUser.getPassword());
%>
