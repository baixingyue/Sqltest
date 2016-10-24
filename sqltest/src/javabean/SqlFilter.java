package javabean;


import java.io.IOException;  
import java.util.Enumeration;  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
//����sql�ؼ��ֵ�Filter 
public class SqlFilter implements Filter {  
  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {  
  
        HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse res = (HttpServletResponse) response;  
        //����������������  
        Enumeration params = req.getParameterNames();  
  
        String sql = "";  
        while (params.hasMoreElements()) {  
            //�õ�������  
            String name = params.nextElement().toString();  
            //System.out.println("name===========================" + name + "--");  
            //�õ�������Ӧֵ  
            String[] value = req.getParameterValues(name);  
            for (int i = 0; i < value.length; i++) {  
                sql = sql + value[i];  
            }  
        }  
        System.out.println("��ƥ���ַ�����"+sql);  
        if (sqlValidate(sql)) {  
            res.sendRedirect("error.jsp");   
        } else {  
            chain.doFilter(req, res);  
        }  
    }  
  
    //У��
    protected static boolean sqlValidate(String str) {  
        str = str.toLowerCase();//ͳһתΪСд
        //String badStr = "and|exec";
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|char|declare|sitename|net user|xp_cmdshell|or|like";  
        /*String badStr = "'|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|" +  
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";    */    //���˵���sql�ؼ��֣������ֶ����  
        String[] badStrs = badStr.split("\\|");  
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) !=-1) { 
                System.out.println("ƥ�䵽��"+badStrs[i]);
                return true;  
            }  
        }  
        return false;  
    }  
  
    public void init(FilterConfig filterConfig) throws ServletException {  
        //throw new UnsupportedOperationException("Not supported yet.");  
    }  
  
    public void destroy() {  
        //throw new UnsupportedOperationException("Not supported yet.");  
    }  
}