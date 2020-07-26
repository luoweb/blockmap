package com.nowcoder.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Subinstitution;
import com.nowcoder.community.service.SubinstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SubinstituionController {
    @Autowired
    SubinstitutionService subinstitutionService;
    @RequestMapping(path="/findWorkInstitution",method = RequestMethod.GET)
    public String findWorkInstitution(Model model,@RequestParam(value="page",required=false,defaultValue="1") Integer page,@RequestParam(value="limit",required=false,defaultValue="10") Integer limit){
        List<Subinstitution> workableSubinstitudeList=subinstitutionService.findWorkSubinstitution(page,limit);
        model.addAttribute("workableSubinstitudeList",workableSubinstitudeList);
        return "/index";
    }
    @RequestMapping(path = "/addSubinstitution", method = RequestMethod.POST)
    @ResponseBody
    public String addInstitution(Subinstitution subinstitution){
        int result=subinstitutionService.addSubinstitution(subinstitution);
        JSONObject json = new JSONObject();
        if(result==0)
           json.put("msg", "添加失败!");
        else
            json.put("msg","添加成功！");
        return json.toJSONString();
    }
    @RequestMapping(path = "/delSubinstitution", method = RequestMethod.POST)
    @ResponseBody
    public String delInstitution(@RequestParam(value="id",required=true) Integer id){
        int result=subinstitutionService.deletSubinstitutionById(id);
        JSONObject json = new JSONObject();
        if(result==0)
            json.put("msg", "删除失败!");
        else
            json.put("msg","删除成功！");
        return json.toJSONString();
    }
    @RequestMapping(path = "/updateSubinstitution", method = RequestMethod.POST)
    @ResponseBody
    public String updateInstitution(@RequestParam(value="id",required=true) Integer id,@RequestParam(value="riskLevel",required=true) Integer riskLevel){
        int result=subinstitutionService.updateInstitutionRiskLevel(id,riskLevel);
        JSONObject json = new JSONObject();
        if(result==0)
            json.put("msg", "更新失败!");
        else
            json.put("msg","更新成功！");
        return json.toJSONString();
    }
}
