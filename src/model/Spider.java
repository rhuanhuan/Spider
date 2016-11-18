package model;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Spider {

    private static HttpClient httpClient = new HttpClient();

    public static boolean downloadPage(String path) throws Exception {
        // 定义输入输出流
        InputStream input = null;
        OutputStream output = null;
        // 得到 post 方法
        GetMethod getMethod = new GetMethod(path);
        // 执行，返回状态码
        int statusCode = httpClient.executeMethod(getMethod);
        // 针对状态码进行处理
        // 简单起见，只处理返回值为 200 的状态码
        if (statusCode == HttpStatus.SC_OK) {
            input = getMethod.getResponseBodyAsStream();
            // 通过对URL的得到文件名
            String filename = path.substring(path.lastIndexOf('/') + 1)
                    + ".html";
            // 获得文件输出流
            output = new FileOutputStream(filename);
            // 输出到文件
            int tempByte = -1;
            while ((tempByte = input.read()) > 0) {
                output.write(tempByte);
            }
            // 关闭输入流
            if (input != null) {
                input.close();
            }
            // 关闭输出流
            if (output != null) {
                output.close();
            }
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        try {
            // 抓取百度首页，输出
            Spider.downloadPage("https://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}