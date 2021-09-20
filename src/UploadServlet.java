import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.List;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadServlet", urlPatterns = "/upload")
public class UploadServlet extends HttpServlet {

    static  String uploadImage="";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println(request.getParameter("getfile"));
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("新的请求: " + request.getRemoteAddr() + " /upload");

        //FileUpload是Apache commons下面的一个子项目，用来实现Java环境下的文件上传功能
        //FileUpload可以解析一个请求，并向应用程序提供上传的items（上传的文件表单项目）的一个List
        //每个文件项目（items）都有一些你的应用程序可能感兴趣的属性，例如name/content type
        //每个items还能提供一个“inputStream”

        //在使用上传的items之前，需要解析请求本身，确保请求实际上是一个文件上传请求可用
        if(!ServletFileUpload.isMultipartContent(request)){
            throw new IllegalArgumentException("Request is not multipart,please 'multipart/form-data' enctype for your form.");
        }

        //new DiskFileItemFactory() 创建工厂（这里用的是工厂模式）
        ServletFileUpload uploadHandler=new ServletFileUpload(new DiskFileItemFactory());
        uploadHandler.setHeaderEncoding("UTF-8");
        PrintWriter writer=response.getWriter();
        response.setContentType("application/json");
        JSONArray json=new JSONArray();
        //解析请求
        try {
            List<FileItem> items=uploadHandler.parseRequest(request);
            for(FileItem item:items){
                //判断是否为普通表单域
                if(!item.isFormField()){
                    //it is a file upload
                    String fileUploadPath = "C:\\Users\\ccy\\Pictures\\数据库课设图片\\foodMenu\\upload";
//                    String fileUploadPath = "http://localhost:8087";
                    File file=new File(fileUploadPath,item.getName());
//                 //write方法将FileItem对象中的内容保存到某个指定的文件中。
                    item.write(file);
                    uploadImage="http://localhost:8087/"+item.getName();
                    System.out.println("uploadImage:"+uploadImage);

                    //修改访问权限
                    Path path= Paths.get(file.getPath());
                    AclFileAttributeView aclView= Files.getFileAttributeView(path,AclFileAttributeView.class);
                    if (aclView == null) {
                        System.out.format("ACL view  is not  supported.%n");
                        return;
                    }
                    try {
                        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();

                        GroupPrincipal group = lookupService.lookupPrincipalByGroupName("Everyone");

                        AclEntry.Builder builder = AclEntry.newBuilder();
                        builder.setPrincipal(group);
                        builder.setType(AclEntryType.ALLOW);

                        //搜索之前所有用户权限
                        List<AclEntry> aclEntries = aclView.getAcl();
                        Set<AclEntryPermission> permissions = aclEntries.get(0).permissions();

                        builder.setPermissions(permissions);
                        AclEntry newEntry = builder.build();

                        //把新的用户权限加入列表
                        aclEntries.add(newEntry);

                        aclView.setAcl(aclEntries);

                        System.out.println("用户权限列表：");
                        for (AclEntry entry : aclEntries) {
                            System.out.format("Principal: %s%n", entry.principal());
                            System.out.format("Type: %s%n", entry.type());
                            System.out.format("Permissions are:%n");

                            permissions = entry.permissions();
                            for (AclEntryPermission p : permissions) {
                                System.out.format("%s %n", p);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    JSONObject jsono=new JSONObject();
                    jsono.put("name",item.getName());
                    jsono.put("size",item.getSize());
                    jsono.put("url", "upload?getfile=" + item.getName());
                    jsono.put("thumbnail_url", "upload?getthumb=" + item.getName());
                    jsono.put("delete_url", "upload?delfile=" + item.getName());
                    jsono.put("delete_type", "GET");
                    json.add(jsono);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.write(json.toString());
            writer.close();
        }
    }
}
