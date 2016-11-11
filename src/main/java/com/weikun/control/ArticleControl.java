package com.weikun.control;

import com.weikun.service.ArticleServiceImpl;
import com.weikun.service.IArticleService;
import com.weikun.vo.Article;
import com.weikun.vo.BBSUser;
import com.weikun.vo.PageBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11.
 */
@WebServlet(name = "ArticleControl",urlPatterns = {"/article"},
   initParams = {
           @WebInitParam(name="show",value = "show.jsp")

   })
public class ArticleControl extends HttpServlet {
    private Map<String,String> map=new HashMap<String,String>();
    private IArticleService service=new ArticleServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");//
        RequestDispatcher dispatcher=null;
        switch (action){
            case "queryall":{//查询主表
                String rootid=request.getParameter("rootid");
                String curpage=request.getParameter("curpage");
                BBSUser user=(BBSUser)request.getSession().getAttribute("user");
                int uid=  user==null?999:user.getId();
                PageBean pb=service.queryArticleAll(Integer.parseInt(rootid),Integer.parseInt(curpage),uid);
                request.setAttribute("pb",pb);
                dispatcher=request.getRequestDispatcher(map.get("show"));
                break;
            }


        }
        dispatcher.forward(request,response);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("show",config.getInitParameter("show"));
    }
}
