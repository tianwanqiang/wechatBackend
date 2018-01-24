package com.twq.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {


    private Logger logger = LoggerFactory.getLogger(this.getClass());//TODO 这么写的原因

    /**
     * 切入点
     * 返回值 包名 任意子包 任务方法
     */
    @Pointcut("execution(public * com.twq.controller..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("收到请求========>{}", request.getRequestURL());
        logger.info("HTTP_METHOD  =====> " + request.getMethod());
        logger.info("IP ===============> " + request.getRemoteAddr());
        logger.info("CLASS_METHOD =====> " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS =============> " + Arrays.toString(joinPoint.getArgs()));
        //TODO 签名校验


        //获取所有参数方法一：

//        Enumeration<String> enu = request.getParameterNames();
//
//        while (enu.hasMoreElements()) {
//
//            String paraName = (String) enu.nextElement();
//
//            System.out.println(paraName + ": " + request.getParameter(paraName));
//
//        }
    }

    @After("log()")
    public void doAfter() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("请求结束========>{}", request.getRequestURL());
    }


    /**
     * 获取返回内容
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturn(Object object) {
        logger.info("返回结果========>{}", object.toString());
    }
}
