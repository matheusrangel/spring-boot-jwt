package com.hackathon.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationManagerConfig extends GlobalAuthenticationConfigurerAdapter {
	
    @Autowired
    private DataSource dataSource;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource) //Overwriting default spring config because I dont need authorities or enabled field
        .usersByUsernameQuery("select username, password, 'true' from user u where u.username = ?")
        .authoritiesByUsernameQuery("select username, 'ADMIN' from user u where u.username = ?");
    }
	
}
