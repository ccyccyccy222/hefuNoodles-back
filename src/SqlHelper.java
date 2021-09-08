//对数据库操作的类:增删改查

import java.sql.*;

public class SqlHelper {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hefunoodles?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "123";

    //定义需要的对象
    private Connection ct = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Statement statement = null;


    //构造函数，初始化ct
    public SqlHelper() {

        // 注册 JDBC 驱动
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ct = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet query(String sql, String[] paras) {
        try {
            ps = ct.prepareStatement(sql);
            //对sql的参数赋值
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //把增删改查合在一起
    public boolean updExecute(String sql, String[] paras){
        boolean b=true;
        try {
            ps = ct.prepareStatement(sql);
            //对sql的参数赋值
            for (int i = 0; i < paras.length; i++) {
                //i+1为问号的编号，paras[i]为传入的参数
                ps.setString(i + 1, paras[i]);
            }
            if(ps.executeUpdate()!=1){
                b= false;
            }
        } catch (SQLException e) {
            b=false;
            e.printStackTrace();
        }
        return b;
    }


    //关闭资源的方法
    public void close() {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //为了让程序健壮
            if (ps != null)
                ps.close();
        } catch (SQLException se2) {
        }// 什么都不做
        try {
            if (ct != null)
                ct.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

    }
}
