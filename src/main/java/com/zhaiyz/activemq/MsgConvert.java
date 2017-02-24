package com.zhaiyz.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class MsgConvert implements MessageConverter {

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		if (!(object instanceof String)) {
			throw new MessageConversionException("obj is not String");
		}

		Message message = session.createTextMessage((String) object);

		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {

		if (!(message instanceof TextMessage)) {
			throw new MessageConversionException("message is not TextMessage");
		}
		
		String msg = ((TextMessage)message).getText();
		
		return msg;
	}

}
