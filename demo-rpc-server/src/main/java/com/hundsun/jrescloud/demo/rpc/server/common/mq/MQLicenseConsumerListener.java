package com.hundsun.jrescloud.demo.rpc.server.common.mq;

import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.util.LicenseContentLoader;
import com.hundsun.jrescloud.mq.annotation.EnableBinding;
import com.hundsun.jrescloud.mq.annotation.MessageBinding;
import com.hundsun.jrescloud.mq.context.MessageContext;
import com.hundsun.jrescloud.mq.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiayq24996 on 2019-08-01
 */
@EnableBinding
public class MQLicenseConsumerListener {
    private static final Logger logger = LoggerFactory.getLogger(MQLicenseConsumerListener.class);

    @MessageBinding("licenseConsumer")
    public void receive(Object paload, MessageContext context, Message message) throws Exception {
        logger.info("================MQ消息到了:" + message.getPayload());
        String notice = message.getPayload();
        String licenceContent = LicenseContentLoader.getInstance().callPermitCenterApi(notice);
        if (StringUtils.isNotEmpty(licenceContent)) {
            LicenseContentLoader.getInstance().clear();
            LicenseContentLoader.getInstance().init(licenceContent);
        }
    }

}
