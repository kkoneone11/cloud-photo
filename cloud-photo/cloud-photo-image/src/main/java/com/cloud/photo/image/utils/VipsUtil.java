package com.cloud.photo.image.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class VipsUtil {

    static String windowsCommand = "D:/Java/vips-dev-8.14/bin/vipsthumbnail.exe";
    static String linuxCommand = "/usr/local/bin/vipsthumbnail";
    //生成缩略图
    public static Boolean thumbnail(String srcPath, String desPath, int tarWidth , int tarHight, String quality) {

        String size_param = "";

        //原图片宽高均小于目前宽高，则直接用原图宽高
        //获取图片宽高
        size_param = tarWidth + "x" + tarHight;

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(stdout);
        log.info("thumbnail() prepare tools info ");

        //加入参数-t表示自动旋转
        String command = "";
        if (desPath.endsWith(".png")) {
            command = String.format("%s -t %s -s %s -o %s[Q=%s,strip]",windowsCommand, srcPath, size_param, desPath, quality);
        }else {
            command = String.format("%s -t %s -s %s -o %s[Q=%s,optimize_coding,strip]",windowsCommand, srcPath, size_param, desPath, quality);
        }

        CommandLine cl = CommandLine.parse(command);
        log.info("thumbnail() command = " + command);

        DefaultExecutor exec = new DefaultExecutor();
        exec.setStreamHandler(psh);
        try {
            System.out.println(command);
            exec.execute(cl);
        } catch (IOException e) {
            log.warn("thumbnail() exec command failed!");
        }
        return true;
    }

}
