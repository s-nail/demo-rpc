package com.hundsun.jrescloud.demo.rpc.server.common.mq;

import com.hundsun.jrescloud.mq.annotation.EnableBinding;
import com.hundsun.jrescloud.mq.annotation.MessageBinding;
import com.hundsun.jrescloud.mq.context.MessageContext;
import com.hundsun.jrescloud.mq.message.Message;

/**
 * Created by jiayq24996 on 2019-08-01
 */
@EnableBinding
public class MQConsumerListener {
    @MessageBinding("licenseConsumer")
    public void receive(Object paload, MessageContext context, Message message) throws Exception {
        System.out.println("================消息到了:");
        System.out.println(context.getTopic());
        System.out.println(message.getMsgId());
        System.out.println((String) message.getPayload());
    }

}
