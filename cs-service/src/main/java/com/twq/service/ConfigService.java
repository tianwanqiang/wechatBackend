package com.twq.service;

import com.twq.dao.mapper.ParametersMapper;
import com.twq.dao.model.Parameters;
import com.twq.dao.model.ParametersExample;
import com.twq.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService {

    @Autowired
    private ParametersMapper parametersMapper;

    /**
     * 获取所有系统参数
     *
     * @return
     */
    public Map<String, String> getPlatParameters() {
        ParametersExample example = new ParametersExample();
        example.createCriteria().andParamTypeEqualTo(Constants.PARAM_TYPE_SYS);
        List<Parameters> rbases = parametersMapper.selectByExample(example);
        Map<String, String> configs = new HashMap<String, String>();
        Parameters base = null;
        for (int i = 0; i < rbases.size(); i++) {
            base = rbases.get(i);
            configs.put(base.getParamKey(), base.getParamValue());
        }
        return configs;
    }

}
