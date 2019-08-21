package com.hundsun.jrescloud.demo.rpc.server.common.task.scheduler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.LicenseContentLoader;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@DisallowConcurrentExecution
public class RefreshLicenceJob implements Job {
    private Logger logger = LoggerFactory.getLogger(RefreshLicenceJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.debug("executing its JOB at " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        System.out.println("executing its JOB at " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        //1.HTTP请求许可中心获取对应系统的许可文件
        String licenceContent = LicenseContentLoader.getInstance().callPermitCenterApi();
        //2.存入本地缓存
        LicenseContentLoader.getInstance().clear();
        LicenseContentLoader.getInstance().init(licenceContent);
    }

}