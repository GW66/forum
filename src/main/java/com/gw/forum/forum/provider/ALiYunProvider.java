package com.gw.forum.forum.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class ALiYunProvider {
// Endpoint以杭州为例，其它Region请按实际情况填写。
    @Value("${aliyun.endpoint}")
    private String endpoint;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.bucketName}")
    private String bucketName;
    public String upload(String fileName,InputStream inputStream){
        String[] split = fileName.split("\\.");
        if (split.length>1){
            fileName=UUID.randomUUID().toString()+"."+split[split.length-1];
        }else {
            return null;
        }
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 上传文件。
        ossClient.putObject(bucketName, fileName,inputStream);
// 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 24 * 3600 * 1000);
// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
        String fileUrl=url.toString();
// 关闭OSSClient。
        ossClient.shutdown();

        return fileUrl;
    }
}
