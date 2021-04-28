package by.dm13y.reactorcache.config;

import by.dm13y.reactorcache.model.entity.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    @Bean
    ReactiveRedisOperations<String, Resource> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Resource> serializer = new Jackson2JsonRedisSerializer<>(Resource.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Resource> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Resource> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
