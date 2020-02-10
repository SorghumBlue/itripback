package cn.itrip.auth.service.impl;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisUtil;
import cn.itrip.common.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    //token:PC-userCode(md5)-userid-creationdate-random(6位)
    @Override
    public String generateToken(String userAgent, ItripUser itripUser) throws Exception {
        StringBuilder str = new StringBuilder("token:");
        //判断客户端类型
        if(!UserAgentUtil.CheckAgent(userAgent)){
            str.append("PC-");
        }else {
            str.append("MOBILE-");
        }
        //userCode的加密
        str.append(MD5.getMd5(itripUser.getUserCode(),32)+"-");
        //userid
        str.append(itripUser.getId()+"-");
        //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        str.append(sdf.format(new Date())+"-");
        //6位随机数
        str.append(MD5.getMd5(userAgent,6)+"-");
        return str.toString();
    }
    @Resource
    private RedisUtil redisUtil;
    @Override
    public void saveToken(String token, ItripUser itripUser) throws Exception {
        String json = JSONObject.toJSONString(itripUser);
        if(token.startsWith("token:PC-")){
            //PC端需要设置过期时间
            redisUtil.setString(token, json, 2*60*60);
        }else {
            //移动端不需要过期时间
            redisUtil.setString(token, json);
        }
    }
}
