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

@WebServlet(name="GetUser",urlPatterns = "/currentUser")
public class GetUser extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();

        ResClass resClass;
        if(LoginCheck.user.equals("")){
            //用户未登录
            resClass=new ResClass("401","请先登录",true);
        }else {
            //从数据库拿数据
            User user=null;

            //查询数据库
            SqlHelper sqlHelper = new SqlHelper();
            ResultSet resultSet = null;

            try {
                String sql = "select name,avaterurl,authority from user where username=?";
                String[] paras = {LoginCheck.user};
                resultSet = sqlHelper.query(sql, paras);

                while (resultSet.next()) {
                    String name = resultSet.getString(1);
                    String avaterurl = resultSet.getString(2);
                    String authority = resultSet.getString(3);
                    user=new User(name,avaterurl,authority);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resClass=new ResClass(user,true);
            sqlHelper.close();
        }

        Gson gson = new Gson();
        out.println(gson.toJson(resClass));
    }
}


