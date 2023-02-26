package com.salimcankaya.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.salimcankaya.config.RabbitMQConfig;
import com.salimcankaya.model.dto.CustomerSmsDto;


@Service
public class SmsService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	
	/**
	 * Send SMS
	 * 
	 * @param customerSmsDto
	 */
	@RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void smsService(CustomerSmsDto customerSmsDto) {
        
		logger.info("Message sent to phone number: " + customerSmsDto.getPhoneNumber());
		logger.info("Dear " + StringUtils.capitalize(customerSmsDto.getName()) + " " + customerSmsDto.getLastName().toUpperCase() + "," +
                "Your application for a loan is approved at " + customerSmsDto.getApprovalDate() + "," +
                "Loan amount is: " + customerSmsDto.getLoanAmount());
    }

}
