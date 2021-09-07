import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(name="LoginCheck",urlPatterns = "/login/account")
public class LoginCheck extends HttpServlet{

    public static String user="";

    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();


        String username=request.getParameter("username");
        String password=request.getParameter("password");

        ResClass resClass = null;

        //查询数据库
        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        try {
            String sql = "select username,password,authority from user";
            String[] paras = {};
            resultSet = sqlHelper.query(sql, paras);

            boolean check=false;

            while (resultSet.next()) {
                String usernameSQL = resultSet.getString(1);
                String passwordSQL = resultSet.getString(2);
                if(usernameSQL.equals(username)&&passwordSQL.equals(password)){
                    //设置全局用户名
                    user=usernameSQL;
                    System.out.println("username"+username+"is online");
                    check=true;
                    String authority = resultSet.getString(3);
                    resClass=new ResClass("ok",authority);
                    break;
                }
            }

            if(!check){
                //表示数据库没有此人
                resClass=new ResClass("error","guest");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sqlHelper.close();

        Gson gson = new Gson();
        out.println(gson.toJson(resClass));
    }
}


class ResClass{
    String state;
    String currentAuthority;

    String errorCode;
    String errorMessage;
    boolean success;

    User user;


    ///login/account返回
    public ResClass(String state, String currentAuthority) {
        this.state = state;
        this.currentAuthority = currentAuthority;
    }

    // /currentUser返回
    public ResClass(String errorCode, String errorMessage,boolean success) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.success =success;
    }

    public ResClass(User user,boolean success) {
        this.user=user;
        this.success =success;
    }
}

