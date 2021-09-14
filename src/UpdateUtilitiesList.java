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

@WebServlet(name = "UpdateUtilitiesList", urlPatterns = "/updateUtilitiesList")
public class UpdateUtilitiesList extends HttpServlet{

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr()+" UpdateUtilitiesList");

        PrintWriter out = response.getWriter();

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        String line = br.readLine();
        System.out.println("line:" + line);

        JSONObject json = JSONObject.parseObject(line);
        System.out.println("json:" + json);

        String oringinalDate=json.getString("date");
        int key=json.getIntValue("currentID");
        float waterUnit=json.getFloat("waterUnit");
        float waterVolume=json.getFloat("waterVolume");
        float waterAmount=json.getFloat("waterAmount");
        float electricUnit=json.getFloat("electricUnit");
        float electricVolume=json.getFloat("electricVolume");
        float electricAmount=json.getFloat("electricAmount");
        float gasUnit=json.getFloat("gasUnit");
        float gasVolume=json.getFloat("gasVolume");
        float gasAmount=json.getFloat("gasAmount");
        float totalAmount=json.getFloat("totalAmount");


        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sdf.format(d);
        System.out.println("更新的时间：" + date);

        int requestType=0;

        //查询数据库
        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        try {
            String sql = "select * from utilities where date=?";
            String[] paras = {oringinalDate};
            resultSet = sqlHelper.query(sql, paras);

            System.out.println("after select");

            String updateSql = null;
            String[] paras2=new String[13];

            //若有记录，则更新
            while (resultSet.next()) {
                requestType=1;
                updateSql="update utilities set date=?," +
                        "waterUnit=?,waterVolume=?,waterAmount=?," +
                        "electricUnit=?,electricVolume=?,electricAmount=?," +
                        "gasUnit=?,gasVolume=?,gasAmount=?," +
                        "totalAmount=? where date=?";
                paras2 = new String[]{
                        date,
                        String.valueOf(waterUnit), String.valueOf(waterVolume), String.valueOf(waterAmount),
                        String.valueOf(electricUnit),String.valueOf(electricVolume), String.valueOf(electricAmount),
                        String.valueOf(gasUnit),String.valueOf(gasVolume), String.valueOf(gasAmount),
                        String.valueOf(totalAmount), oringinalDate
                };
            }
            if(requestType==0){
                //若无记录，则添加
                updateSql="insert into utilities (id,date," +
                        "waterUnit,waterVolume,waterAmount," +
                        "electricUnit,electricVolume,electricAmount," +
                        "gasUnit,gasVolume,gasAmount," +
                        "totalAmount) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                paras2 = new String[]{
                        String.valueOf(key),date,
                        String.valueOf(waterUnit), String.valueOf(waterVolume), String.valueOf(waterAmount),
                        String.valueOf(electricUnit),String.valueOf(electricVolume), String.valueOf(electricAmount),
                        String.valueOf(gasUnit),String.valueOf(gasVolume), String.valueOf(gasAmount),
                        String.valueOf(totalAmount)
                };
            }
            sqlHelper.updExecute(updateSql,paras2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sqlHelper.close();

        out.println("{\"requestType\":"+requestType+"}");

    }
}
