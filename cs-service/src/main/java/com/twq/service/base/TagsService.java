package com.twq.service.base;

import com.twq.dao.mapper.TagsMapper;
import com.twq.dao.mapper.UserInfoMapper;
import com.twq.dao.model.Tags;
import com.twq.dao.model.TagsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {


    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    public List<Tags> getTagsInfo() {
        return tagsMapper.selectByExample(new TagsExample());
    }
}
