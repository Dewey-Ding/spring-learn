package com.example.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.learn.common.AdminUser;
import com.example.learn.common.UserDto;
import com.example.learn.configuration.BaseProperties;
import com.example.learn.service.BaseService;
import com.example.utils.kafkaDelay.KafkaDelayCommitter;
import com.example.utils.kafkaDelay.KafkaDelayListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dewey
 * @date 2019-11-02 19:32
 */
@RestController
@RequestMapping("/test")
public class BaseController {
    @Autowired
    private BaseService baseService;
    @Autowired
    private BaseProperties baseProperties;
    @Autowired
    private ConversionService conversionService;

    @RequestMapping("/add")
    public String test( Integer a, Integer b){
        UserDto userDto = conversionService.convert("test", UserDto.class);
        System.out.println(userDto.getAge());
        Integer result = baseService.add(a,b);
        return result.toString();
    }

    @RequestMapping("/enumtest")
    public String enumTest(@RequestBody AdminUser adminUser){
        return JSONObject.toJSONString(adminUser);

    }

//    @KafkaDelayListener(topic = "test-delay")
//    public void onMessage(ConsumerRecord<String,String> record){
//        System.out.println(record.value());
//    }
//
//    @KafkaDelayListener(topic = "test-delay",delayTimeSec = 2 )
//    public void onMessage(ConsumerRecord<String,String> record, KafkaDelayCommitter committer){
//        System.out.println(record.value());
//        committer.commitSync();
//    }
//    @KafkaDelayListener(topic = "test-delay")
//    public void onMessage1(ConsumerRecord<String,String> record){
//        System.out.println(record.value());
//    }



}
