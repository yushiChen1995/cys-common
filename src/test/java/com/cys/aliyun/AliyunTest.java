package com.cys.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author cys
 * @date 2019/9/3
 */

@Slf4j
public class AliyunTest {

    /**
     * 测试上传文件流
     */
    @Test
    public void testAliYunOss() throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAIoYnHqdqWr77t";
        String accessKeySecret = "iqo7OrXUqEJ6PbzVyC00BAPuMTtVi4";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("D://1.png");
        ossClient.putObject("qc-chenyushi", "abc.png", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    @Test
    public void testLog() {
        Exception exception = new Exception("测试");
        log.info("e:{}", exception.getMessage());
    }
}
