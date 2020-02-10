package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface TokenService {
    String generateToken(String userAgent, ItripUser itripUser) throws Exception;
    void saveToken(String token, ItripUser itripUser) throws Exception;
}
