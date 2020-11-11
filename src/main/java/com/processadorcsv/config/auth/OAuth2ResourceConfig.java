package com.processadorcsv.config.auth;

import com.processadorcsv.config.CorsConfigFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static java.util.Arrays.asList;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    private static final String TEST_PROFILE = "test";

    @Autowired
    private Environment environment;

    @Override
    @SuppressWarnings({"checkstyle:methodlength"})
    public void configure(HttpSecurity http) throws Exception {
        String[] permitAll = {
            "/login/**",
            "/oauth/token",
            "/oauth/authorize",
            "/swagger-ui.html**",
            "/swagger-resources/**",
            "/v2/api-docs**",
            "/webjars/**",
            "/api/docs",
            "/api/personagem/**"
        };

        http
            .addFilterBefore(new CorsConfigFilter(), ChannelProcessingFilter.class)
            .requestMatchers()
            .antMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers(permitAll).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        if (asList(environment.getActiveProfiles()).contains(TEST_PROFILE)) {
            resources.stateless(false);
        }
    }
}
