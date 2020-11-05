package com.deyuan.controller;


import com.deyuan.pojo.Role;
import com.deyuan.pojo.UserInfo;
import com.deyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    //用户关联角色操作--添加角色
    @RequestMapping("/addRoleToUser")
    public String addRoleToUser(@RequestParam(name = "userId",required = true)String userId,@RequestParam(name = "ids",required = true)String[] roles){
           userService.addRoleToUser(userId,roles);
           return "redirect:findAll.do";
    }


    //用户关联角色操作
    @RequestMapping("/findUserByIdAndAllRole")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id",required = true) String userId){
UserInfo userInfo = userService.findById(userId);
List<Role> roleList = userService.findOtherRoles(userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",userInfo);
        //根据用户id查询出没有的用户
        mv.addObject("roleList",roleList);
        mv.setViewName("user-role-add");
        return mv;
    }

    //查询用户详情
    @RequestMapping("/findById")
    public ModelAndView findById(String id){
         UserInfo userInfo  =  userService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",userInfo);
        mv.setViewName("user-show1");
        return mv;
    }

    //添加用户
    @RequestMapping("/save")
    @PreAuthorize("authentication.principal.username=='dstjava'")
    public String save(UserInfo userInfo){
        userService.save(userInfo);
        return "redirect:findAll.do";
    }


    //查询全部
    @RequestMapping("/findAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll(){
        List<UserInfo> userInfoList =  userService.findAll();
        ModelAndView mv = new  ModelAndView();
        mv.addObject("userList",userInfoList);
        mv.setViewName("user-list");
        return mv;
    }

}
