package com.jiantou.demo.controller;

import com.jiantou.demo.shiro.ShiroUtils;
import com.jiantou.demo.util.R;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysLoginController {

    @ApiOperation(value = "登录")
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public R login(String username, String password) {
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

        return R.ok();
    }


    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public R logout() {
        ShiroUtils.logout();
        return R.ok();
    }
}
