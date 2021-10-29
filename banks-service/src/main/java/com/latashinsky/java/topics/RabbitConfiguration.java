package com.latashinsky.java.topics;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    Queue queue1() {
        Queue queue = new Queue("telegram");
        queue.getArguments().put("x-dead-letter-exchange", "dead-exchange");
        return queue;
    }

    @Bean
    public TopicExchange deadExchange() {
        return new TopicExchange("dead-exchange", true, false, null);
    }


    @Bean
    public Queue deadQueue1() {
        return new Queue("dead-telegram", true, false, false);
    }

    @Bean
    public Binding deadBinding1() {
        return BindingBuilder.bind(deadQueue1()).to(deadExchange()).with("*.*.telegram");
    }

    @Bean
    public Queue queue2() {
        Queue queue = new Queue("email");
        queue.getArguments().put("x-dead-letter-exchange", "dead-exchange");
        return queue;
    }

    @Bean
    public Queue deadQueue2() {
        return new Queue("dead-email", true, false, false);
    }

    @Bean
    public Binding deadBinding2() {
        return BindingBuilder.bind(deadQueue2()).to(deadExchange()).with("*.*.email");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("main-exchange");
    }

    @Bean
    public Binding bindingTopicToDelete() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("*.*.email");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("*.*.telegram");
    }
}
