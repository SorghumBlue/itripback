package cn.itrip.auth.service.impl;

import cn.itrip.auth.service.UserService;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private ItripUserMapper userMapper;
    @Override
    public ItripUser login(String name, String password) throws Exception {
        ItripUser itripUser = findByUserCode(name);
        if(itripUser != null && itripUser.getUserPassword().equals(password)){
            if(itripUser.getActivated() != 1){
                throw new Exception("用户未激活");
            }else {
                return itripUser;
            }
        }
        return null;
    }

    @Override
    public ItripUser findByUserCode(String userCode) throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("userCode",userCode);
        List<ItripUser> list = userMapper.getItripUserListByMap(map);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
