package dev.cline.apricot.cdi.conversation;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/*")
public class SessionFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest hreq = (HttpServletRequest)request;

        // リクエストの文字コードをUTF-8にセットする
        hreq.setCharacterEncoding("UTF-8");

        // セッションを生成しておく
        HttpSession session = hreq.getSession(true);
        // セッションに対して同期処理を行う
        synchronized(session) {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}