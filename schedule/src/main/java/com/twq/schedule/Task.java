package com.twq.schedule;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.twq.dao.mapper.*;
import com.twq.dao.model.*;
import com.twq.util.HongBaoAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class Task {

    public static final Byte UNCHECK = Byte.valueOf("0");
    public static final Byte CHECKED = Byte.valueOf("1");
    public static final BigDecimal RATE = new BigDecimal(0.7);
    public static final String CONSTANT_ID = "3";
    public static final String LUCKY_ID = "2";
    public static final String EARLY_ID = "1";
    public static final String[] times = {"6:35", "7:30", "8:00", "7:34"};

    private Logger logger = LoggerFactory.getLogger(Task.class);

    @Autowired
    private GamesMapper gamesMapper;


    @Autowired
    private UserWalletMapper userWalletMapper;

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private CustomMapper customMapper;


    // 0 30 7 ? * *
    //0 30 23 * * ? *
    @Scheduled(cron = "0 50 23 * * ? ")
    public void dailyAccountRunning() {

        //每天11：50后
        //1.如果当天所有人都没有打卡成功，则将打卡金退还用户钱包
        String todayPartyTime = getTodayPartyTime();
        GamesExample gamesExample = new GamesExample();
        gamesExample.createCriteria().andPartyTimeEqualTo(todayPartyTime).andIsRecheckEqualTo(CHECKED);
        long length = gamesMapper.countByExample(gamesExample);
        List<Games> winners = new ArrayList<>();
        if (length == 0) {
            clearExample(gamesExample);
            gamesExample.createCriteria().andPartyTimeEqualTo(todayPartyTime);
            List<Games> allGames = gamesMapper.selectByExample(gamesExample);
            for (Games games : allGames
                    ) {
                setUserWallet(games, games.getCheckMoney());
            }
            return;
        } else {
            winners = gamesMapper.selectByExample(gamesExample);
        }

        //2.否则，将打卡成功用户瓜分打卡失败用户佣金
        //得到今天未打卡的游戏数据
        clearExample(gamesExample);
        GamesExample.Criteria where = gamesExample.createCriteria();
        where.andPartyTimeEqualTo(todayPartyTime).andIsRecheckEqualTo(UNCHECK);
        List<Games> failers = gamesMapper.selectByExample(gamesExample);
        //得到昨天瓜分奖金
        BigDecimal uncheckMoney = new BigDecimal(0);
        for (Games g :
                failers) {
            BigDecimal checkMoney = g.getCheckMoney();
            uncheckMoney.add(checkMoney);
        }

        //得到今天打卡成功数据
        int size = 0;
        //成功打卡人数
        size = (winners != null && winners.size() > 0) ? winners.size() : size;
        //实际瓜分总金额
        BigDecimal realMoney = uncheckMoney.multiply(RATE);
        //人均奖金
        if (size <= 0) {
            logger.debug("昨日无人参与");
            return;
        }

        HongBaoAlgorithm h = new HongBaoAlgorithm();
        final HongBaoAlgorithm.HongBao hb = h.new HongBao(realMoney.doubleValue(), size);

        for (Games winner : winners
                ) {
            //每人获取金额
            double perAssign = hb.assignHongBao();
            BigDecimal perGot = new BigDecimal(perAssign);
            BigDecimal earn = perGot;//TODO 随机处理
            winner.setReCheckmoney(earn);
            setUserWallet(winner, earn);

            GamesExample example1 = new GamesExample();
            example1.createCriteria().andUserIdEqualTo(winner.getUserId());
            //更新用户打卡记录
            gamesMapper.updateByExampleSelective(winner, example1);
        }
    }

    /**
     * 每天统计用户标签
     */
    @Scheduled(cron = "0 */1 * * * ? ")
    public void dailyTagRunning() {
        GamesExample gamesExample = new GamesExample();
        clearExample(setMorningStar(gamesExample));
        setKeepStar();
        setLuckStar();
    }

    private void setLuckStar() {
        Map luckUser = customMapper.getLuckUser();
        String user_id = (String) luckUser.get("user_id");
        UserInfoExample userInfoExample1 = new UserInfoExample();
        userInfoExample1.createCriteria().andIdEqualTo(user_id);
        List<UserInfo> userInfos1 = userInfoMapper.selectByExample(userInfoExample1);
        UserInfo userInfo1 = userInfos1.get(0);

        Tags luckTag = new Tags();
        luckTag.setId(LUCKY_ID);
        luckTag.setTagData(times[getRandomInt(times.length)]);
        luckTag.setUserName(userInfo1.getNickName());
        luckTag.setHeadImg(userInfo1.getAvaUrl());
        tagsMapper.updateByPrimaryKeySelective(luckTag);//幸运之星
    }

    private void setKeepStar() {
        List<Map<String, String>> mostUser = customMapper.getMostUser();

        mostUser.sort(new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return o1.get("cnt").compareTo(o2.get("cnt"));
            }
        });
        Map<String, String> userMap = mostUser.get(0);
        String firstUid = userMap.get("user_id");
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andIdEqualTo(firstUid);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
        Tags mostTag = new Tags();
        mostTag.setId(CONSTANT_ID);
        UserInfo userInfo = userInfos.get(0);
        mostTag.setUserName(userInfo.getNickName());
        mostTag.setHeadImg(userInfo.getAvaUrl());
        mostTag.setTagData(times[getRandomInt(times.length)]);
        tagsMapper.updateByPrimaryKeySelective(mostTag);//坚持之星
    }

    private GamesExample setMorningStar(GamesExample gamesExample) {
        PageHelper.startPage(0, 200, "re_checkTime");
        gamesExample.createCriteria().andIsRecheckEqualTo(CHECKED);
        List<Games> games = gamesMapper.selectByExample(gamesExample);
        logger.debug("games=====>>>" + JSON.toJSONString(games));
        if (games != null && games.size() > 0) {
            Games games1 = games.get(0);//早起之星
            String userId = games1.getUserId();
            UserInfoExample userInfoExample = new UserInfoExample();
            userInfoExample.createCriteria().andIdEqualTo(userId);
            UserInfo user1 = userInfoMapper.selectByExample(userInfoExample).get(0);

            Tags early = new Tags();
            early.setId(EARLY_ID);
            early.setHeadImg(user1.getAvaUrl());
            String tagData = games1.getReChecktime().getHours() + ":" + games1.getReChecktime().getMinutes();
            early.setTagData(tagData);
            early.setUserName(user1.getNickName());
            tagsMapper.updateByPrimaryKeySelective(early);//早起之星

        }
        return gamesExample;
    }

    /**
     * 设置用户钱包
     */
    private void setUserWallet(Games winner, BigDecimal earn) {
        UserWalletExample userWalletExample = new UserWalletExample();
        userWalletExample.createCriteria().andUserIdEqualTo(winner.getUserId());
        List<UserWallet> userWallets = userWalletMapper.selectByExample(userWalletExample);
        if (userWallets != null && userWallets.size() > 0) {
            UserWallet userWallet = userWallets.get(0);
            BigDecimal money = userWallet.getUserWallet();
            userWallet.setUserWallet(money.add(earn));
            //2.结算打卡成功用户的打卡金额，存入用户钱包
            userWalletMapper.updateByExample(userWallet, userWalletExample);

        } else {
            UserWallet wallet = new UserWallet();
            wallet.setId(new UUIDEditor().getAsText());
            wallet.setUserWallet(earn);
            wallet.setUserId(winner.getUserId());
            //2.结算打卡成功用户的打卡金额，存入用户钱包
            userWalletMapper.insertSelective(wallet);
        }
    }


    private void clearExample(GamesExample gamesExample) {
        gamesExample.clear();
    }


    /**
     * 得到昨天的游戏时间
     *
     * @return
     */
    private String getTodayPartyTime() {
        Calendar yesterDay = Calendar.getInstance();
        Date time = yesterDay.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(time);
    }

    public int getRandomInt(int length) {
        return new Random().nextInt(length);
    }
}
