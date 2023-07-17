package com.ssongman.springredis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;

@Configuration
public class RedisConfig {
	
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;
	
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    
    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
    	
//		log.debug("RedisRepositoryConfig - Not local - Redis cluster");
//		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);  	
//		redisClusterConfiguration.setPassword(redisPassword);

		ClusterTopologyRefreshOptions topologyRefreshOptins = ClusterTopologyRefreshOptions.builder()
				.dynamicRefreshSources(true) // 노드들을 discovery 활성화(기본적용)
//				.enablePeriodicRefresh(Duration.ofSeconds(5))// 5초마다 업데이트 (기본은 false)
				.enableAllAdaptiveRefreshTriggers() // 모든 이벤트 트리거에 대한 자동 갱신 활성화(기본은 특정 이벤트)
//				.adaptiveRefreshTriggersTimeout(Duration.ofSeconds(5)) // 30초마다 커넥션 갱신 (기본 1초)
				.build();
		
		ClusterClientOptions clientOptions = ClusterClientOptions.builder()
				.topologyRefreshOptions(topologyRefreshOptins)
				.autoReconnect(true) // 자동 재접속 활성화
//				.timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofSeconds(5)).build()) // 커넥션 타임아웃 5초
				.suspendReconnectOnProtocolFailure(true) // 연결중 발생하는 프로토콜 오류시 연결 일시 중단
//				.maxRedirects(3) // 기본은 5
				.build();
		
		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
			.clientOptions(clientOptions)
			.build();	
		
        return new LettuceConnectionFactory(host, port);
    }

}
