package com.deyuan.controller;

import com.deyuan.pojo.Permission;
import com.deyuan.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.save(permission);
        return "redirect:findAll.do";
    }


    @RequestMapping("/findAll")
    public ModelAndView findAll(){
        List<Permission> list = permissionService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("permissionList",list);
        mv.setViewName("permission-list");
        return mv;
    }
}
