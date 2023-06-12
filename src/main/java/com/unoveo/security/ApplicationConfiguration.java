package com.unoveo.security;

//import com.unoveo.db.User;
//import com.unoveo.db.UserDetailsServiceImpl;
//import com.unoveo.jwtutils.JWTRequestFilter;
//import com.unoveo.jwtutils.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@ComponentScan("com.unoveo.security.ApplicationConfiguration")
@Configuration

public class ApplicationConfiguration {

//    public DataSourceConfig dataSourceConfig;

    //    @Bean
//    public JWTRequestFilter getfilter(){
//        return new JWTRequestFilter();
//    }
//    @Bean JWTUtils getjwtUtils(){
//        return new JWTUtils();
//    }
//
//    @Bean
//    public CustomUserDetailsService getUserdetails() {
//        return new CustomUserDetailsService();
//    };

//    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

}



