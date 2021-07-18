package com.example.aop.aop_learn.service;

import com.example.aop.aop_learn.common.UserDto;
import com.example.aop.aop_learn.log.LogAnnotation;
import com.example.aop.aop_learn.service.intef.demo;
import javafx.application.Application;
import org.apache.catalina.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BaseService {

    @Autowired
    private BaseService baseService;


    @Autowired
    private Map<String,Object> map = new HashMap<>();

//    @Autowired
//    public void init(Map<String,demo> map){
//        System.out.println("map+interface:"+map.toString());
//    }
//    @Autowired
//    public void init2(Map<String,TestService> map){
//        System.out.println("map+class:"+map.toString());
//    }
//
//    @Autowired
//    public void init3(Set<demo> set){
//        System.out.println("collection+interface:"+set.toString());
//    }
//    @Autowired
//    public void init4(Set<TestService> set){
//        System.out.println("collection+class:"+set.toString());
//    }
//    @Autowired
//    public void init5(demo[]  array){
//        System.out.println("array+interface:"+Arrays.asList(array).toString());
//    }
//    @Autowired
//    public void init6(TestService[] array){
//        System.out.println("array+class:"+Arrays.asList(array).toString());
//    }


    @LogAnnotation(value = "BaseService add")
    public Integer add(Integer a,Integer b){
        //BaseService service = (BaseService) beanFactory.getBean("baseService");
        //service.hello();
        //((BaseService)AopContext.currentProxy()).hello();
        baseService.hello();
        return a+b;
    }

    @LogAnnotation(value = "BaseService hello")
    public String hello(){
        return "hello world";
    }


    public static void main(String[] args) {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto("ame",2));
        users.add(new UserDto("age",0));
        users.add(new UserDto("haha",4));
        users.add(new UserDto("hehe",5));
        users.add(new UserDto("0h",6));
        Map<Integer,UserDto> map = users.stream().filter(one->one.getAge()>8).collect(Collectors.toMap(UserDto::getAge,UserDto->UserDto));
        System.out.println(map.toString());
        System.out.println(map.isEmpty());
        users.sort(Comparator.comparing(UserDto::getAge).reversed());
        System.out.println(users);
    }

}
