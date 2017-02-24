package com.zhaiyz.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;

/**
 * 消息消费者
 * 
 * @author zhaiyz
 */
public class ConsumerListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerListener.class);

	private JmsTemplate jmsTemplate;

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

	@Override
	public void onMessage(Message message) {

		if (message instanceof ObjectMessage) {
			try {
				String request = (String) (((ObjectMessage) message).getObject());
				LOGGER.info(request);
				Destination destination = message.getJMSReplyTo();
				final String jmsCorrelationID = message.getJMSCorrelationID();
				jmsTemplate.convertAndSend(destination, request + "的应答！", new MessagePostProcessor() {

					@Override
					public Message postProcessMessage(Message message) throws JMSException {
						message.setJMSCorrelationID(jmsCorrelationID);
						return message;
					}
				});
			} catch (JMSException e) {
				LOGGER.error("接收信息出错", e);
			}
		}
		
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				final String request = textMessage.getText();
				LOGGER.info(request);
				Destination destination = textMessage.getJMSReplyTo();
				final String jmsCorrelationID = textMessage.getJMSCorrelationID();
				jmsTemplate.send(destination, new MessageCreator() {

					@Override
					public Message createMessage(Session session) throws JMSException {
						Message msg = session.createTextMessage(request + "的应答！");
						msg.setJMSCorrelationID(jmsCorrelationID);
						return msg;
					}
				});
			} catch (JMSException e) {
				LOGGER.error("接收信息出错", e);
			}
		}
	}
}
