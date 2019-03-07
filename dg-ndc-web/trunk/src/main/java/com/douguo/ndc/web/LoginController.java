package com.douguo.ndc.web;

import com.douguo.ndc.model.User;
import com.douguo.ndc.service.UserService;
import com.douguo.ndc.util.ConstantStr;
import com.douguo.ndc.util.CookieUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lichang on 2018/7/27
 */
@Controller public class LoginController extends CommonController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired private UserService userService;

    @RequestMapping(value = {"/", "/index", "/login"}, method = RequestMethod.GET) public String toLogin() {
        return "pages/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String sname, String password,
        @RequestParam(value = "rememberMe", defaultValue = "false",required = false) Boolean rememberMe, Model model) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(sname, password);
        token.setRememberMe(rememberMe);
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                //设置 登录信息
                String principal = (String)subject.getPrincipal();
                User user=userService.selectByIdentify(principal);
                session.setAttribute(ConstantStr.SESSION_USER, user);
                session.setAttribute(ConstantStr.UID, user.getUid());
                //添加  Cookie
                CookieUtil.getInstance().addCookie(response, sname,password, CookieUtil.COOKIE_MAX_AGE);
                log.info("用户:"+principal+"登录成功");

                return "pages/index";
            }
        } catch (IncorrectCredentialsException e) {
            model.addAttribute(ConstantStr.MESSAGE, "登录密码错误");
        } catch (ExcessiveAttemptsException e) {
            model.addAttribute(ConstantStr.MESSAGE, "登录失败次数过多");
        } catch (LockedAccountException e) {
            model.addAttribute(ConstantStr.MESSAGE, "帐号已被锁定");
        } catch (DisabledAccountException e) {
            model.addAttribute(ConstantStr.MESSAGE, "帐号已被禁用");
        } catch (ExpiredCredentialsException e) {
            model.addAttribute(ConstantStr.MESSAGE, "帐号已过期");
        } catch (UnknownAccountException e) {
            model.addAttribute(ConstantStr.MESSAGE, "帐号不存在");
        } catch (UnauthorizedException e) {
            model.addAttribute(ConstantStr.MESSAGE, "您没有得到相应的授权！");
        }

        return "pages/login";
    }

    @RequestMapping(value = "/queryAllUser",method = RequestMethod.GET)
    public String selectAllUser(Model model){

        model.addAttribute("userList",userService.selectAllUser());
        return "pages/staff_manage";
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET) public String logout() {
        session.removeAttribute(ConstantStr.SESSION_USER);
        return "pages/login";
    }



}
