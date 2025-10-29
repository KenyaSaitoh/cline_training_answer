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

// 隱崎ｨｼ繝√ぉ繝・け繝輔ぅ繝ｫ繧ｿ・医Ο繧ｰ繧､繝ｳ縺励※縺・↑縺・Θ繝ｼ繧ｶ繝ｼ繧段ndex.xhtml縺ｫ繝ｪ繝繧､繝ｬ繧ｯ繝医☆繧具ｼ・
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
        
        // 繝ｪ繧ｯ繧ｨ繧ｹ繝医＆繧後◆繝壹・繧ｸ縺ｮ繝代せ繧貞叙蠕・
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        logger.debug("AuthenticationFilter - RequestURI: {}", requestURI);
        
        // 隱崎ｨｼ荳崎ｦ√↑繝壹・繧ｸ・亥・髢九・繝ｼ繧ｸ・峨・繝ｪ繧ｹ繝・
        boolean isPublicPage = requestURI.endsWith("/index.xhtml") 
                || requestURI.endsWith("/customerInput.xhtml")
                || requestURI.endsWith("/customerOutput.xhtml")
                || requestURI.contains("/jakarta.faces.resource/");  // JSF 繝ｪ繧ｽ繝ｼ繧ｹ・・SS縲∫判蜒上↑縺ｩ・・
        
        // LoginBean縺九ｉ逶ｴ謗･繝ｭ繧ｰ繧､繝ｳ迥ｶ諷九ｒ繝√ぉ繝・け・・DI繧､繝ｳ繧ｸ繧ｧ繧ｯ繧ｷ繝ｧ繝ｳ邨檎罰・・
        boolean isLoggedIn = (loginBean != null && loginBean.isLoggedIn());
        
        logger.debug("isPublicPage: {}, isLoggedIn: {}, loginBean: {}", 
                isPublicPage, isLoggedIn, loginBean);
        
        // 繝ｭ繧ｰ繧､繝ｳ縺悟ｿ・ｦ√↑繝壹・繧ｸ縺ｧ譛ｪ繝ｭ繧ｰ繧､繝ｳ縺ｮ蝣ｴ蜷医（ndex.xhtml 縺ｫ繝ｪ繝繧､繝ｬ繧ｯ繝・
        if (!isPublicPage && !isLoggedIn) {
            logger.info("譛ｪ繝ｭ繧ｰ繧､繝ｳ繝ｦ繝ｼ繧ｶ繝ｼ繧偵Μ繝繧､繝ｬ繧ｯ繝・ {} -> {}/index.xhtml", 
                    requestURI, contextPath);
            httpResponse.sendRedirect(contextPath + "/index.xhtml");
        } else {
            // 隱崎ｨｼOK縲√∪縺溘・隱崎ｨｼ荳崎ｦ√↑繝壹・繧ｸ 竊・蜃ｦ逅・ｒ邯夊｡・
            chain.doFilter(request, response);
        }
    }
}

