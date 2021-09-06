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

@WebServlet(name = "SelectMaterial", urlPatterns = "/materialList")
public class SelectMaterial extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr());

        PrintWriter out = response.getWriter();


        SqlHelper sqlHelper = new SqlHelper();
        ResultSet resultSet = null;

        Material material[] = new Material[30];

        int sum = 0;

        try {
            String sql = "select * from material";
            String[] paras = {};
            resultSet = sqlHelper.query(sql, paras);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String type = resultSet.getString(3);
                String unit = resultSet.getString(4);
                float remaining = resultSet.getFloat(5);
                String date = resultSet.getString(6);

                material[sum++]=new Material(id,name,type,unit,remaining,date);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        Gson gson = new Gson();
        String json[] = new String[material.length + 1];
        for (int i = 0; i < material.length; i++) json[i] = gson.toJson(material[i]);

//        out.println(Arrays.toString(json));

        String result = "[";
        for (int i = 0; i < material.length - 1; i++){
            if(json[i]==null) break;
            result += json[i] + ",";
        }
        result += json[material.length - 1];
        result += "]";

        out.println(result);
    }
}




