package com.douguo.ndc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400)
public class DcWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcWebApplication.class, args);
	}
}
