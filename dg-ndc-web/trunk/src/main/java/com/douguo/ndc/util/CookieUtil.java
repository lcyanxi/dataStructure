package com.douguo.ndc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lcyanxi on 2018-08-29.
 */
public class CookieUtil {

    /**
     * 默认最长保存的时间为一周
     */
    public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

    public static final String SNAME = "sname";

    public static final String PASSWORD = "password";

    private static final CookieUtil mInstance = new CookieUtil();

    private CookieUtil() {
        super();
    }

    public static CookieUtil getInstance() {

        return mInstance;
    }

    /**
     * @param response //请求响应
     * @param name     //Cookie的名字
     * @param password //密码
     * @param maxAge   //Cookie 的生命周期
     */
    public void addCookie(HttpServletResponse response, String name, String password, int maxAge) {

        Cookie cookie = new Cookie(SNAME, name);
        Cookie passwordCookie = new Cookie(PASSWORD, password);

        cookie.setPath("/");

        if (maxAge > 0) {

            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
        response.addCookie(passwordCookie);
    }

    /**
     * @param request //请求
     * @param name    //Cookie的名字
     * @return //对应的Cookie
     */
    public Cookie findCookieByCookieName(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals(name)) {

                return cookie;
            }
        }

        return null;
    }
}
