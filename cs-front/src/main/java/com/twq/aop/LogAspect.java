package com.twq.aop;


import com.twq.front.SignCheck;
import com.twq.util.CSLog;
import com.twq.util.Constants;
import com.twq.util.Conversion;
import com.twq.util.Nodes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private SignCheck signCheck;
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
        String log_ignore_api = "";//todo
        String apiId = "-";
        String userId = "-";
        BufferedReader reader;
        StringBuffer jsonHttpBody;
        String line;
        Map<String, String> reqMsg;
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("收到请求========>{}", request.getRequestURL());
        logger.info("HTTP_METHOD  =====> " + request.getMethod());
        logger.info("IP ===============> " + request.getRemoteAddr());
        logger.info("CLASS_METHOD =====> " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS =============> " + Arrays.toString(joinPoint.getArgs()));
        String rpid = UUID.randomUUID().toString();
        request.setAttribute(Nodes.rpid, rpid);
        //TODO 签名校验
        //(2)签名校验
        try {
            request.setCharacterEncoding("UTF8");
            reader = request.getReader();
            jsonHttpBody = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                jsonHttpBody.append(line);
            }
            reader.close();
            //(0)请求包体转为Map格式
            reqMsg = Conversion.convertJson2Map(jsonHttpBody.toString());
            apiId = reqMsg.get(Nodes.apiId);
            if (!log_ignore_api.contains(apiId)) {//TODO 日志忽略接口
                CSLog.infoRPID_Start(logger, rpid, "收到的请求：{}", jsonHttpBody.toString());
            }

            userId = reqMsg.get(Nodes.userId);
            //(1)字段个数校验，返回缺失字段名

//            if (!ckResult.getRetCode().equals(Constants.RET_CODE_SUCCESSFUL)) {
//                resp = new ResMsg(ckResult);
//                throw new CustomException(ckResult.getRetMsg());
//            }

            //(2)签名校验
            Map<String, String> ckResult = signCheck.check(rpid, reqMsg);
//            if (!ckResult.getRetCode().equals(Constants.RET_CODE_SUCCESSFUL)) {
//                resp = new ResMsg(ckResult);
//                throw new CustomException(ckResult.getRetMsg());
//            }
            if (!ckResult.get(Nodes.retCode).equals(Constants.RET_CODE_SUCCESSFUL)) {
//                throw new CustomException(ckResult.get(Nodes.retMsg));
            }

            //(3)开启流程

        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO 签名校验
        //(2)签名校验
        try {
            request.setCharacterEncoding("UTF8");
            reader = request.getReader();
            jsonHttpBody = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                jsonHttpBody.append(line);
            }
            reader.close();
            //(0)请求包体转为Map格式
            reqMsg = Conversion.convertJson2Map(jsonHttpBody.toString());
            apiId = reqMsg.get(Nodes.apiId);
            if (!log_ignore_api.contains(apiId)) {//TODO 日志忽略接口
                CSLog.infoRPID_Start(logger, rpid, "收到的请求：{}", jsonHttpBody.toString());
            }

            userId = reqMsg.get(Nodes.userId);
            //(1)字段个数校验，返回缺失字段名

//            if (!ckResult.getRetCode().equals(Constants.RET_CODE_SUCCESSFUL)) {
//                resp = new ResMsg(ckResult);
//                throw new CustomException(ckResult.getRetMsg());
//            }

            //(2)签名校验
            Map<String, String> ckResult = signCheck.check(rpid, reqMsg);
//            if (!ckResult.getRetCode().equals(Constants.RET_CODE_SUCCESSFUL)) {
//                resp = new ResMsg(ckResult);
//                throw new CustomException(ckResult.getRetMsg());
//            }
            if (!ckResult.get(Nodes.retCode).equals(Constants.RET_CODE_SUCCESSFUL)) {
//                throw new CustomException(ckResult.get(Nodes.retMsg));
            }

            //(3)开启流程

        } catch (Exception e) {
            e.printStackTrace();
        }

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
