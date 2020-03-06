package com.sirzhangs.zuul.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sirzhangs.zuul.filter.TokenFilter;

@Configuration
public class ZuulConfigure {

	@Bean
	public TokenFilter tokeFileter() {
		return new TokenFilter();
	}
	
}
