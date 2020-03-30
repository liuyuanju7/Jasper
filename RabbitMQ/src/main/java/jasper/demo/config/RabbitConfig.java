package jasper.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author liuyuanju1
 * @date 2020/3/27
 * @description:
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue helloQueue() {
        // Queue是 org.springframework.amqp.core.Queue; 不是 Util
        return new Queue("hello");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }
}
