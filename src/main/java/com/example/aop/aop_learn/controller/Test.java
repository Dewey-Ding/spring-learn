package com.example.aop.aop_learn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.aop.aop_learn.common.UserDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/test")
@Controller
public class Test {
    @RequestMapping("/abc/v{ver}")
    @ResponseBody
    public UserDto getOne(@PathVariable(value = "ver") Integer ver, @RequestBody JSONObject jsonParam){
        System.out.println(JSONObject.toJSONString(jsonParam));
        return  new UserDto("hello world",1);
    }

    @RequestMapping("/json/v{ver}")
    @ResponseBody
    public String getJson(@PathVariable(value = "ver") Integer ver,@RequestBody String content){
        Map<String, JSONObject> dataMap = JSON.parseObject(content, Map.class);
        if(dataMap != null && !dataMap.isEmpty()){
            for(Map.Entry<String,JSONObject> entry : dataMap.entrySet()){
                JSONObject jsonObject = entry.getValue();
                String[] tokens = null;
                if(jsonObject != null && (tokens = ((String)jsonObject.get("targets")).split(",")).length>0){
                    for (String one:tokens){
                        System.out.println(one);
                    }
                }
            }
        }
//        JSONObject data = JSONObject.parseObject(content);
//        JSONArray jsonArray = data.getJSONArray("statuses");
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject object = jsonArray.getJSONObject(i);
//            System.out.println(object.get("biTag"));
//        }
        //List<JSONObject> list = JSONObject.parseA
//        JSONArray jsonArray = JSONObject.parseArray(content);
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject one = jsonArray.getJSONObject(i);
//            String[] tokens = ((String)one.get("registrationIds")).trim().split(",");
//            for (int j = 0; j < tokens.length; j++) {
//                System.out.println(tokens[j]);
//            }
//
//        }
        System.out.println(content);
        return "content";
    }


    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"code\": 0,\n" +
                "    \"message\": \"asf\",\n" +
                "    \"data\": {\n" +
                "        \"10000\": [\n" +
                "            \"J0476035d625e6c64567f71487e040e7d017f0558675b\",\n" +
                "            \"J0476045d625e6c64567f71487e040e7d017f0558675b\",\n" +
                "            \"J0476035d625e6sd64567f71487e040e7d017f0558675b\"\n" +
                "        ],\n" +
                "        \"message_id\": \"xxxxxxxxx\",\n" +
                "        \"task_id\": \"XXXXXXXXXX\"\n" +
                "    }\n" +
                "}");
        String invaild = jsonObject.getJSONObject("data").getString("10000");
        if(invaild!=null){
            List<String> invalid = JSONObject.parseArray(invaild,String.class);
            System.out.println(invaild.toString());
        }
    }
}
