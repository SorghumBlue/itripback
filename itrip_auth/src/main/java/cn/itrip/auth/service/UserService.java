package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    ItripUser login(String name, String password) throws Exception;
    ItripUser findByUserCode(String userCode) throws Exception;
}
