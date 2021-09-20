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

@WebServlet(name = "UpdateFoodList", urlPatterns = "/updateFoodList")
public class UpdateFoodList extends HttpServlet {

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr() + " /updateFoodList");

        PrintWriter out = response.getWriter();

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line = br.readLine();
        System.out.println("line:" + line);

        JSONObject json = JSONObject.parseObject(line);
        System.out.println("json:" + json);

        int requestType=json.getIntValue("type");
        String id = json.getString("currentID");
        String name = json.getString("name");
        float price = json.getFloat("price");
        String imageUrl = json.getString("imageUrl");


        //查询数据库
        SqlHelper sqlHelper = new SqlHelper();

        String updateSql=null;
        String[] paras2=new String[5];

        switch (requestType){
            case 0:
                //添加
                updateSql = "insert into food (id,name,price,imgurl) values(?,?,?,?)";
                paras2 = new String[]{id,name, String.valueOf(price), UploadServlet.uploadImage};
                break;
            case 1:
                //修改
                updateSql = "update food set name=?,price=?,imgurl=? where id=?";
                paras2 = new String[]{name, String.valueOf(price), UploadServlet.uploadImage, id};
        }

        sqlHelper.updExecute(updateSql, paras2);

        sqlHelper.close();

        out.println("{\"requestType\":" + requestType + "}");

    }
}
