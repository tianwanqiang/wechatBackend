package com.twq.securiy;

import com.twq.util.CSLog;
import com.twq.util.Constants;
import com.twq.util.Nodes;
import com.twq.worksflow.msg.ResHeader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by twq on 2018/1/29. TODO
 */
@Service
public class CheckFields implements Check {
    private final Logger LOGGER = CSLog.get();

    public ResHeader check(String rpid, Map<String, String> reqMsg) {
        ResHeader resHeader = new ResHeader();
        StringBuilder missing = null;
        StringBuilder extra = null;

        //西安判断apiId字段
        if (!reqMsg.containsKey(Nodes.apiId)) {
            resHeader.setRetCode(Constants.RET_CODE_FIELD_ERROR_REQ, Nodes.apiId);
            return resHeader;
        }

        String apiId = reqMsg.get(Nodes.apiId);
        String field = Constants.apiFieldsProperties.getProperty(apiId);
        if (field != null) {
            String fields[] = field.split(",");
            missing = new StringBuilder();
            String confField;
            List<String> list = new ArrayList<String>();

            //判断请求少了什么参数，对配置文件里的参数进行遍历
            for (int i = 0; i < fields.length; i++) {
                confField = fields[i].trim();
                if (confField.startsWith("^")) {//配置文件里^开头的参数忽略
                    list.add(confField.substring(1));
                } else if (!reqMsg.containsKey(confField)) {
                    missing.append(confField).append(",");
                    list.add(confField);
                } else {
                    list.add(confField);
                }
            }
            extra = new StringBuilder();


            for (Map.Entry<String, String> entry : reqMsg.entrySet()) {//判断请求多传了什么参数，apiId、clientType忽略
                if (!list.contains(entry.getKey()) && !Nodes.apiId.equals(entry.getKey()) && !Nodes.clientType.equals(entry.getKey())) {
                    extra.append(entry.getKey()).append(",");
                }
            }
        }
        if (missing != null && missing.length() > 0 && extra != null && extra.length() > 0) {
            resHeader.setRetCode(Constants.RET_CODE_FIELD_ERROR_ALL, missing.substring(0, missing.length() - 1), extra.substring(0, extra.length() - 1));
        } else if (missing != null && missing.length() > 0) {
            resHeader.setRetCode(Constants.RET_CODE_FIELD_ERROR_REQ, missing.substring(0, missing.length() - 1));
        } else if (extra != null && extra.length() > 0) {
            resHeader.setRetCode(Constants.RET_CODE_FIELD_ERROR_SEND, extra.substring(0, extra.length() - 1));
        } else if (missing == null && extra == null) {
            CSLog.debugRPID(LOGGER, rpid, "【请求参数校验】接口{}未添加校验信息跳过校验.", apiId);
        } else {
            CSLog.debugRPID(LOGGER, rpid, "【请求参数校验】接口{}校验通过.", apiId);
        }
        return resHeader;
    }
}
