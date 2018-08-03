package com.twq.service.business;

import com.twq.dao.model.Games;
import com.twq.service.base.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 和用户账户和金额有关的service
 */
@Service
public class UserAccountService {


    @Autowired
    private GameService gameService;


    public Map<String, Object> userMoneyLog(String userId) {
        Map<String, Object> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesDay = calendar.getTime();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String yesterDayParty = simpleDateFormat.format(yesDay);
        String today = simpleDateFormat.format(date);
        List<Games> userGameInfoByUid = gameService.getUserGameInfoByUidAndParyTime(userId, yesterDayParty);
        BigDecimal yesterDay = new BigDecimal(0);//昨日瓜分
        BigDecimal contract = new BigDecimal(0);//累计契约
        BigDecimal haverest = new BigDecimal(0);//累计瓜分
        int size = 0;//挑战次数\
        List<Map> list = new ArrayList<>();
        if (userGameInfoByUid != null && userGameInfoByUid.size() > 0) {
            yesterDay.add(userGameInfoByUid.get(0).getReCheckmoney());
        }

        List<Games> userGameInfoByUid1 = gameService.getUserGameInfoByUid(userId);


        if (userGameInfoByUid1 != null && userGameInfoByUid1.size() > 0) {
            Stream<Games> sorted = userGameInfoByUid1.stream().filter(games -> !games.getPartyTime().equals(today)).sorted(new Comparator<Games>() {
                @Override
                public int compare(Games o1, Games o2) {
                    return o1.getCheckInTime().compareTo(o2.getCheckInTime());
                }
            });
            List<Games> collect = sorted.collect(Collectors.toList());
            for (Games gm : collect
                    ) {
                contract.add(gm.getCheckMoney());
                haverest.add(gm.getReCheckmoney() == null ? new BigDecimal(0) : gm.getReCheckmoney());
                Map<String, Object> item = new HashMap<>();
                item.put("day", gm.getPartyTime());
                item.put("time", gm.getCheckInTime());
                item.put("type_txt", gm.getIsRecheck() == 0 ? "挑战失败" : "挑战成功");
                item.put("project_name", "万强打卡");
                item.put("amount", gm.getCheckMoney().divide(new BigDecimal(100)));
                item.put("created_at", gm.getCheckInTime().toLocaleString());
                list.add(item);
            }
            size = userGameInfoByUid1.size();
        }

        result.put("contract", contract);
        result.put("harvest", haverest);
        result.put("join_count", size);
        result.put("yesterday", yesterDay);
        result.put("list", list);
        return result;
    }

}
