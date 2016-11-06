package com.weikun.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/6.
 */
@WebFilter(filterName = "EncodingFilter",
        initParams ={@WebInitParam(name="encoder",value = "utf-8")}

)
public class EncodingFilter implements Filter {
    String encoder="";
    public EncodingFilter(){

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(encoder);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        encoder=config.getInitParameter("encoder");
    }

}
