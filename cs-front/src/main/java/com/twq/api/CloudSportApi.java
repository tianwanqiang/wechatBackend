package com.twq.api;

import com.alibaba.fastjson.JSON;
import com.twq.exception.CustomException;
import com.twq.securiy.CheckSign;
import com.twq.util.*;
import com.twq.worksflow.WorksController;
import com.twq.worksflow.WorksRetData;
import com.twq.worksflow.msg.ResArray;
import com.twq.worksflow.msg.ResHeader;
import com.twq.worksflow.msg.ResMsg;
import com.twq.worksflow.server.ApiRestService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class CloudSportApi implements ApiRestService {

    @Autowired
    private WorksController worksController;
    //    @Autowired
//    private CheckFields checkFields;
    @Autowired
    private CheckSign checkSign;
    private final Logger LOGGER = CSLog.get();

    @Override
    @RequestMapping(path = "/calling", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String callingApi(HttpServletRequest request, HttpServletResponse response) {
        String rpid = SerialUtil.getRPID();
        long now = System.currentTimeMillis();
        String apiId = null;
        String userId = null;
        String token = null;
        String signature = null;
        BufferedReader reader;
        StringBuffer jsonHttpBody;
        String line;
        Map<String, String> reqMsg;
        ResMsg resp = null;
        String log_ignore_api = PropertiesUtils.getDynpropsMapperInstans().getProperty(Nodes.log_ignore_api);

        try {
            request.setCharacterEncoding("utf-8");
            reader = request.getReader();
            jsonHttpBody = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                jsonHttpBody.append(line);
            }
            reader.close();
            token = request.getHeader(Nodes.token);
            userId = request.getHeader(Nodes.userId);
            signature = request.getHeader(Nodes.signature);
            if (token == null || userId == null || signature == null) {//请求头校验
                throw new CustomException(Constants.RET_CODE_HEADER_NOT_ENOUGH);
            }
            reqMsg = Conversion.convertJson2Map(jsonHttpBody.toString());
            //(0)请求包体转为Map格式
            if (!log_ignore_api.contains(apiId)) {
                CSLog.infoRPID_Start(LOGGER, rpid, "收到的请求：{} - {}", request.getRemoteAddr(), jsonHttpBody.toString());
            }

            //(1)字段个数校验，返回缺失字段名
//            ResHeader ckResult = checkFields.check(rpid,reqMsg);
//            if(!ckResult.getRetCode().equals(Constants.RET_CODE_SUCCESSFUL)){
//                resp = new ResMsg(ckResult);
//                throw new CustomException(ckResult.getRetMsg());
//            }
            //(2)签名校验
            reqMsg.put(Nodes.token, token);
            reqMsg.put(Nodes.userId, userId);
            reqMsg.put(Nodes.signature, signature);
            ResHeader ckResult = checkSign.check(rpid, reqMsg);
            if (!ckResult.getRetCode().equals(Constants.RET_CODE_SUCCESSFUL)) {
                resp = new ResMsg(ckResult);
                throw new CustomException(ckResult.getRetMsg());
            }
            reqMsg.put(Nodes.rpid, rpid);
            reqMsg.put(Nodes.clientIp, request.getRemoteAddr());
            WorksRetData ret = worksController.doFlow(reqMsg);
            if (!Constants.RET_CODE_SUCCESSFUL.equals(ret.getRetCode())) {
                resp = new ResMsg(ret.getRetCode(), ret.getRetMsg());
                //throw new CustomException(ret.getRetMsg());
            } else {
                resp = new ResMsg();
                Map<String, String> body = ret.getRetParameter(Nodes.BODY);
                List<Object> items = ret.getRetParameter(Nodes.ITEMS);
                if (body != null || items != null) {
                    resp = new ResArray();
                    ((ResArray) resp).setBody(body);
                    ((ResArray) resp).setItems(items);
                }
            }

        } catch (CustomException e) {
            e.printStackTrace();
            CSLog.errorRPID(LOGGER, rpid, "{}", e.getMessage());
            resp = new ResMsg(e.getRetCode());
        } catch (IOException e) {
            CSLog.errorRPID(LOGGER, rpid, "{}", e.getMessage());
            e.printStackTrace();
            resp = new ResMsg(Constants.RET_CODE_SYSTEM_ERROR);
        } catch (Exception e) {
            CSLog.errorRPID(LOGGER, rpid, "{}", e.getMessage());
            e.printStackTrace();
            resp = new ResMsg(Constants.RET_CODE_SYSTEM_ERROR);
        } finally {
            String ret = JSON.toJSONString(resp);
            String retCode = "N/A" ;
            String retMsg  = "N/A" ;
            if(resp!=null&&resp.getResult()!=null){
                retCode = resp.getResult().getRetCode() ;
                retMsg  = resp.getResult().getRetMsg() ;
            }
            CSLog.infoMpsp("{},{},{},{},{},{}",rpid,apiId,retCode,userId,retMsg,System.currentTimeMillis()-now);
            return ret ;
        }
    }
}
