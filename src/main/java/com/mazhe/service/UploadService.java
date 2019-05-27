package com.mazhe.service;

import com.mazhe.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2019/5/19.
 */
@Service
public class UploadService {

    /***
     * 新增文件
     * @param file
     * @return
     */
    @Value("${file.uploadFolder}")
    private  String path;

    public String saveFile(MultipartFile file) {

        try {
            // 文件保存路径
            String filePath = path; //映射的地址
            //String filePath = request.getSession().getServletContext().getRealPath("upload/");本地项目路径
            String filename = file.getOriginalFilename();//获取file图片名称
            return uploadFile(file.getBytes(), filePath, filename);
           // return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        String path= filePath+ DateUtil.getCurrent()+"_"+fileName;
        FileOutputStream out = new FileOutputStream(path);
        out.write(file);
        out.flush();
        out.close();
        return path;
    }
}
