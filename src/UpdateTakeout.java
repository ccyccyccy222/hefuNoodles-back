import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
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
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "UpdateTakeout", urlPatterns = "/updateTakeoutList")
public class UpdateTakeout extends HttpServlet{

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr()+" /updateMaterialList");

        PrintWriter out = response.getWriter();

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        String line = br.readLine();
        System.out.println("line:" + line);

        JSONObject json = JSONObject.parseObject(line);
        System.out.println("json:" + json);

        String key=json.getString("key");
        int state=json.getIntValue("state");

        //查询数据库
        SqlHelper sqlHelper = new SqlHelper();

        String updateSql="update takeout set state=? where id=?";
        String[] paras2 = new String[]{String.valueOf(state), key};

        sqlHelper.updExecute(updateSql,paras2);

        sqlHelper.close();

//        Gson gson = new Gson();
//        out.println(gson.toJson(resAccount));

        out.println("{\"code\":"+0+"}");

    }
}
