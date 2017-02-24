package com.zhaiyz.activemq;


import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * 消息生产者
 * 
 */
public class Producer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

	private JmsTemplate jmsTemplate;

	private Destination requestDestination;

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

	/**
	 * @return the requestDestination
	 */
	public Destination getRequestDestination() {
		return requestDestination;
	}

	/**
	 * @param requestDestination
	 *            the requestDestination to set
	 */
	public void setRequestDestination(Destination requestDestination) {
		this.requestDestination = requestDestination;
	}

	public boolean convertAndSendMessage(final String message) {
		jmsTemplate.convertAndSend(requestDestination, message);
		
		return true;
	}

	public void receiveMessage(String message) {
		LOGGER.info(message);
	}
}
