package javabean;

import javabean.loginUsers;
import java.sql.*;

public class userConn {
	// 定义驱动程序和数据库连接串，假定使用Mysql数据库
	private String driverStr = "com.mysql.jdbc.Driver";
	private String connStr = "jdbc:mysql://localhost:3306/dbsecurity?user=root&password=501109zhy";
	private Connection connection = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	// 加载驱动程序
	public userConn() {
		try {
			Class.forName(driverStr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.print("error1");
		}
	}

	// 建立与数据库的连接
	private Connection getConnection() {
		try {
			connection = DriverManager.getConnection(connStr);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("error2");

		}
		return connection;
	}

	// 创建语句对象
	private Statement createStatement() {
		try {
			stmt = getConnection().createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("error3");

		}
		return stmt;
	}

	// 执行查询操作，返回结果集对象
	public ResultSet executeQuery(String sql) {
		try {
			rs = createStatement().executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 执行更新操作
	public int executeUpdate(String sql) {
		int result = 0;
		try {
			result = createStatement().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 执行关闭操作
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//判断是不是普通用户
	public boolean usersLogin(loginUsers u) throws SQLException{
		  //获取用户名密码
		  String username=u.getUsername();
		  String password=u.getPassword();
		  
		  String sql="select *from user1 where username='"+username+"'and password='"+password+"';";
		  ResultSet rss=executeQuery(sql);
          while(rss.next()){
        	  if(rss!=null){
        		  return true;
        	  }
        	  else{
        		  return false;
        	  }
          }
          return false;
	}
	public boolean sqlValidate(loginUsers u){
		//获取用户名密码
		  String username=u.getUsername();
		  String password=u.getPassword();
		  String badStr = "'|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|" +  
          "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
          "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";     //过滤掉的sql关键字，可以手动添加  
	        String[] badStrs = badStr.split("\\|");  
	        for (int i = 0; i < badStrs.length; i++) {
	            if (username.indexOf(badStrs[i]) !=-1||password.indexOf(badStrs[i]) !=-1) { 
	                System.out.println("匹配到："+badStrs[i]);
	                return false;  
	            }  
	        }  
	        return true;  
	}
	 
}
