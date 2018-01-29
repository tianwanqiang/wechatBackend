package com.twq.business.platform;


import com.twq.business.util.SpringUtil;
import com.twq.exception.CustomException;
import com.twq.util.CSLog;
import com.twq.util.Constants;
import com.twq.util.Nodes;
import com.twq.worksflow.WorksController;
import com.twq.worksflow.WorksDoData;
import com.twq.worksflow.WorksGroup;
import com.twq.worksflow.WorksRetData;
import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by twq on 2018/1/29.
 */

@Service
public class SimpleWorksController implements WorksController {
    private static Logger LOGGER = CSLog.get();

    //每个流程在xml配置
    @Override
    public WorksRetData doFlow(Map<String, String> reqMsg) {
        String apiId = reqMsg.get(Nodes.apiId);
        String flowId = Constants.apiMapperInstans.getProperty("twq_" + apiId);
        Map<String, String> gc = reqMsg;
        WorksDoData doData = null;
        WorksRetData retData = new WorksRetData();
        try {
            if (flowId == null) {
                throw new IllegalArgumentException();
            }
            Object worksGroup = SpringUtil.getBean(flowId);
            CSLog.debugRPID_In(LOGGER, reqMsg.get(Nodes.rpid), "==>搜索到流程:【{}】", flowId);
            doData = ((WorksGroup) worksGroup).startingWorks(gc);
            //对执行结果进行转换
            retData.setRetData(doData.getOutData());
        } catch (NumberFormatException e) {
            retData.setRetCode(Constants.RET_CODE_DB_ERROR);
            e.printStackTrace();
        } catch (NoSuchBeanDefinitionException e) {
            retData.setRetCode(Constants.RET_CODE_CONFIG_ERROR, flowId);
            CSLog.errorRPID(LOGGER, reqMsg.get(Nodes.rpid), "==>未搜到流程：【{}】", flowId);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            retData.setRetCode(Constants.RET_CODE_API_MAPPER_NOT_EXIST, apiId);
            e.printStackTrace();
        } catch (CustomException e) {
            retData.setRetCode(e.getRetCode());
            //e.printStackTrace();
        } catch (Exception e) {
            retData.setRetCode(Constants.RET_CODE_SYSTEM_ERROR);
            e.printStackTrace();
        }
        return retData;
    }
}
