package com.weikun.control;

import com.weikun.service.IUserService;
import com.weikun.service.UserServiceImpl;
import com.weikun.vo.BBSUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6.
 */
@WebServlet(name = "UserControl",urlPatterns ={"/user"},initParams = {
        @WebInitParam(name="show",value = "show.jsp")



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
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        BBSUser user=new BBSUser();
        user.setPassword(password);
        user.setUsername(username);
        RequestDispatcher dispatcher=null;
        HttpSession session=request.getSession();
        if(service.login(user)){//登录成功
            //


            session.setAttribute("user",user);//把该成功的用户放到内存中
            if(request.getParameter("sun")!=null){//勾上了 记住一星期
                Cookie c=new Cookie("www.papaok.org/username",username);
                c.setMaxAge(3600*24*7);
                Cookie c1=new Cookie("www.papaok.org/password",password);

                c1.setMaxAge(3600*24*7);
                response.addCookie(c);
                response.addCookie(c1);
            }
            request.setAttribute("info","欢迎:");
        }else{
            session.removeAttribute("user");
            request.setAttribute("info","非法登录！");
        }
        dispatcher=request.getRequestDispatcher(map.get("show"));
        dispatcher.forward(request,response);

    }
}
