package jasper.demo.producer;

import jasper.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuyuanju1
 * @date 2020/3/27
 * @description:
 */
@Component
@Slf4j
public class UserSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        User user = new User().setName("Jasper").setCode("001").setAge(24);
        amqpTemplate.convertAndSend("user", user);
    }
}
