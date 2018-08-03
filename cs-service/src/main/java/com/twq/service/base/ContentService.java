package com.twq.service.base;

import com.twq.dao.mapper.ContentInfoMapper;
import com.twq.dao.model.ContentInfo;
import com.twq.dao.model.ContentInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页信息
 */
@Service
public class ContentService {


    @Autowired
    private ContentInfoMapper contentInfoMapper;

    public ContentInfo getContent() {
        List<ContentInfo> projectInfos = contentInfoMapper.selectByExample(new ContentInfoExample());
        return (projectInfos != null && projectInfos.size() > 0) ? projectInfos.get(0) : null;
    }
}
