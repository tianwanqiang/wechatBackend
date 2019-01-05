package com.twq.api;

import com.twq.dao.model.ContentInfo;
import com.twq.dao.model.Games;
import com.twq.dao.model.ProjectInfo;
import com.twq.dao.model.Tags;
import com.twq.service.base.*;
import com.twq.util.Nodes;
import com.twq.util.USER_JOIN_STATUS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(path = "/index")
public class IndexController {

    private static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    IndexService indexService;
    @Autowired
    ContentService contentService;

    @Autowired
    UsersService usersService;

    @Autowired
    GameService gameService;

    @Autowired
    TagsService tagsService;


    @Value("${begin_time}")
    private String begin_time;

    @Value("${end_time}")
    private String end_time;

    @Value("${join_amount}")
    private Integer joinAmount;


    @RequestMapping(path = "/index", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map callingApi(@RequestParam Integer project_id, @RequestParam(value = "user_id", defaultValue = "") String userId,
                          @RequestParam(value = "token", defaultValue = "") String token) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> mainMap = new HashMap<>();
        Map<String, Object> contentMap = new HashMap<>();
        List<Map<String, Object>> tags = new ArrayList<>();

//        UserInfo topStar = usersService.getTopStar();
//        UserInfo constStar = usersService.getConstStar();
//        UserInfo luckStar = usersService.getLuckStar();

        ProjectInfo project = indexService.getProject();
        ContentInfo content = contentService.getContent();
        Map<String, Object> rankNumbers = gameService.getRankNumbers();
        long totayGameCount = gameService.getTomorrowGameCount();
        //TODO 定时任务汇总
        List<Tags> tagsInfo = tagsService.getTagsInfo();
        LOGGER.debug("totalcount{}", totayGameCount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());


        contentMap.put(Nodes.TITLE, content.getTitle());
        contentMap.put(Nodes.CONTENT, content.getContent());
        contentMap.put(Nodes.HOW_TO_PLAY, content.getHowToPlay());
        contentMap.put(Nodes.SHARE_IMG, content.getShareImg());
        contentMap.put(Nodes.SUB_TITLE, content.getSubTitle());


        Field[] declaredFields = ProjectInfo.class.getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            try {
                Field field = declaredFields[i];
                field.setAccessible(true);
                mainMap.put(field.getName(), field.get(project));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Tags tg : tagsInfo
                ) {
            Map<String, Object> earlyMap = new HashMap<>();
            earlyMap.put("tag_name", tg.getTagName());
            earlyMap.put("username", tg.getUserName());
            earlyMap.put("head_img", tg.getHeadImg());
            earlyMap.put("tag_data", tg.getTagData());
            tags.add(earlyMap);
        }

        int joinStatus = USER_JOIN_STATUS.STATUS_NOT_JOINED.getStatusValue();
        String oDate;
        if (userId != null && userId != "" && token != null && token != "") {
            LOGGER.debug("userId token got!!!");
            List<Games> games = gameService.getTomorrowUserGameInfoByUid(userId);
            if (games != null && games.size() > 0) {
                Games games1 = games.get(0);
                String partyTime = games1.getPartyTime();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(partyTime.substring(0, 4)).append("/").append(partyTime.substring(4, 6)).append("/").append(partyTime.substring(6));
                oDate = stringBuilder.toString();
                LOGGER.info("date========>" + oDate);
                mainMap.put("date", oDate);
                if (games1.getReChecktime() == null) {
                    joinStatus = USER_JOIN_STATUS.STATUS_JOINED_NOTCHECK.getStatusValue();
                } else {
                    joinStatus = USER_JOIN_STATUS.STATUS_JOINED_CHECKED.getStatusValue();
                }
            }
        }
        mainMap.put("tags", tags);
        mainMap.put("total_count", totayGameCount);
        mainMap.put("content_json", contentMap);
        mainMap.put("join_amount", joinAmount);
        mainMap.put("background_img", project.getBackgroundImg());
        mainMap.put("project_name", project.getProjectName());
        mainMap.put("success_count", rankNumbers.get("suc_cnt"));
        mainMap.put("fail_count", rankNumbers.get("fal_cnt"));
        mainMap.put("user_join_status", joinStatus);
        //partyTime
//        String current_time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        mainMap.put("start_time", begin_time);
        mainMap.put("end_time", end_time);
        mainMap.put("current_time", getTimeFormat(new Date(), "yyyy/MM/dd HH:mm:ss"));

        result.put("data", mainMap);
        LOGGER.debug("{},获取项目详情", format);
        return result;
    }


    private String getTimeFormat(Date date, String partten) {
        DateFormat dateFormat = new SimpleDateFormat(partten);
        return dateFormat.format(date);

    }
}
