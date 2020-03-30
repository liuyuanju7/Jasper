package jasper.demo;

import jasper.demo.producer.FanoutSender;
import jasper.demo.producer.Sender;
import jasper.demo.producer.TopicSender;
import jasper.demo.producer.UserSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqApplicationTests {

	@Autowired
	private Sender sender;
	@Autowired
	private UserSender userSender;
	@Autowired
	private TopicSender topicSender;
	@Autowired
	private FanoutSender fanoutSender;

	@Test
	void hello() {
		sender.send();
	}

	@Test
	void sendUser() {
		userSender.send();
	}

	@Test
	void testTopic() {
		topicSender.send();
		topicSender.send1();
		topicSender.send2();
	}

	@Test
	void fanoutSender() {
		fanoutSender.send();
	}

	@Test
	void contextLoads() {
	}



}
