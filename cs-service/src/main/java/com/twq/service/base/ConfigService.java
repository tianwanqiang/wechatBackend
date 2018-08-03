package com.twq.service.base;

import com.twq.dao.mapper.SysConfigMapper;
import com.twq.dao.model.SysConfig;
import com.twq.dao.model.SysConfigExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class ConfigService {

    @Autowired
    private SysConfigMapper parametersMapper;

    /**
     * 获取所有系统参数
     *
     * @return
     */
    public Properties getPlatParameters() {
        Properties config = new Properties();
        SysConfigExample example = new SysConfigExample();
        List<SysConfig> sysConfigs = parametersMapper.selectByExample(example);
        for (int i = 0; i < sysConfigs.size(); i++) {
            config.setProperty(sysConfigs.get(i).getConfigKey(), sysConfigs.get(i).getConfigValue());
        }
        return config;
        //        Parameters base = null;
//        for (int i = 0; i < rbases.size(); i++) {
//            base = rbases.get(i);
//            configs.put(base.getParamKey(), base.getParamValue());
//        }
//        return configs;
    }

}
