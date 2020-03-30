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
public class TopicSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String context = "I am message 0: RouteKey: topic.1";
        amqpTemplate.convertAndSend("topicExchange", "topic.1", context);
    }

    public void send1() {
        String context = "I am message 1: RouteKey: topic.message";
        amqpTemplate.convertAndSend("topicExchange", "topic.message", context);
    }

    public void send2() {
        String context = "I am message 2: RouteKey: topic.messages";
        amqpTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }



}
