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

@WebServlet(name = "SelectUtilitiesList", urlPatterns = "/utilitiesList")
public class SelectUtilitiesList extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr()+" /utilitiesList");

        PrintWriter out = response.getWriter();


        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        Utilities utilities[] = new Utilities[300];

        int sum = 0;

        try {
            String sql = "select * from utilities";
            String[] paras = {};
            resultSet = sqlHelper.query(sql, paras);

            while (resultSet.next()) {
                int key=resultSet.getInt(1);
                String date = resultSet.getString(2);
                float waterUnit=resultSet.getFloat(3);
                float waterVolume=resultSet.getFloat(4);
                float waterAmount=resultSet.getFloat(5);
                float electricUnit=resultSet.getFloat(6);
                float electricVolume=resultSet.getFloat(7);
                float electricAmount=resultSet.getFloat(8);
                float gasUnit=resultSet.getFloat(9);
                float gasVolume=resultSet.getFloat(10);
                float gasAmount=resultSet.getFloat(11);
                float totalAmount=resultSet.getFloat(12);

                utilities[sum++]=new Utilities(key,date,
                        waterUnit,waterVolume,waterAmount,
                        electricUnit,electricVolume,electricAmount,
                        gasUnit,gasVolume,gasAmount,totalAmount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        Gson gson = new Gson();
        String json[] = new String[sum];
        for (int i = 0; i < sum; i++) json[i] = gson.toJson(utilities[i]);

//        out.println(Arrays.toString(json));

        String result = "[";
        for (int i = 0; i < sum - 1; i++)
            result += json[i] + ",";
        result += json[sum - 1];
        result += "]";

        out.println(result);
    }
}




