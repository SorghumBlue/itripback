package com.kgc.test;

import cn.itrip.common.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class UserTest {
    @Resource
    private RedisUtil redisUtil;
    @Test
    public void test01(){
        System.out.println(redisUtil.getList("list"));
    }
}
