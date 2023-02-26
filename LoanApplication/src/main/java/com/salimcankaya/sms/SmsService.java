package com.salimcankaya.sms;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.salimcankaya.config.RabbitMQConfig;
import com.salimcankaya.model.dto.CustomerSmsDto;


@Service
public class SmsService {
	
	
	/**
	 * Send SMS
	 * 
	 * @param customerSmsDto
	 */
	@RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void smsService(CustomerSmsDto customerSmsDto) {
        System.out.println("Message sent to phone number: " + customerSmsDto.getPhoneNumber() + "\n");
        System.out.println("Dear " + StringUtils.capitalize(customerSmsDto.getName()) + " " + customerSmsDto.getLastName().toUpperCase() + "," +
                "\nYour application for a loan is approved at " + customerSmsDto.getApprovalDate() +
                "\nLoan amount is: " + customerSmsDto.getLoanAmount());
    }

}
