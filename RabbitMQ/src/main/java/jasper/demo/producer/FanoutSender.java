package jasper.demo.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuyuanju1
 * @date 2020/3/28
 * @description:
 */
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String context = "fanout message";
        // fanout 模式 route key 无效
        amqpTemplate.convertAndSend("fanoutExchange", "", context);
    }
}
