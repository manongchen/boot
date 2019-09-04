package com.xingchen.boot.utils;

import com.google.common.collect.Maps;
import com.xingchen.boot.entity.User;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.StringJoiner;

public class DateUtilsTest {
    @Test
    public void test(){
        Date now = DateUtils.getNow();
        System.out.println(now);

        System.out.println(DateUtils.parseDateLine(now));

    }

    @Test
    public void test2(){
        String s="1,2,3,4，5，6，7";
        System.out.println(s);
        s = s.replace("，",",");
        System.out.println(s);
        String[] split = s.split(",");
        for (String s1 : split) {
            System.out.println(s1);
        }
        StringJoiner joiner = new StringJoiner("-","[","]");
        StringJoiner joiner2 = new StringJoiner("-");
        for (String s1 : split) {
            joiner.add(s1);
            joiner2.add(s1);
        }
        String joinerStr = joiner.toString();
        System.out.println(joiner2.toString());
        System.out.println(joinerStr);
    }

    @Test
    public void test3(){
        Map map = Maps.newHashMap();
        map.put("id","");
        map.put("ids","1");
        Object id = map.get("id");
        Object ids = map.get("ids");
        System.out.println(StringUtils.isEmpty(id));
        System.out.println(StringUtils.isEmpty(ids));
    }

    @Test
    public void test4(){
        User user = new User();
        user.setUsername("xc");
        user.setPassword("123");

        User user1 = new User();
        //拷贝
        BeanUtils.copyProperties(user,user1);
        user1.setPassword("q11");

        System.out.println(user);
        System.out.println(user1);
        System.out.println(user==user1);

        User user2 = user;
        user2.setUsername("xxc");
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user==user2);


    }

    @Test
    public void test5(){
        StringJoiner joiner = new StringJoiner("--", "[[[_", "_]]]");
        joiner.add("1").add("2").add("3").add("4");

        StringJoiner joiner2 = new StringJoiner("...");
        joiner2.add("a").add("b").add("c");

        joiner.merge(joiner2);
        System.out.println(joiner.toString());

        StringJoiner joiner3 = new StringJoiner("','", "('", "')");
        joiner3.add("1").add("2").add("3").add("4");
        System.out.println(joiner3.toString());

    }
}