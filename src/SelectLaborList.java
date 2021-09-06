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

@WebServlet(name = "SelectLaborList", urlPatterns = "/laborList")
public class SelectLaborList extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();


        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        Labor labor[] = new Labor[300];

        int sum = 0;

        try {
            String sql = "select * from labormanage";
            String[] paras = {};
            resultSet = sqlHelper.query(sql, paras);

            while (resultSet.next()) {
                String laborId=resultSet.getString(1);
                String name = resultSet.getString(2);
                String position = resultSet.getString(3);
                int baseSalary=resultSet.getInt(4);
                int overTimeAllowance=resultSet.getInt(5);
                int mealAllowance=resultSet.getInt(6);
                int socialSecurity=resultSet.getInt(7);
                int otherAllowance=resultSet.getInt(8);
                int timeOff=resultSet.getInt(9);
                int otherOff=resultSet.getInt(10);
                int totalSalary=resultSet.getInt(11);
                int handSalary=resultSet.getInt(12);
                boolean toAccount = resultSet.getInt(13) == 1;
                String updateTime = resultSet.getString(14);

                labor[sum++]=new Labor( laborId,  name,  position,  updateTime, baseSalary,  overTimeAllowance, mealAllowance, socialSecurity, otherAllowance, timeOff,otherOff,totalSalary,handSalary,toAccount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        Gson gson = new Gson();
        String json[] = new String[sum];
        for (int i = 0; i < sum; i++) json[i] = gson.toJson(labor[i]);

//        out.println(Arrays.toString(json));

        String result = "[";
        for (int i = 0; i < sum - 1; i++)
            result += json[i] + ",";
        result += json[sum - 1];
        result += "]";

        out.println(result);
    }
}




