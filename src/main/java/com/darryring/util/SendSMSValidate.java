/*
package com.darryring.util;

import com.alibaba.fastjson.JSONObject;

*/
/**
 * 短信验证
 *//*


public class SendSMSValidate {
    public static boolean sendSms( String mobile,String param) {
        String sid="70fd6f32589814eb001ef7482fb4d731";
        String token="b3d41209e76f0f4859164b1a9482c99d";
        String appid="8f6ab95a991d4db88255d19e34382e47";
        String templateid="418112"; // 模板id
        String uid="";

        boolean result = false;

        try {
            String url = "https://open.ucpaas.com/ol/sms/sendsms";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sid", sid);
            jsonObject.put("token", token);
            jsonObject.put("appid", appid);
            jsonObject.put("templateid", templateid);
            jsonObject.put("param", param);
            jsonObject.put("mobile", mobile);
            jsonObject.put("uid", uid);

            String body = jsonObject.toJSONString();

            System.out.println("body = " + body);

            result = HttpClientUtil.postJson(url, body, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
*/
