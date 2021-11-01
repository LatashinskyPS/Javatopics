package com.intexsoft.notificationssevice.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intexsoft.notificationssevice.models.MessageFromBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public class BanksMessageConverter extends AbstractMessageConverter {
    Logger logger = LoggerFactory.getLogger(BanksMessageConverter.class);

    @Override

    protected @Nonnull
    Message createMessage(@NonNull Object object, @Nonnull MessageProperties messageProperties) {
        return new Message(object.toString().getBytes(StandardCharsets.UTF_8), messageProperties);
    }

    @Override
    public @Nonnull
    Object fromMessage(Message message) throws MessageConversionException {
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            return new ObjectMapper().readValue(str, MessageFromBank.class);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
        }
        throw new MessageConversionException("Can't convert:" + message);
    }
}
