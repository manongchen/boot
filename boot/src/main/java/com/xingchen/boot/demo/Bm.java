package com.xingchen.boot.demo;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Bm
 * @Description TODO()
 * @Author xingchen
 * @Date 2019-08-22 17:44
 * @Version 1.0.0
 */
public class Bm {
//三个配置信息
    /**
     * appkey
     */
    String appKey;
    /**
     * 密钥
     */
    String secretKey;
    /**
     * API地址
     */
    String apiHost;
    /**
     * 编码
     */
    public static final String CHAR_SET = "UTF-8";

    /**
     * 执行服务调用
     *
     * @param uri  URI地址
     * @param data 数据
     * @return 响应字符串
     * @throws Exception
     */
    public String invoke(String uri, Object data) throws Exception {
        StringBuilder result = new StringBuilder();
        String json = JSON.toJSONString(data);
        long requestAt = System.currentTimeMillis();
        String sign = signature("POST", requestAt, uri, json);
        URL realUrl = new URL(apiHost + uri);
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
        connection.setRequestMethod("POST");
        //添加头信息
        connection.addRequestProperty("content-type", "application/x-www-form-urlencoded");
        connection.addRequestProperty("DATE", String.valueOf(requestAt));
        connection.addRequestProperty("APP-KEY", appKey);
        connection.addRequestProperty("X-SIGNATURE", sign);

        connection.setDoOutput(true);
        connection.setDoInput(true);
        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            out.print("data=" + URLEncoder.encode(json, CHAR_SET));
            out.flush();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                    result.append("\r\n");
                }
                return result.toString();
            }
        }

    }

    /**
     * 签名
     *
     * @param method    请求方法
     * @param requestAt 请求时间
     * @param uri       请求的uri地址
     * @param json      data的内容
     * @return
     * @throws Exception
     */
    private String signature(String method, long requestAt, String uri, String json) {
        String signatureUrl = uri;
        if (signatureUrl.contains("?")) {
            signatureUrl = uri.substring(0, uri.indexOf("?"));
        }
        StringBuilder data = new StringBuilder(method)
                .append("&")
                .append(requestAt)
                .append("&")
                .append(signatureUrl)
                .append("&")
                .append(json)
                .append("&")
                .append(this.appKey)
                .append("&")
                .append(this.secretKey);
        log("验签字符串=" + data);
        String signature = md5(data.toString());
        log("MD5签名=" + signature);
        return signature;
    }

    /**
     * md5加密
     *
     * @param text
     * @return
     */
    private String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(text.getBytes(CHAR_SET));
            BigInteger bigInteger = new BigInteger(1, md.digest());
            StringBuilder hashText = new StringBuilder(bigInteger.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log("签名出错=" + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 日志打印
     *
     * @param s
     */
    static void log(String s) {
        System.out.println(s);
    }


    public static void main(String[] args) throws Exception {

        Bm client = new Bm();
        client.appKey = ("test");
        client.apiHost = ("http://39.104.188.230:9091");
        client.secretKey = ("dfeb3a87bf69a0a0b162660476c6cbc0254f1038");

        {
            //订单拉取接口
            Map param = new HashMap<>();
            param.put("subjectCode", "10018");
            param.put("whCode", "SYW0027");
            String resp = client.invoke("/api/v2/platform/pullOrder", param);
            log("响应=" + resp);
        }


//        {
//            //订单确认接口
//            List> param2 = new ArrayList<>();
//            Map o1 = new HashMap<>();
//            o1.put("orderNo", "20180815ALG26769");
//            param2.add(o1);
//            Map o2 = new HashMap<>();
//            o2.put("orderNo", "20180815LQO38124");
//            param2.add(o2);
//            String resp2 = client.invoke("/api/v2/platform/confirmOrder", param2);
//            log("响应=" + resp2);
//        }


//        {
//            //订单物流信息传输接口
//            List> param3 = new ArrayList<>();
//            Map m1 = new HashMap<>();
//            m1.put("orderNo", "20180815LQO38124");
//            m1.put("thirdPartyGoodsCode", "123");
//            m1.put("waybill", "");
//            m1.put("logisticsCompany", "3");
//            param3.add(m1);
//            Map m2 = new HashMap<>();
//            m2.put("orderNo", "20180815ALG26769");
//            m2.put("thirdPartyGoodsCode", "123");
//            m2.put("waybill", "test2");
//            m2.put("logisticsCompany", "3");
//            param3.add(m2);
//            String resp3 = client.invoke("/api/v2/platform/logistics", param3);
//            log("响应=" + resp3);
//        }


//        {
//            //缺海关支付单通知接口
//            List> param4 = new ArrayList<>();
//            Map o41 = new HashMap<>();
//            o41.put("orderNo", "20180815ALG26769");
//            param4.add(o41);
//            Map o42 = new HashMap<>();
//            o42.put("orderNo", "20180815LQO38124");
//            param4.add(o42);
//            String resp4 = client.invoke("/api/v2/platform/lackPayBill", param4);
//            log("响应=" + resp4);
//        }


    }
}
