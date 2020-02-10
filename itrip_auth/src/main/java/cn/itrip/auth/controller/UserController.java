package cn.itrip.auth.controller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Dto doLogin(String name, String password, HttpServletRequest request){
        try {
            ItripUser user = userService.login(name, MD5.getMd5(password,32));
            //登陆失败
            if(user == null){
                return DtoUtil.returnFail("用户名密码错误", ErrorCode.AUTH_UNKNOWN);
            }
            //登录成功   生成token
            String userAgent = request.getHeader("user-agent");
            String token = tokenService.generateToken(userAgent, user);
            //保存token到redis
            tokenService.saveToken(token,user);
            //返回一个tokenVo对象
            ItripTokenVO tokenVO = new ItripTokenVO(token,
                    Calendar.getInstance().getTimeInMillis()+2*60*60*1000,
                    Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnSuccess("登录成功",tokenVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }
}
