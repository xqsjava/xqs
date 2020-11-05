package com.deyuan.controller;


import com.deyuan.pojo.SysLog;
import com.deyuan.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    private Date visitTime;//操作时间
    private Class classzz; //访问类
    private Method method; //访问的方法名称

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    //前置
    @Before("execution(* com.deyuan.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime= new Date();
        //访问具体类
        classzz = jp.getTarget().getClass();
        //执行具体的方法名称
        String methodName = jp.getSignature().getName();//获取访问的方法名称
        //获取访问方法的参数
        Object[] args = jp.getArgs();
        if (args==null||args.length==0) {
            method = classzz.getMethod(methodName);//只能获取到无参的构造方法
        }else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0;i<args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            //封装参数
            classzz.getMethod(methodName,classArgs);
        }
    }

    //后置通知
    @After("execution(* com.deyuan.controller.*.*(..))")
    public void doAfter(){
           long time = new Date().getTime()-visitTime.getTime();//访问的时长
        //获取操作的URL   通过反射的方式获取
        String url = "";
        if (classzz!= null && method!=null && classzz!=LogAop.class){
            //获取类上的RequestMapping 注解  获取注解里的内容
           RequestMapping classAnnotation = (RequestMapping) classzz.getAnnotation(RequestMapping.class);
           if (classAnnotation!=null){
               String[] classValue = classAnnotation.value();//获取到类上的requestMapping的value值
               //获取方法中requestMapping注解
               RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
               //取出里面的value值
               String[] methodValue = methodAnnotation.value();
               url = classValue[0]+methodValue[0];

               String ip =request.getRemoteAddr();//获取请求的ip地址
               //获取当前的用户操作
               SecurityContext context = SecurityContextHolder.getContext();
               //获取到当前的操作用户
               User principal = (User) context.getAuthentication().getPrincipal();
               //获取当前用户名
               String userName = principal.getUsername();
               SysLog sysLog = new SysLog();
               sysLog.setIp(ip);//ip地址
               sysLog.setExecutionTime(time);//执行时长
               sysLog.setMethod("[类名] "+classzz.getName()+"[方法名]"+method.getName());//访问的方法
               sysLog.setUrl(url);//请求的url
               sysLog.setUsername(userName);//当前访问的用户
               sysLog.setVisitTime(visitTime);//访问的时间
               sysLogService.save(sysLog);
           }
        }


    }
}
