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

@WebServlet(name = "SelectTakeout", urlPatterns = "/takeoutList")
public class SelectTakeout extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();


        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        Takeout takeout[] = new Takeout[3000];

        int sum = 0;

        try {
            String sql = "select * from takeout";
            String[] paras = {};
            resultSet = sqlHelper.query(sql, paras);

            while (resultSet.next()) {
                String key=resultSet.getString(1);
                String date = resultSet.getString(2);
                String customer = resultSet.getString(3);
                String platform = resultSet.getString(4);
                String order = resultSet.getString(5);
                int state=resultSet.getInt(6);
                String comment = resultSet.getString(7);
                String refundReason = resultSet.getString(8);

                takeout[sum++]=new Takeout(key, date,order,state, comment, customer,platform,refundReason);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        Gson gson = new Gson();
        String json[] = new String[takeout.length + 1];
        for (int i = 0; i < takeout.length; i++) json[i] = gson.toJson(takeout[i]);

//        out.println(Arrays.toString(json));

        String result = "[";
        for (int i = 0; i < takeout.length - 1; i++)
            result += json[i] + ",";
        result += json[takeout.length - 1];
        result += "]";

        out.println(result);
    }
}




