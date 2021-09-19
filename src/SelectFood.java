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

@WebServlet(name = "SelectFood", urlPatterns = "/food")
public class SelectFood extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr()+" /food");

        PrintWriter out = response.getWriter();


        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        Food food[] = new Food[10];

        int sum = 0;

        try {
            String sql = "select * from food";
            String[] paras = {};
            resultSet = sqlHelper.query(sql, paras);

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
//                System.out.println("name:"+name);
                float price = resultSet.getFloat(3);
                String imgurl = resultSet.getString(4);
                String bill = resultSet.getString(5);

                food[sum++] = new Food(id, name, price, imgurl, bill);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        sqlHelper.close();

        Gson gson = new Gson();
        String json[] = new String[sum];
        for (int i = 0; i < sum; i++) json[i] = gson.toJson(food[i]);

//        out.println(Arrays.toString(json));

        String result = "[";
        for (int i = 0; i < sum - 1; i++)
            result += json[i] + ",";
        result += json[sum - 1];
        result += "]";

        out.println(result);
    }
}




