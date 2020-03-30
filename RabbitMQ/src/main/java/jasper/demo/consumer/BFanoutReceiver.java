package jasper.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liuyuanju1
 * @date 2020/3/28
 * @description:
 */
@Component
@RabbitListener(queues = "fanout.B")
public class BFanoutReceiver {

    @RabbitHandler
    public void onMessage(String msg) {
        System.out.println("fanout.B receive: " + msg);
    }
}
