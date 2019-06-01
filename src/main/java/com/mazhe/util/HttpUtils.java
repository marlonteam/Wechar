package com.mazhe.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 网络数据传输工具类
 *
 * @author: 海涵
 * @create: 2019-04-11 18:59
 **/
@Slf4j
public class HttpUtils {
    private static final Gson gson = new Gson();

    public static final String HTTP = "http";
    public static final String HTTPS = "https";

    public static final Integer STATUS_CODE_443 = 443;
    public static final Integer STATUS_CODE_200 = 200;

    /**
     * POST请求数据传输
     * @param httpRequestUrl
     * @param jsonData
     * @return
     */
    public static String postHttpsRequest(String httpRequestUrl,  String jsonData) {
        //调用
        Protocol myHttps = new Protocol(HTTPS, new SSLSocketFactory(), STATUS_CODE_443);
        Protocol.registerProtocol(HTTPS, myHttps);
        log.info("发送url:[{}], 发送报文:[{}]", httpRequestUrl, jsonData);
        long beginTime = System.currentTimeMillis();
        Long costTime = null;
        PostMethod postMethod = new PostMethod(httpRequestUrl);
        try {
            HttpClient client = new HttpClient();
            //超时设置
            client.getHttpConnectionManager().getParams().setConnectionTimeout(180000);
            client.getHttpConnectionManager().getParams().setSoTimeout(180000);
            //添加银联代理控制开关
            //client.getHostConfiguration().setProxy(paymentConfig.getChannelUPProxyHost(), paymentConfig.getChannelUPProxyPort());
            RequestEntity entity = new StringRequestEntity(jsonData, "text/xml", "UTF-8");
            postMethod.setRequestEntity(entity);
            int status = client.executeMethod(postMethod);
            costTime = System.currentTimeMillis() - beginTime;
            if (STATUS_CODE_200 == status) {
                InputStream is = postMethod.getResponseBodyAsStream();

                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String resMsg = new String(sb);
                log.info("响应报文:[{}]，网络耗时[{}]ms", resMsg, costTime);
                return resMsg;

            } else {
                int statusCode = postMethod.getStatusCode();
                String statusText = postMethod.getStatusText();
                log.info("交易请求异常:[{}], [{}], 网络耗时[{}]ms", statusCode, statusText, costTime);
            }
        } catch(Exception e) {
            log.info("交易请求异常信息:[{}], 网络耗时[{}]ms", e, costTime);
        } finally {
            postMethod.releaseConnection();
        }

        return null;
    }


    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = StringUtils.EMPTY;
        BufferedReader in = null;
        log.info("url:[{}]", url);
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info("key[{}]------[{}]", key, map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常![{}]", e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
