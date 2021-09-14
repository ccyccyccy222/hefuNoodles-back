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

@WebServlet(name = "UpdateLaborList", urlPatterns = "/updateLaborList")
public class UpdateLaborList extends HttpServlet{

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr()+" UpdateLaborList");

        PrintWriter out = response.getWriter();

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        String line = br.readLine();
        System.out.println("line:" + line);

        JSONObject json = JSONObject.parseObject(line);
        System.out.println("json:" + json);

        String laborId=json.getString("laborId");
        String name=json.getString("name");
        String position=json.getString("position");
        int baseSalary=json.getIntValue("baseSalary");
        int overTimeAllowance=json.getIntValue("overTimeAllowance");
        int mealAllowance=json.getIntValue("mealAllowance");
        int otherAllowance=json.getIntValue("otherAllowance");
        int socialSecurity=json.getIntValue("socialSecurity");
        int timeOff=json.getIntValue("timeOff");
        int otherOff=json.getIntValue("otherOff");
        int totalSalary=json.getIntValue("totalSalary");
        int handSalary=json.getIntValue("handSalary");
        boolean toAccount=json.getBooleanValue("toAccount");


        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sdf.format(d);
        System.out.println("更新的时间：" + date);

        int requestType=0;

        //查询数据库
        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        try {
            String sql = "select * from labormanage where laborId=?";
            String[] paras = {laborId};
            resultSet = sqlHelper.query(sql, paras);

            String updateSql = null;
            String[] paras2=new String[14];

            //若有记录，则更新
            while (resultSet.next()) {
                requestType=1;
                updateSql="update labormanage set name=?,position=?," +
                        "baseSalary=?,overTimeAllowance=?,mealAllowance=?," +
                        "socialSecurity=?,otherAllowance=?,timeOff=?,"+
                        "otherOff=?,totalSalary=?,handSalary=?,"+
                        "toAccount=?,updateTime=? where laborId=?";
                paras2 = new String[]{
                        name, position, String.valueOf(baseSalary),
                        String.valueOf(overTimeAllowance), String.valueOf(mealAllowance),
                        String.valueOf(socialSecurity),String.valueOf(otherAllowance),
                        String.valueOf(timeOff), String.valueOf(otherOff),
                        String.valueOf(totalSalary), String.valueOf(handSalary),
                        toAccount?"1":"0",date,laborId
                };
            }
            if(requestType==0){
                //若无记录，则添加
                updateSql="insert into labormanage (laborId,name, position," +
                        "baseSalary,overTimeAllowance,mealAllowance,socialSecurity," +
                        "otherAllowance,timeOff,otherOff,totalSalary,handSalary," +
                        "toAccount,updateTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                paras2 = new String[]{
                        laborId,name, position, String.valueOf(baseSalary),
                        String.valueOf(overTimeAllowance), String.valueOf(mealAllowance),
                        String.valueOf(socialSecurity),String.valueOf(otherAllowance),
                        String.valueOf(timeOff), String.valueOf(otherOff),
                        String.valueOf(totalSalary), String.valueOf(handSalary),
                        toAccount?"1":"0",date
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
