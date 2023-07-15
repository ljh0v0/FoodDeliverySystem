package com.myfood.filter;

import com.alibaba.fastjson.JSON;
import com.myfood.common.BaseContext;
import com.myfood.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String[] uris = new String[] {
                "/employee/login",
                "employee/logout",
                "/user/send_msg",
                "/user/login"
        };

        String requestURI = request.getRequestURI();
        log.info("Intercept requests: {}", requestURI);

        if(checkURI(uris, requestURI)){
            log.info("Forward the request: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("employee") != null){
            log.info("User has login");
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("user") != null){
            log.info("User has login");

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("User not login");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    private boolean checkURI(String[] uris, String requestURI){
        for(String uri : uris){
            boolean matched = PATH_MATCHER.match(uri, requestURI);
            if (matched){
                return true;
            }
        }
        return false;
    }
}
