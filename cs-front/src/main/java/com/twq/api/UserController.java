package com.twq.api;

import com.twq.dao.model.UserInfo;
import com.twq.service.base.ContentService;
import com.twq.service.base.GameService;
import com.twq.service.base.IndexService;
import com.twq.service.base.UsersService;
import com.twq.service.business.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    IndexService indexService;
    @Autowired
    ContentService contentService;

    @Autowired
    UsersService usersService;

    @Autowired
    GameService gameService;

    @Autowired
    UserAccountService userAccountService;

    @RequestMapping(path = "/moneyLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> decryUserData(@RequestParam("user_id") String userId,
                                             @RequestParam("token") String token) throws Exception {
        Map<String, Object> result = new HashMap<>();
        UserInfo userInfo = null;
        if (token == null || userId == null) {
            throw new Exception("USER OR TOKEN IS NULL");
        } else {
            userInfo = usersService.getUserInfoById(userId);
            if (userInfo == null) {
                throw new Exception("USER NOT EXISTS");
            } else if (!userInfo.getToken().equals(token)) {
                throw new Exception("TOKEN IS NOT CORRECT");
            }
        }
        Map<String, Object> stringObjectMap = userAccountService.userMoneyLog(userId);
        result.put("data", stringObjectMap);
        return result;

    }


}
