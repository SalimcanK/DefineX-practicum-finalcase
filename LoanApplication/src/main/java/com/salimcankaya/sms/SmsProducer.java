package com.salimcankaya.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.salimcankaya.config.RabbitMQConfig;
import com.salimcankaya.model.dto.CustomerSmsDto;


@Service
public class SmsProducer {
	
	
	private final RabbitTemplate rabbitTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(SmsProducer.class);
	
	
	public SmsProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}


	/**
	 * Produce SMS when loan is approved
	 * 
	 * @param CustomerSmsDto object
	 */
	public void messageOnLoanApproval(CustomerSmsDto customerSmsDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, customerSmsDto);
        logger.info("SMS produced");
    }

}
