package com.weikun.control;

import com.weikun.service.IUserService;
import com.weikun.service.UserServiceImpl;
import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6.
 */
@WebServlet(name = "UserControl",urlPatterns ={"/user"},initParams = {
        @WebInitParam(name="show",value = "article?action=queryall&rootid=0&curpage=1")



})
public class UserControl extends HttpServlet {
    private Map<String,String> map=new HashMap();
    private IUserService service=new UserServiceImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("show",config.getInitParameter("show"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(ServletFileUpload.isMultipartContent(request)){//代表上传的格式是二进制
            //注册。
            String spath=request.getServletContext().getRealPath("/");



            String tmpPath = "" + System.getProperty("file.separator") + "tmpdir";
            File tmpDir=  new File(tmpPath);//临时上传文件路径

            if (!tmpDir.isDirectory())//如果不存在
                tmpDir.mkdir();//就创建



            DiskFileItemFactory df=new DiskFileItemFactory();
            df.setRepository(tmpDir);//把要上传的文件都放到这个临时目录下
            df.setSizeThreshold(1024*1024*10);//开多大缓存

            ServletFileUpload sf=new ServletFileUpload(df);
            sf.setFileSizeMax(1024*1024*10);//设置单个文件大小
            sf.setSizeMax(1024*1024*10*10);//设置总共上传文件的大小
            RequestDispatcher dispatcher=null;

            try {
                FileItemIterator fii=sf.getItemIterator(request);
                //1.上传头像
                BBSUser user=service.uploadPic(fii,spath);
                //2保存头像到数据库

                if(service.register(user)){//存储成功
                    dispatcher = request.getRequestDispatcher(map.get("show"));
                    dispatcher.forward(request, response);
                }

            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }else{//文本
            String action=request.getParameter("action");
            if(action.equals("login")){
                login(request, response);
            }else if(action.equals("pic")){
                String id=request.getParameter("id");
                byte [] buffer=service.queryPicByid(Integer.parseInt(id));

                response.setContentType("image/jpeg");
                OutputStream os=response.getOutputStream();
                os.write(buffer);

                os.flush();
                os.close();
            }

        }


    }


    public void getPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        BBSUser user = new BBSUser();
        user.setPassword(password);
        user.setUsername(username);
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();
        BBSUser u=service.login(user);
        if (u!=null) {//登录成功            //


            session.setAttribute("user", u);//把该成功的用户放到内存中
            if (request.getParameter("sun") != null) {//勾上了 记住一星期
                Cookie c = new Cookie("www.papaok.org/username", username);
                c.setMaxAge(3600 * 24 * 7);
                Cookie c1 = new Cookie("www.papaok.org/password", password);

                c1.setMaxAge(3600 * 24 * 7);
                response.addCookie(c);
                response.addCookie(c1);
            }
            request.setAttribute("info", "欢迎:");
        } else {
            session.removeAttribute("user");
            request.setAttribute("info", "非法登录！");
        }
        dispatcher = request.getRequestDispatcher(map.get("show"));
        dispatcher.forward(request, response);
    }
}
