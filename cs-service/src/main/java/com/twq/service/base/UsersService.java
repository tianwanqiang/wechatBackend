package com.twq.service.base;

import com.twq.dao.mapper.GamesMapper;
import com.twq.dao.mapper.UserInfoMapper;
import com.twq.dao.mapper.UserWalletMapper;
import com.twq.dao.model.*;
import com.twq.util.Constants;
import com.twq.util.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UsersService {

    Logger logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private GamesMapper gamesMapper;

    @Autowired
    private UserWalletMapper userWalletMapper;

//    public List<UserInfo> getUsersForRank() {
//        UserInfoExample example = new UserInfoExample();
//        UserInfoExample.Criteria where = example.createCriteria();
//        where.andMoneyIsNotNull().andIsJoinEqualTo(1);
//        example.setOrderByClause("money desc");
//        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
//        return userInfos;
//    }

    /**
     * 注册或更新用户信息
     *
     * @param openId
     * @param sessionKey
     * @param avaUrl
     * @param nick
     * @return
     */
    public int registOrUpdate(String openId, String sessionKey, String avaUrl, String nick) {
        int result = -1;
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andOpenIdEqualTo(openId);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (userInfos != null && userInfos.size() > 0) {
            UserInfo userInfo = userInfos.get(0);
            userInfo.setSessionKey(sessionKey);
            userInfo.setAvaUrl(avaUrl);
            userInfo.setToken(UUID.randomUUID().toString());
            userInfo.setNickName(nick);
            //更新用户 openId sessionkey等
            example = new UserInfoExample();
            example.createCriteria().andOpenIdEqualTo(userInfo.getOpenId());
            result = userInfoMapper.updateByExampleSelective(userInfo, example);
        } else {
            UserInfo userInfo = new UserInfo();
            //TODO IDworker 在机器启动时初始化好存入容器才对
            SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 1);
            long l = snowflakeIdWorker.nextId();
            userInfo.setId(String.valueOf(l));
            userInfo.setNickName(nick);
            userInfo.setAvaUrl(avaUrl);
            userInfo.setSessionKey(sessionKey);
            userInfo.setOpenId(openId);
            userInfo.setToken(UUID.randomUUID().toString());
            userInfo.setCreatedAt(new Date());
            userInfo.setIsJoin(Constants.IS_JOINED_FALSE);
            result = userInfoMapper.insertSelective(userInfo);
        }
        return result;

    }


    public Map<String, Object> getUserGameInfo(String openId) {
        Map<String, Object> result = new HashMap<>();
        long cnt = 0L;
        BigDecimal allCheck = new BigDecimal(0);
        BigDecimal allCheckGet = new BigDecimal(0);
        UserInfo userInfoByOpenId = getUserInfoByOpenId(openId);
        String id = userInfoByOpenId.getId();
        //1.挑战次数
        GamesExample gamesExample = new GamesExample();
        gamesExample.createCriteria().andUserIdEqualTo(id);
        List<Games> games = gamesMapper.selectByExample(gamesExample);
        if (games != null && games.size() > 0) {
            cnt = games.size();
            for (int i = 0; i < games.size(); i++) {
                Games games1 = games.get(i);
                BigDecimal checkMoney = games1.getCheckMoney();
                BigDecimal reCheckmoney = games1.getReCheckmoney();
                if (reCheckmoney == null) {
                    reCheckmoney = new BigDecimal(0);
                }
                allCheck.add(checkMoney);
                allCheckGet.add(reCheckmoney);
            }
        }
        result.put("join_count", cnt);
        result.put("contract", allCheck);
        result.put("harvest", allCheckGet);
        result.put("token", userInfoByOpenId.getToken());
        result.put("head_img", userInfoByOpenId.getAvaUrl());
        result.put("name", userInfoByOpenId.getNickName());
        result.put("id", userInfoByOpenId.getId());
        result.put("openid", userInfoByOpenId.getOpenId());

        return result;

    }

    public UserInfo getUserInfoByOpenId(String openId) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andOpenIdEqualTo(openId);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (userInfos != null && userInfos.size() > 0) {
            return userInfos.get(0);
        }
        return null;
    }

    public BigDecimal getUserBalanceInfo(String userId) {
        BigDecimal balance = new BigDecimal(0);
        UserWalletExample example = new UserWalletExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserWallet> userWallets = userWalletMapper.selectByExample(example);
        if (userWallets != null && userWallets.size() > 0) {
            balance = userWallets.get(0).getUserWallet();
        }
        return balance;

    }


    public UserInfo getUserInfoById(String userId) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andIdEqualTo(userId);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos == null ? null : userInfos.get(0) == null ? null : userInfos.get(0);
    }

    public Map<String, Object> sigatureUser(String user_id, String token, String openid) {
        Calendar instance = Calendar.getInstance();
        Map<String, Object> result = new HashMap<>();
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andIdEqualTo(user_id);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (userInfos != null && userInfos.size() > 0) {
            UserInfo userInfo = userInfos.get(0);
            String token1 = userInfo.getToken();
            String openId = userInfo.getOpenId();
            if (token1 != null && token.equals(token1)) {
                result.put("timeStamp", System.nanoTime());
                result.put("nonceStr", UUID.randomUUID().toString().substring(0, 23));
                result.put("package", "package");
                result.put("signType", "MD5");
                result.put("success", "1");
                return result;
            } else {
                result.put("msg", "签名校验失败");
                result.put("success", "0");
                logger.error("{}=====>签名校验失败,token错误", instance.getTime().toLocaleString());
                return result;
            }

        } else {
            result.put("msg", "请求参数错误");
            result.put("success", "0");
            logger.error("{}=====>请求参数错误", instance.getTime().toLocaleString());
            return result;
        }


    }
}
