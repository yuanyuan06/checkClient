package com.jumbo.http;

/**
 * Created by Administrator on 2016/1/19.
 */
public class checkService {

    public static void main(String[] args) {
        System.setProperty("dubbo.properties.file", "conf\\dubbo.properties");
        com.alibaba.dubbo.container.Main.main(args);
    }
}
