package com.nowcoder.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.ConfirmInformationPost;
import com.nowcoder.community.service.ConfirmInformationPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfirmInformationPostController {
    @Autowired
    ConfirmInformationPostService confirmInformationPostService;

    @RequestMapping(path = "/addConfirmInformation", method = RequestMethod.POST)
    @ResponseBody
    //上报确诊信息
    public String postConfirmInformation (ConfirmInformationPost confirmInformationPost) {
        int result=confirmInformationPostService.addConfirmInformation(confirmInformationPost);
        JSONObject json=new JSONObject();
        if(result==0)
            json.put("msg", "添加失败!");
        else
            json.put("msg","添加成功！");
        return json.toJSONString();
    }
}
