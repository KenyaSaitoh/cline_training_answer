package pro.kensait.berrybooks.web.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.web.login.LoginBean;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 認証チェチE��フィルタ�E�ログインしてぁE��ぁE��ーザーをindex.xhtmlにリダイレクトする！E
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"*.xhtml"})
public class AuthenticationFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Inject
    private LoginBean loginBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                         FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // リクエストされたペ�Eジのパスを取征E
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        logger.debug("AuthenticationFilter - RequestURI: {}", requestURI);
        
        // 認証不要なペ�Eジ�E��E開�Eージ�E��EリスチE
        boolean isPublicPage = requestURI.endsWith("/index.xhtml") 
                || requestURI.endsWith("/customerInput.xhtml")
                || requestURI.endsWith("/customerOutput.xhtml")
                || requestURI.contains("/jakarta.faces.resource/");  // JSF リソース�E�ESS、画像など�E�E
        
        // LoginBeanから直接ログイン状態をチェチE���E�EDIインジェクション経由�E�E
        boolean isLoggedIn = (loginBean != null && loginBean.isLoggedIn());
        
        logger.debug("isPublicPage: {}, isLoggedIn: {}, loginBean: {}", 
                isPublicPage, isLoggedIn, loginBean);
        
        // ログインが忁E��なペ�Eジで未ログインの場合、index.xhtml にリダイレクチE
        if (!isPublicPage && !isLoggedIn) {
            logger.info("未ログインユーザーをリダイレクチE {} -> {}/index.xhtml", 
                    requestURI, contextPath);
            httpResponse.sendRedirect(contextPath + "/index.xhtml");
        } else {
            // 認証OK、また�E認証不要なペ�Eジ ↁE処琁E��続衁E
            chain.doFilter(request, response);
        }
    }
}

