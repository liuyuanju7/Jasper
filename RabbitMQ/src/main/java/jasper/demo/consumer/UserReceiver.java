package jasper.demo.consumer;

import jasper.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liuyuanju1
 * @date 2020/3/28
 * @description:
 */
@Slf4j
@Component
@RabbitListener(queues = "user")
public class UserReceiver {

    @RabbitHandler
    public void process(User user){
        System.out.println("ReceiveUser: " + user.toString());
    }
}
