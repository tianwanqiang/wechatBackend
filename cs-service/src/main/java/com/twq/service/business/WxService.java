package com.twq.service.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.twq.service.base.ConfigService;
import com.twq.service.base.UsersService;
import com.twq.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class WxService {

    public String SignType_MD5 = "md5";

    @Autowired
    private ConfigService configService;
    @Autowired
    private UsersService usersService;

    public Object login(String code, String loginUrl) {
        Properties platParameters = configService.getPlatParameters();
        String appKey = (String) platParameters.get(Nodes.APPKEY);
        String appSec = (String) platParameters.get(Nodes.APPSEC);
        String replace = loginUrl.replace("APPID", appKey).replace("SECRET", appSec).replace("JSCODE", code);
        String output = null;
        //TODO 用户信息入库，返回业务令牌
        JSONObject get = WeixinUtil.httpRequest(replace, "GET", output);
        String session_key = (String) get.get("session_key");
        String openId = (String) get.get("openid");
        usersService.registOrUpdate(openId, session_key, null, null);
        CSLog.info("用户信息：" + JSON.toJSONString(get));
        return get;
    }


    public Object prePay(String openId, String goodsDetail, String productType, String goodsBody, Integer totalFee, TradeTypeEnum tradeTypeEnum) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Properties platParameters = configService.getPlatParameters();
        String appKey = (String) platParameters.get(Nodes.APPKEY);
        String mchId = (String) platParameters.get(Nodes.MCH_ID);
        String mchSec = (String) platParameters.get(Nodes.MCH_SEC);


        Map<String, String> parameters = new HashMap<>();
        parameters.put("appid", appKey);
        parameters.put("mch_id", mchId);
        String nonce_str = StringUtil.getRandomChar(32);//随机字符串
        parameters.put("nonce_str", nonce_str);
        String sign_type = SignType_MD5;
        parameters.put("sign_type", sign_type);
        parameters.put("body", goodsBody);
        parameters.put("detail", goodsDetail);
        parameters.put("attach", mchId);
        String out_trade_no = UUID.randomUUID().toString();
        parameters.put("out_trade_no", out_trade_no);
        parameters.put("fee_type", FeeTypeEnum.FEE_TYPE_CNY.getCode());
        parameters.put("total_fee", totalFee.toString());
        Date timeStart = new Date();
        parameters.put("time_start", DateUtil.getFormatDateWechatPay(timeStart));
        String notify_url = (String) platParameters.get(Nodes.ORDER_NOTIFY_URL);
        parameters.put("notify_url", notify_url);
        parameters.put("trade_type", tradeTypeEnum.getCode());
        parameters.put("product_id", productType);
        parameters.put("openid", openId);
        String[] objects = (String[]) parameters.keySet().toArray();
        //.第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
        Arrays.sort(objects);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i]).append("=").append(parameters.get(objects[i])).append("&");
        }
        String tempStr = sb.toString();
        //第二步：拼接商户平台API密钥：
        String tempSignStr = tempStr + "key=" + mchSec;
        String sign = new DecryptUtil().EncoderByMd5(tempSignStr).toUpperCase();
        parameters.put("sign", sign);
        parameters.put("appid", appKey);
        String outputStr = XMLUtil.xmlFormat(parameters, true);
        String preRequest = (String) platParameters.get(Nodes.PRE_REQUEST_URL);
        JSONObject jsonObject = WeixinUtil.httpRequest(preRequest, RequestMethod.POST.name(), outputStr);
        return jsonObject;

    }





}
