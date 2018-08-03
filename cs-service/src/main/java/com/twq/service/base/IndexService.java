package com.twq.service.base;

import com.twq.dao.mapper.ProjectInfoMapper;
import com.twq.dao.model.ProjectInfo;
import com.twq.dao.model.ProjectInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页信息
 */
@Service
public class IndexService {


    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    public ProjectInfo getProject() {
        List<ProjectInfo> projectInfos = projectInfoMapper.selectByExample(new ProjectInfoExample());
        return (projectInfos != null && projectInfos.size() > 0) ? projectInfos.get(0) : null;
    }
}
