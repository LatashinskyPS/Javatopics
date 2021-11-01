package com.latashinsky.banksservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    private final ConnectionFactory connectionFactory;

    public RabbitConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue telegramQueue() {
        Queue queue = new Queue("telegram");
        queue.getArguments().put("x-dead-letter-exchange", "dead-exchange");
        return queue;
    }

    @Bean
    public Queue emailQueue() {
        Queue queue = new Queue("email");
        queue.getArguments().put("x-dead-letter-exchange", "dead-exchange");
        return queue;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("main-exchange");
    }

    @Bean
    public TopicExchange deadTopicExchange() {
        return new TopicExchange("dead-exchange", true, false, null);
    }

    @Bean
    public Queue deadTelegramQueue() {
        return new Queue("dead-telegram", true, false, false);
    }

    @Bean
    public Queue deadEmailQueue() {
        return new Queue("dead-email", true, false, false);
    }

    @Bean
    public Binding bindingTelegrams() {
        return BindingBuilder.bind(telegramQueue()).to(topicExchange()).with("*.*.telegram");
    }

    @Bean
    public Binding bindingEmails() {
        return BindingBuilder.bind(emailQueue()).to(topicExchange()).with("*.*.email");
    }

    @Bean
    public Binding bindingDeadTelegramQueue() {
        return BindingBuilder.bind(deadTelegramQueue()).to(deadTopicExchange()).with("*.*.telegram");
    }

    @Bean
    public Binding bindingDeadEmailQueue() {
        return BindingBuilder.bind(deadEmailQueue()).to(deadTopicExchange()).with("*.*.email");
    }

}
