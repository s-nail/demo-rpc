package com.hundsun.jrescloud.demo.rpc.server.common.task.scheduler;

import com.hundsun.jrescloud.common.util.ConfigUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.util.QuartzManager;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created by jiayq24996 on 2019-08-20
 */
@Component
@Order(value = 1)
public class RefreshLicenceCacheRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(RefreshLicenceCacheRunner.class);
    @Override
    public void run(String... args) throws Exception {
        try {
            //*/5 * * * * ?
            String corn = ConfigUtils.get("hs.license.refresh.cache.task.corn", "0 0 23 * * ?");
            QuartzManager.startScheduler(new RefreshLicenceJob(), "RefreshLicenceTask", corn);
        } catch (SchedulerException e) {
            logger.error("定时刷新Licence缓存异常：" + e.getMessage());
        }
    }
}

