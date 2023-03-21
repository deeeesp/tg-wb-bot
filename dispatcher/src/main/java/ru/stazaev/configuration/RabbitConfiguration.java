package ru.stazaev.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.stazaev.queue.RabbitQueue.*;

@Configuration
public class RabbitConfiguration {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue registerUserQueue() {
        return new Queue(REGISTER_USER);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(ANSWER_MESSAGE);
    }

    @Bean
    public Queue weatherRequestQueue() {
        return new Queue(WEATHER_REQUEST);
    }

    @Bean
    public Queue messageHandlerQueue() {
        return new Queue(MESSAGE_HANDLER);
    }

    @Bean
    public Queue cityCodeRequestQueue() {
        return new Queue(CITY_CODE_REQUEST);
    }

    @Bean
    public Queue cityCodeResponseQueue() {
        return new Queue(CITY_CODE_RESPONSE);
    }

    @Bean
    public Queue hourForecastQueue() {
        return new Queue(HOUR_FORECAST);
    }

    @Bean
    public Queue twelveHoursForecastQueue() {
        return new Queue(TWELVE_HOURS_FORECAST);
    }

    @Bean
    public Queue dayForecastQueue() {
        return new Queue(DAY_FORECAST);
    }

    @Bean
    public Queue fiveDaysForecastQueue() {
        return new Queue(FIVE_DAYS_FORECAST);
    }
}
