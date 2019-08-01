package com.hundsun.jrescloud.demo.rpc.server.common.mq;

import com.hundsun.jrescloud.mq.api.OutputExchange;
import com.hundsun.jrescloud.mq.exception.BaseMqException;
import com.hundsun.jrescloud.mq.message.DefaultMessage;
import com.hundsun.jrescloud.mq.message.Message;
import com.hundsun.jrescloud.rpc.annotation.CloudComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jiayq24996 on 2019-08-01
 */
//@CloudComponent("MQProvider")
public class MQProvider {
    @Autowired
    private OutputExchange outputExchange;

    public void publish(String instanceId, Message message) throws BaseMqException {
        outputExchange.publish("output1", DefaultMessage.build("TP_MESSAGE_DEFAULT.EC_DEFAULT", "test_test"));
    }
}
