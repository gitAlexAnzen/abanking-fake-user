/*
 * Copyright (c)  2014 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import mx.com.anzen.app.abanking.fake.user.service.redis.model.FakeUser;
import mx.com.anzen.app.abanking.fake.user.service.redis.repository.FakeIDUserRepository;
import mx.com.anzen.app.abanking.fake.user.service.redis.repository.FakeUserRepository;
import mx.com.anzen.app.abanking.fake.user.service.redis.repository.RedisSimpleCrudRepository;

/**
 * configure the Redis connection and the data repositories
 */
@Configuration
public class RedisConfiguration {

	/**
	 * the redis's host
	 * TODO: to remove when using spring-boot
	 */
	@Value("${redis.server.host}")
	private String redisHost;
	
	/**
	 * the redis's port
	 * TODO: to remove when using spring-boot
	 */
	@Value("${redis.server.port}")
	private Integer redisPort;
	
	/**
	 * @return the Redis repository used by the default core-data session-service implementation
	 */
	@Bean(name = {"fakeUserRepository"})
	public RedisSimpleCrudRepository<FakeUser, String> fakeUserRepository(){
		return new FakeUserRepository();
	}
	
	/**
	 * @return the Redis repository used by the default core-data session-service implementation
	 */
	@Bean(name = {"fakeIDUserRepository"})
	public RedisSimpleCrudRepository<FakeUser, String> fakeIDUserRepository(){
		return new FakeIDUserRepository();
	}
	
	/**
	 * @return the Redis template used by the redis repositories
	 */
	@SuppressWarnings("rawtypes")
	@Bean
	public RedisTemplate redisTemplate(){
		RedisTemplate result = new RedisTemplate();
		result.setConnectionFactory(redisConnectionFactory());
		return result;
	}
	
	/**
	 * @return a redis connection
	 * TODO: the Jedis connection-factory will be removed when using spring-boot
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory(){
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
		redisConnectionFactory.setHostName(redisHost);
		redisConnectionFactory.setPort(redisPort);
		redisConnectionFactory.setUsePool(false);
		return redisConnectionFactory;
	}
}
