package com.mazhe.service;

import com.alibaba.fastjson.JSONObject;
import com.mazhe.util.HttpsUntils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class JsoupImageService {


    public static void main(String[] args) throws Exception {
        System.out.println("进入图片下载");
        String url = "https://origin.cpc.ncep.noaa.gov/products/people/mchen/CFSv2FCST/monthly/";
        String encoding ="utf-8";
        String fengzhuang = Fengzhuang(url,encoding);
        System.out.println(fengzhuang);
    }
    public static String Fengzhuang(String url,String encoding ) throws Exception {


        Document doc = Jsoup.connect(url)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Referer", url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000)
                .get();


        Elements divs = doc.select("table tr");
        int j=0;
        for (Element tr : divs) {
            Elements tds = tr.select("td");

            for(int i = 0; i < tds.size(); i++){
                if(i==1){
                   // log.info(""+tds.get(i).html());
                    if(!StringUtils.isEmpty(tds.get(i).select("a").html())){
                        if(tds.get(i).select("a").first().html().equals("Image")){

                            //todo 是否存在
                            String  imageurl= tds.get(i).select("a").attr("href");

                            imageurl="https://origin.cpc.ncep.noaa.gov/products/people/mchen/CFSv2FCST/monthly/"+imageurl;
                            log.info(imageurl);
                            download(imageurl, j);
                            j++;
                        }
                    }


                }
            }

        }
        return null;
    }


    //url 图片文件保存到本地
    //java 通过url下载图片保存到本地
    public static void download(String urlString, int i) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流  C:\Users\Administrator\Desktop
        String filename = "C:\\Users\\Administrator\\Desktop\\images\\" + i + ".jpg";  //下载路径及下载图片名称
        File file = new File(filename);
        FileOutputStream os = new FileOutputStream(file, true);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        System.out.println(i);
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

}
