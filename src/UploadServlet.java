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

                    /*
                    Path和Files类封装了在用户机器上处理文件系统所需的所有功能,
                    Path和Files是在Java SE 7 中新添加进来的类
                    在传统java.io中， 文件和目录都被抽象成File对象， 即 File file = new File(".");
                    NIO.中则引入接口Path代表与平台无关的路径，文件和目录都用Path对象表示
                    通过路径工具类Paths返回一个路径对象Path

                    Paths.get()是一个创建Path的工厂方法，需要传入一个绝对路径作为参数。
                    下边我们传入的是Windows系统的文件路径格式
                    String fileUploadPath = "C:\\Users\\ccy\\Pictures\\数据库课设图片\\foodMenu\\upload";


                    https://www.cnblogs.com/gaomanito/p/11642918.html
                    https://blog.csdn.net/oMaoYanEr/article/details/80961130
                     */
                    Path path= Paths.get(file.getPath());

                    /* AclFileAttributeView:
                    一个文件属性视图，支持读取或更新文件的访问控制列表（ACL）或文件所有者属性。
                    ACL用于指定文件系统对象的访问权限。
                    ACL是access-control-entries的有序列表，每个列表指定UserPrincipal以及该用户主体的访问级别。
                    此文件属性视图定义getAcl和setAcl方法，以根据RFC 3530: Network File System (NFS) version 4 Protocol中指定的ACL模型读取和写入ACL。
                    https://www.1024sky.cn/blog/article/4922
                    */

                    /* getFileAttributeView():
                    返回给定类型的文件属性视图。
                    public static <V extends FileAttributeView> V getFileAttributeView​(Path path, 类<V> type, LinkOption... options)
                    文件属性视图提供一组文件属性的只读或可更新视图。
                    此方法适用于文件属性视图定义用于读取或更新文件属性的类型安全方法的情况。
                    type参数是所需属性视图的类型，如果支持，该方法将返回该类型的实例。
                    BasicFileAttributeView类型支持访问文件的基本属性。
                    调用此方法以选择该类型的文件属性视图将始终返回该类的实例。
                     https://www.apiref.com/java11-zh/java.base/java/nio/file/Files.html#getFileAttributeView(java.nio.file.Path,java.lang.Class,java.nio.file.LinkOption...)
                     */

                    /*AclFileAttributeView.class
                    表示获取AclFileAttributeView的class对象，类型类指的是代表一个类型的类
                     */
                    AclFileAttributeView aclView= Files.getFileAttributeView(path,AclFileAttributeView.class);
                    if (aclView == null) {
                        System.out.format("ACL view  is not  supported.%n");
                        return;
                    }
                    try {
                        /*FileSystems
                        文件系统的工厂方法。 此类定义getDefault方法以获取默认文件系统和工厂方法以构造其他类型的文件系统
                        getUserPrincipalLookupService方法返回UserPrincipalLookupService以按名称查找用户或组。
                        https://www.apiref.com/java11-zh/java.base/java/nio/file/FileSystems.html
                         */
                        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();

                        // GroupPrincipal表示组标识 。另：UserPrincipal表示可用于确定对文件系统中的对象的访问权限的标识。
                        // 此处可以得到\Everyone (Well-known group)组，表示所有用户都可以访问
                        GroupPrincipal group = lookupService.lookupPrincipalByGroupName("Everyone");

                        //AclEntry是acl的一个条目，包含type,Principal,permissions,flags四个部分，通过AclEntry.Builder创建
                        //一个Builder对象是通过调用AclEntry类的newBuilder()方法获得的
                        AclEntry.Builder builder = AclEntry.newBuilder();
                        builder.setPrincipal(group);
                        /*AclEntryType
                        A typesafe enumeration of the access control entry types.
                        ALLOW:Explicitly grants access to a file or directory.
                        (来自官方文档)
                         */
                        builder.setType(AclEntryType.ALLOW);

                        //搜索之前所有用户权限
                        //aclView.getAcl():获取视图的访问权限列表
                        List<AclEntry> aclEntries = aclView.getAcl();
                        //获取第一个文件系统对象(在我这里获取到的是system组)的访问权限中的permissions
                        Set<AclEntryPermission> permissions = aclEntries.get(0).permissions();

                        builder.setPermissions(permissions);

                        //创建AclEntry条目
                        AclEntry newEntry = builder.build();

                        //把新的用户权限加入acl权限列表
                        aclEntries.add(newEntry);

                        //将该权限列表应用于视图中
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
