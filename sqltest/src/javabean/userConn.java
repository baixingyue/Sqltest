package javabean;

import javabean.loginUsers;
import java.sql.*;

public class userConn {
	// ����������������ݿ����Ӵ����ٶ�ʹ��Mysql���ݿ�
	private String driverStr = "com.mysql.jdbc.Driver";
	private String connStr = "jdbc:mysql://localhost:3306/dbsecurity?user=root&password=501109zhy";
	private Connection connection = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	// ������������
	public userConn() {
		try {
			Class.forName(driverStr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.print("error1");
		}
	}

	// ���������ݿ������
	private Connection getConnection() {
		try {
			connection = DriverManager.getConnection(connStr);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("error2");

		}
		return connection;
	}

	// ����������
	private Statement createStatement() {
		try {
			stmt = getConnection().createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("error3");

		}
		return stmt;
	}

	// ִ�в�ѯ���������ؽ��������
	public ResultSet executeQuery(String sql) {
		try {
			rs = createStatement().executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	// ִ�и��²���
	public int executeUpdate(String sql) {
		int result = 0;
		try {
			result = createStatement().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// ִ�йرղ���
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

	//�ж��ǲ�����ͨ�û�
	public boolean usersLogin(loginUsers u) throws SQLException{
		  //��ȡ�û�������
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
		//��ȡ�û�������
		  String username=u.getUsername();
		  String password=u.getPassword();
		  String badStr = "'|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|" +  
          "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
          "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";     //���˵���sql�ؼ��֣������ֶ����  
	        String[] badStrs = badStr.split("\\|");  
	        for (int i = 0; i < badStrs.length; i++) {
	            if (username.indexOf(badStrs[i]) !=-1||password.indexOf(badStrs[i]) !=-1) { 
	                System.out.println("ƥ�䵽��"+badStrs[i]);
	                return false;  
	            }  
	        }  
	        return true;  
	}
	 
}
