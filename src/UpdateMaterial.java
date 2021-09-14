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
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "UpdateMaterial", urlPatterns = "/updateMaterialList")
public class UpdateMaterial extends HttpServlet{

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

        String name=json.getString("name");
        String type=json.getString("type");
        String unit=json.getString("unit");
        int remaining=json.getIntValue("remaining");

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sdf.format(d);
        System.out.println("更新的时间：" + date);

        int requestType=0;

        //查询数据库
        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        try {
            String sql = "select type,unit,remaining from material where name=?";
            String[] paras = {name};
            resultSet = sqlHelper.query(sql, paras);

            String updateSql = null;
            String[] paras2=new String[5];

            //若有记录，则更新
            while (resultSet.next()) {
                requestType=1;
                updateSql="update material set type=?,unit=?,remaining=?,date=? where name=?";
                paras2 = new String[]{type, unit, String.valueOf(remaining), date, name};
            }
            if(requestType==0){
                //若无记录，则添加
                updateSql="insert into material (name,type,unit,remaining,date) values(?,?,?,?,?)";
                paras2 = new String[]{name, type, unit, String.valueOf(remaining), date};
            }
            sqlHelper.updExecute(updateSql,paras2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sqlHelper.close();

//        Gson gson = new Gson();
//        out.println(gson.toJson(resAccount));

        out.println("{\"requestType\":"+requestType+"}");

    }
}
