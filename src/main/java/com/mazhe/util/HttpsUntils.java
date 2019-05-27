package com.mazhe.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.Proxy.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * java https 代理设置，忽略证书ssl 工具类
 */

@Slf4j
public class HttpsUntils  {
	
	public static void main(String[] args) {
		System.out.println("java https test");
	}
	public static String HttpsProxy(String url, String param, String proxy, int port) {
        HttpsURLConnection httpsConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        Proxy proxy1=null;
        try {
            URL urlClient = new URL(url);
            System.out.println("=========URL========:" + urlClient);

                SSLContext sc = SSLContext.getInstance("SSL");
                // 指定信任https
                sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
                //创建代理虽然是https也是Type.HTTP
                //设置代理
                if(null!=proxy&&!proxy.equals("")&&port>0) {
                	proxy1=new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
                	httpsConn = (HttpsURLConnection) urlClient.openConnection(proxy1);
                }
                else {
                	 httpsConn = (HttpsURLConnection) urlClient.openConnection();
                }
                
                // 设置连接主机服务器超时时间：30000毫秒
                httpsConn.setConnectTimeout(30000);
                // 设置读取主机服务器返回数据超时时间：60000毫秒
                httpsConn.setReadTimeout(60000);
                
                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                 // 设置通用的请求属性
                httpsConn.setRequestProperty("accept", "*/*");
                httpsConn.setRequestProperty("connection", "Keep-Alive");
                httpsConn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                httpsConn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 发送POST请求必须设置如下两行
                httpsConn.setDoOutput(true);
                httpsConn.setDoInput(true);
                httpsConn.setRequestMethod("POST");
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(httpsConn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(httpsConn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                // 断开连接
                httpsConn.disconnect();
                System.out.println("====result===="+result);
                System.out.println("result:" + httpsConn.getResponseMessage());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

         return result;
    }

    public static String HttpProxy(String url, String param, String proxy, int port) {
        HttpURLConnection httpConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        Proxy proxy1=null;
        try {
            URL urlClient = new URL(url);
            System.out.println("==============URL========:" + urlClient);
            
	            if(null!=proxy&&!proxy.equals("")&&port>0) {
	            	proxy1=new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
	            	httpConn = (HttpsURLConnection) urlClient.openConnection(proxy1);
	            }
	            else {
	            	 httpConn = (HttpsURLConnection) urlClient.openConnection();
	            }
                
                // 设置连接主机服务器超时时间：30000毫秒
                httpConn.setConnectTimeout(30000);
                // 设置读取主机服务器返回数据超时时间：60000毫秒
                httpConn.setReadTimeout(60000);
                
                // 设置通用的请求属性
                httpConn.setRequestProperty("accept", "*/*");
                httpConn.setRequestProperty("connection", "Keep-Alive");
                httpConn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 发送POST请求必须设置如下两行
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(httpConn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(httpConn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                // 断开连接
                httpConn.disconnect();
                System.out.println("result"+result);
                System.out.println("result:" + httpConn.getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

         return result;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        log.info("url:{}",url);
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info("key{}------{}",key,map.get(key));
                //	System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！{}" , e);
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


