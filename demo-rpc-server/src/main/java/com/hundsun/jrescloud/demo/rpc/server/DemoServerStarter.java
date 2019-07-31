package com.hundsun.jrescloud.demo.rpc.server;

import com.hundsun.jrescloud.common.boot.CloudApplication;
import com.hundsun.jrescloud.common.boot.CloudBootstrap;
import com.hundsun.jrescloud.common.web.CloudServletInitializer;

@CloudApplication
public class DemoServerStarter extends CloudServletInitializer {
    public static void main(String[] args) {
        CloudBootstrap.run(DemoServerStarter.class, args);
    }
}
