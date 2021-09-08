import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(name = "LoginCheck", urlPatterns = "/login/account")
public class LoginCheck extends HttpServlet {

    static String user = "";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = br.readLine();
        System.out.println("line:" + line);

        JSONObject json = JSONObject.parseObject(line);
        System.out.println("json:" + json);

        String username=json.getString("username");
        String password=json.getString("password");
//
//        System.out.println("username："+username);
//        System.out.println("password："+password);
//
//
        ResAccount resAccount = null;

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
                    System.out.println("username "+username+" is online");
                    check=true;
                    String authority = resultSet.getString(3);
                    resAccount=new ResAccount("ok",authority,true);
                    break;
                }
            }

            if(!check){
                //表示数据库没有此人
                resAccount=new ResAccount("error","guest",true);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sqlHelper.close();

        Gson gson = new Gson();
        out.println(gson.toJson(resAccount));
    }
}

class ResAccount{
    String status;
    String currentAuthority;
    boolean success;


    ///login/account返回
    public ResAccount(String status, String currentAuthority,boolean success) {
        this.status = status;
        this.currentAuthority = currentAuthority;
        this.success=success;
    }
}

