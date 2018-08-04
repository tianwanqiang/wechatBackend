package com.twq.service.base;

import com.twq.dao.mapper.GamesMapper;
import com.twq.dao.mapper.UserInfoMapper;
import com.twq.dao.model.Games;
import com.twq.dao.model.GamesExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GameService {

    public static final Byte UNCHECKED = Byte.valueOf("0");
    public static final Byte CHECKED = Byte.valueOf("1");
    @Autowired
    private GamesMapper gamesMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 获取昨日活动数据
     */
    public Map<String, Object> getRankNumbers() {
        Calendar nowCal = Calendar.getInstance();
//        nowCal.add(Calendar.DATE, -1);
        Date time = nowCal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        //昨天
        String partyTime = simpleDateFormat.format(time);

        GamesExample example = new GamesExample();
        GamesExample.Criteria where = example.createCriteria();
        //魔鬼数字，后期修改为常量或枚举
        where.andIsRecheckEqualTo(UNCHECKED).andPartyTimeEqualTo(partyTime);
        long fail_count = gamesMapper.countByExample(example);
        example.clear();

        example.createCriteria().andIsRecheckEqualTo(CHECKED).andPartyTimeEqualTo(partyTime);
        long succ_count = gamesMapper.countByExample(example);
        Map<String, Object> result = new HashMap<>();
        result.put("suc_cnt", succ_count);
        result.put("fal_cnt", fail_count);
        return result;
    }

//    /**
//     * 获取当日参与数据
//     *
//     * @return
//     */
//    public long getTotayGameCount() {
//        GamesExample example = new GamesExample();
//        GamesExample.Criteria where = example.createCriteria();
//        //partyTIme应该精确到天即可
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        String partyTime = simpleDateFormat.format(new Date());
//        where.andPartyTimeEqualTo(partyTime);
//        return gamesMapper.countByExample(example);
//    }

    /**
     * 获取明日参与数据
     * 所有参与者打卡后游戏时间均为明天
     *
     * @return
     */
    public long getTomorrowGameCount() {
        GamesExample example = new GamesExample();
        GamesExample.Criteria where = example.createCriteria();
        //partyTIme应该精确到天即可
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        String partyTime = simpleDateFormat.format(tomorrow.getTime());
        where.andPartyTimeEqualTo(partyTime);
        return gamesMapper.countByExample(example);
    }

    /**
     * 根据用户id和游戏时间获取挑战记录
     *
     * @param userId
     * @param partyTime
     * @return
     */
    public List<Games> getUserGameInfoByUidAndParyTime(String userId, String partyTime) {
        GamesExample example = new GamesExample();
        GamesExample.Criteria where = example.createCriteria();
        where.andUserIdEqualTo(userId).andPartyTimeEqualTo(partyTime);
        List<Games> games = gamesMapper.selectByExample(example);
        return games;
    }

    public List<Games> getUserGameInfoByUid(String userId) {
        GamesExample example = new GamesExample();
        GamesExample.Criteria where = example.createCriteria();
        where.andUserIdEqualTo(userId);
        List<Games> games = gamesMapper.selectByExample(example);
        return games;
    }


    /**
     * 根据用户id和游戏时间获取挑战记录
     *
     * @param userId
     */
    public List<Games> getTomorrowUserGameInfoByUid(String userId) {

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String format = df.format(instance.getTime());
        return getUserGameInfoByUidAndParyTime(userId, format);
    }
}
