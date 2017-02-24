package com.zhaiyz.activemq;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * 消息消费者
 * 
 */
public class Consumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

	private JmsTemplate jmsTemplate;
	
	private Destination replyDestination;
	
	/**
	 * @return the jmsTemplate
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/**
	 * @param jmsTemplate
	 *            the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getReplyDestination() {
		return replyDestination;
	}

	public void setReplyDestination(Destination replyDestination) {
		this.replyDestination = replyDestination;
	}

	public void receiveMessage(String request) {
		LOGGER.info(request);
		jmsTemplate.convertAndSend(replyDestination, request + "的应答！");
	}
}
