package com.hundsun.jrescloud.demo.rpc.client;

import com.hundsun.jrescloud.common.boot.CloudApplication;
import com.hundsun.jrescloud.common.boot.CloudBootstrap;
import com.hundsun.jrescloud.common.web.CloudServletInitializer;

@CloudApplication
public class DemoClientStarter extends CloudServletInitializer {
    public static void main(String[] args) {
        CloudBootstrap.run(DemoClientStarter.class, args);
    }
}
