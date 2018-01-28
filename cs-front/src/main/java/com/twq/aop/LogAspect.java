package com.twq.aop;


import com.alibaba.fastjson.JSON;
import com.twq.dao.model.Users;
import com.twq.message.ReqMsg;
import com.twq.service.ConfigService;
import com.twq.service.UsersService;
import com.twq.util.CSLog;
import com.twq.util.Constants;
import com.twq.util.Nodes;
import com.twq.util.SerialUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());//TODO 这么写的原因
    private List<String> noAopMethods = Arrays.asList("doPayNotice", "doLogin", "wechatAuthCallback", "retrieveH5Game", "retrieveH5GameTop", "retrieveH5Sign", "doLoginSignCode");
    @Resource
    private UsersService usersService;
    @Resource
    private ConfigService configService;
    private String appKey;

    /**
     * 切入点
     * 返回值 包名 任意子包 任务方法
     */
    @Pointcut("execution(public * com.twq.controller..*.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String rpid = "";
        ReqMsg req = null;
        Object result = null;
        Map<String, String> reqMsg = null;
        long now = -1;
        CSLog.info("WebLogAspect.doAround()");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            reqMsg = new HashMap<>();
            String paramName = enu.nextElement();
            reqMsg.put(paramName, request.getParameter(paramName));
        }
        try {
            if (noAopMethods.contains(methodName)) {
                result = joinPoint.proceed();
                return result;
            }
            rpid = SerialUtil.getRPID();
            now = System.currentTimeMillis();
            req = (ReqMsg) joinPoint.getArgs()[0];
            req.rpid = rpid;
            CSLog.debugRPID(logger, rpid, "-{}==>收到请求,包体:{}", methodName, JSON.toJSONString(req));
            String retCode = null;
            String userId = req.getUserId();

            if ((retCode = checkSign(req, methodName)) != "0") {
//                result = new ResMsg(retCode);//TODO
            } else {
                result = joinPoint.proceed();
            }

        } catch (Throwable e) {
            //根据自定义异常返回不同相应
            e.printStackTrace();
        }
        return result;
    }

    private String checkSign(ReqMsg req, String methodName) throws Exception {
        Users user = usersService.getUserById(req.getUserId());

        if (user == null)
            return Constants.RET_CODE_LOGINED_USER_NOT_EXIST;

        if (user.getAccountType() != null) {
            req.setAccountType(user.getAccountType().toString());
        } else
            req.setAccountType("3");//微信用户 //TODO

        if (appKey == null) {
            appKey = configService.getPlatParameters().get(Nodes.appKey);
        }
        if (!req.checkSession(methodName, user, appKey))
            return "-999";//session error TODO

        req.setExtra(user);
        return String.valueOf(0);
    }
}
