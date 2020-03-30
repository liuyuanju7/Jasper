package jasper.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author liuyuanju1
 * @date 2020/3/27
 * @description:
 */
@Component
@Slf4j
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String context = "hello " + LocalDateTime.now();
        System.out.println("Send: " + context);
        amqpTemplate.convertAndSend("hello", context);
    }
}
