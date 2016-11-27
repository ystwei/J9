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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11.
 */
@WebServlet(name = "ArticleControl",urlPatterns = {"/article"},
   initParams = {
           @WebInitParam(name="show",value = "show.jsp"),
           @WebInitParam(name="success",value = "article?action=queryall&rootid=0&curpage=1")


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
            case "del": {//删除帖子
                String id=request.getParameter("id");//主贴id
                if(service.delArticle(Integer.parseInt(id))){
                    dispatcher=request.getRequestDispatcher(map.get("success"));
                }

                break;
            }
            case "addz": {//增加主帖子
                Article a=new Article();
                String title=new String(request.getParameter("title").getBytes("iso8859-1"),"utf-8");
                String content=new String(request.getParameter("content").getBytes("iso8859-1"),"utf-8");
                a.setTitle(title);
                a.setContent(content);
                a.setRootid(0);//主贴
                BBSUser user=(BBSUser)request.getSession().getAttribute("user");


                a.setUser(user);

                if(service.addArticle(a)){//增加成功
                    dispatcher=request.getRequestDispatcher(map.get("success"));
                }

                break;
            }


            case "reply": {//增加从帖子
                Article a=new Article();
                String title=new String(request.getParameter("title").getBytes("iso8859-1"),"utf-8");
                String content=new String(request.getParameter("content").getBytes("iso8859-1"),"utf-8");
                a.setTitle(title);
                a.setContent(content);
                a.setRootid(Integer.parseInt(request.getParameter("rootid")));//主贴
                BBSUser user=(BBSUser)request.getSession().getAttribute("user");


                a.setUser(user);
                if(a.getRootid()==0){
                    if(service.addArticle(a)){//增加成功
                        dispatcher=request.getRequestDispatcher(map.get("success"));
                    }
                }else{
                    if(service.addArticle(a)) {//增加成功
                        dispatcher = request.getRequestDispatcher("article?action=querybyid&rootid=" + a.getRootid());
                    }
                }


                break;
            }
            case "querybyid": {//根据主贴id查询所有从贴

                String result=service.queryArticleById(Integer.parseInt(request.getParameter("rootid")));

                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html");

                PrintWriter out=response.getWriter();
                out.println(result);
                out.flush();
                out.close();
                break;
            }
            case "delreply": {//删除从贴
                int id=Integer.parseInt(request.getParameter("id"));
                int rootid=Integer.parseInt(request.getParameter("rootid"));
                if(service.delReply(id)){

                    dispatcher=request.getRequestDispatcher("article?action=querybyid&rootid="+rootid);
                }
            }
        }
        dispatcher.forward(request,response);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("show",config.getInitParameter("show"));
        map.put("success",config.getInitParameter("success"));
    }
}
