package com.douguo.ndc.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lichang on 2018/8/7
 */
@Controller public class CommonController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected Subject subject;
    protected Session session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        subject = SecurityUtils.getSubject();
        session = subject.getSession();
    }
}
