package ru.itmo.wp.servlet;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CaptchaFilter extends HttpFilter {
    private static final String CAPTCHA_VALUE = "code";
    private static final String PASS_TAG = "pass";
    private final Configuration configuration = new Configuration();

    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        try {
            configuration.setDirectoryForTemplateLoading(
                    new File(config.getServletContext().getRealPath("static/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equalsIgnoreCase("POST")) {
            if (isCaptcha(request)) {
                if (isCaptchaRequest(request)) {
                    passCaptcha(request);
                } else {
                    refreshCaptcha(request);
                }
                String location = request.getParameter("location");
                response.setHeader("Location", location);
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                return;
            }
        }

        if (request.getMethod().equalsIgnoreCase("GET")) {
            if (request.getRequestURI().equals("/favicon.ico")) {
                chain.doFilter(request, response);
                return;
            }

            if (!Boolean.TRUE.equals(request.getSession(true).getAttribute(PASS_TAG))) {
                int code = refreshCaptcha(request);
                String location = request.getRequestURI();
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("text/html");
                response.getWriter().print(generateHTML(code, location));
                response.getWriter().flush();
                return;
            }
        }
        chain.doFilter(request, response);
    }


    private String generateHTML(int code, String location) {
        Map<String, String> values = new HashMap<>();
        Writer stringWriter = new StringWriter();
        String encoded = Base64.getEncoder().encodeToString(ImageUtils.toPng(String.valueOf(code)));
        values.put("encoded", encoded);
        values.put("location", location);
        try {
            configuration.getTemplate("captcha.html").process(values, stringWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    private void passCaptcha(HttpServletRequest request) {
        request.getSession(false).setAttribute(PASS_TAG, true);
    }

    private int refreshCaptcha(HttpServletRequest request) {
        Integer randomKey = new SecureRandom().nextInt(100);
        request.getSession(true).setAttribute(CAPTCHA_VALUE, String.valueOf(randomKey));
        return randomKey;
    }

    private boolean isCaptchaRequest(HttpServletRequest request) {
        String sessionkey = (String) request.getSession().getAttribute(CAPTCHA_VALUE);
        String key = (String) request.getParameter(CAPTCHA_VALUE);
        if (sessionkey.isEmpty() || key.isEmpty()) {
            return false;
        }
        return key.equals(sessionkey);
    }

    private boolean isCaptcha(HttpServletRequest request) {
        return request.getRequestURI().equals("/captcha");
    }

}

