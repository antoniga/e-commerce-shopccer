package com.shopccer.site.config;

import com.shopccer.common.Constants;
import com.shopccer.common.entity.Ajuste;
import com.shopccer.site.service.AjusteService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AjusteFiltro implements Filter {

    @Autowired
    AjusteService ajusteService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  servletRequest = (HttpServletRequest) request;
        String url = servletRequest.getRequestURL().toString();

        String[] avoidExtensions = { ".css", ".js", ".png", ".jpg" };
        if (Arrays.stream(avoidExtensions).anyMatch(url::endsWith)) {
            chain.doFilter(request, response);
            return;
        }

        List<Ajuste> ajustesGenerales = ajusteService.getAjustesGenerales();

        ajustesGenerales.forEach( ajuste -> {
            request.setAttribute(ajuste.getClave(),ajuste.getValor());
        });

        request.setAttribute("S3_BASE_URI", Constants.S3_BASE_URI);
        chain.doFilter(request,response);

    }
}
