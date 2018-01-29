package com.twq.api;

import com.twq.api.msg.ResMsg;
import com.twq.exception.CustomException;
import com.twq.server.ApiRestService;
import com.twq.util.*;
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
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class CloudSportApi implements ApiRestService {

    //    @Autowired
//    private WorksController worksController;
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
            reader       = request.getReader();
            jsonHttpBody = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                jsonHttpBody.append(line);
            }
            reader.close();
        } catch (CustomException e) {
            e.printStackTrace();
            CSLog.errorRPID(LOGGER, rpid, "{}", e.getMessage());
            resp = new ResMsg(e.getRetCode());
        }catch (IOException e) {
            CSLog.errorRPID(LOGGER,rpid,"{}",e.getMessage());
            e.printStackTrace();
            resp = new ResMsg(Constants.RET_CODE_SYSTEM_ERROR);
        }catch (Exception e) {
            CSLog.errorRPID(LOGGER,rpid,"{}",e.getMessage());
            e.printStackTrace();
            resp = new ResMsg(Constants.RET_CODE_SYSTEM_ERROR);
        }
        return null;
    }
}
