package com.twq.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.twq.dao.model.UserInfo;
import com.twq.service.base.*;
import com.twq.service.business.WxService;
import com.twq.util.Constants;
import com.twq.util.DecryptUtil;
import com.twq.util.Nodes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/wx")
public class WxController {

    private static Logger LOGGER = LoggerFactory.getLogger(WxController.class);
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


    @Value("${join_amount}")
    private Integer joinAmount;
    @Value("${wx_login_url}")
    private String wx_login_url;
    @Autowired
    private WxService wxService;

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object callingApi(@RequestParam("code") String code) {
        Map<String, Object> result = new HashMap<>();
        LOGGER.info("进入登录:code==" + code);
        Object login = wxService.login(code, wx_login_url);
        result.put(Nodes.DATA, login);
        //TODO 用户信息入库
        result.put(Nodes.retCode, Constants.RET_CODE_SUCCESSFUL);
        return result;
    }


    @RequestMapping(path = "/decryUserData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object decryUserData(@RequestParam("encrypted") String encrypted,
                                @RequestParam("iv") String iv,
                                @RequestParam("session_key") String session_key,
                                @RequestParam("type") String type) {

        Map<String, Object> result = new HashMap<>();
        DecryptUtil decryptUtil = new DecryptUtil();
        byte[] decrypt = decryptUtil.decrypt(encrypted, session_key, iv);
        String str = new String(decrypt);
        LOGGER.info("解密str:" + str);
        //1.得到基础信息
        JSONObject jsonObject = JSON.parseObject(str);
        String openId = (String) jsonObject.get("openId");
        String nickName = (String) jsonObject.get("nickName");
        String avatarUrl = (String) jsonObject.get("avatarUrl");
        //更新用户信息
        usersService.registOrUpdate(openId, null, avatarUrl, nickName);
        //2.得到用户业务数据
        Map<String, Object> userGameInfo = usersService.getUserGameInfo(openId);
//        BigDecimal userBalanceInfo = usersService.getUserBalanceInfo(openId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        result.put("user", userGameInfo);
//        result.put("balance", userBalanceInfo);

//        usersService.registOrUpdate(openId,null,avatarUrl,nickName);
//        return null;
//        LOGGER.info("进入登录:code==" + code);
//        Object login = wxService.login(code, wx_login_url);
//        result.put(Nodes.DATA, login);
//        result.put(Nodes.retCode, Constants.RET_CODE_SUCCESSFUL);
        LOGGER.info("json----->" + JSON.toJSONString(map));
        return map;
    }


    @RequestMapping(path = "/getBalance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
        BigDecimal userBalanceInfo = usersService.getUserBalanceInfo(userInfo.getId());

        result.put("balance", userBalanceInfo);
        return result;

    }

    public static void main(String[] args) {
        String encrypted = "zbTEZ6/iqHU6kQzEOLJ48spis3yxy51xirtzLvY4BeYymZVGg9m1TNSZGt6sEhlQ/wKhne9KNXRO9MPmljRPlFdxAl5dLhZncvoG4q6gEbdztu7ePNvhvCmZWPEC+HZ0+yai+RPKr6/LjMpFwtzKNpeRfyl8tO4f0/WjEZp4ZAVZx5lseVVmViW0hGQ1Ra9ko9BR2kVOd6sPvO00RjnivA/na6av30hUBCppL77xWx5RP8m+zpH772iBwRlJDyRbk+1iAIXPiirymbinol+kLUaY1+kVcHadkl6nozl7ZMK6omNw3D05JvDgUd1h1dxPh42V0Jzms1P8ZOmILn+RvD+GLM18DXYtTEd+7RiRChx9ygPZfy6Oa4kLzcde8tfzRJu36Wd4HN3vpNY69FLDwuGyF9M1XC4/6ut1fVtm3TCh7kxkEo7RnAqR0dGfrpBIzXmvakC7wkw7czMtgf23qSbPqhEo3fReUjTYX8F0SWo=";
        String session_key = "IOW1ppK5/nrA1oRBLLZWqg==";
        String iv = "EWaybC4ks74iFu7VCpiXvQ==";
        byte[] decrypt = new DecryptUtil().decrypt(encrypted, session_key, iv);
        System.out.println(decrypt);
    }

    /**
     * user_id: o.id,
     * token: o.token,
     * openid: o.openid
     */
    @RequestMapping(path = "/signature", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> signatureMove(@RequestParam("user_id") String user_id, @RequestParam("token") String token, @RequestParam("openid") String openid) {
        Map<String, Object> result = new HashMap<>();

        //1.生成后台订单
        //2.调用接口，获取统一下单接口返回的 prepay_id 参数值


        if (StringUtils.isBlank(user_id) || StringUtils.isBlank(token) || StringUtils.isBlank(openid)) {
            return null;
        } else {
            result = usersService.sigatureUser(user_id, token, openid);
        }
        return result;


    }


}
