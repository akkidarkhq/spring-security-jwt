package com.unoveo.security;


//https://github.com/nijogeorgep/Java-Spring-JWT-Authentication/blob/master/src/main/java/com/cloudwalker/jwtauth/jwt/JwtAuthEntryPoint.java

import com.unoveo.jwt.JwtAuthEntryPoint;
import com.unoveo.jwt.JwtAuthFilter;
import com.unoveo.jwt.JwtUtils;
import com.unoveo.persistence.PersistenceJPAConfig;
import com.unoveo.repository.RoleRepository;
import com.unoveo.repository.UserRepository;
import com.unoveo.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackageClasses={UserRepository.class, RoleRepository.class})
@ComponentScan(basePackages = "com.unoveo.security")
public class SecurityConfiguration {
    @Autowired
    PersistenceJPAConfig persistenceJPAConfig;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean JwtAuthFilter getJwtAuthFilter(){
        return new JwtAuthFilter();
    }

    @Bean JwtUtils getJwtUtils(){
        return new JwtUtils();
    }

    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    public JwtAuthEntryPoint getUnauthorizedHandler(){
        return new JwtAuthEntryPoint();
    }

    @Bean PersistenceJPAConfig getPersistenceJPAConfig(){
        return new PersistenceJPAConfig();
    }


//    @Bean
//    DaoAuthenticationProvider authProvider(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(getPassWordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public ProviderManager authManagerBean(HttpSecurity security) throws Exception {
//        return (ProviderManager) security.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(authProvider()).
//                build();
//    }
/// insteadd down one

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPassWordEncoder());
        return new ProviderManager(authProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("com.unoveo.security filter chain method called");
        http.cors(cors->cors.configurationSource(corsConfigurationSource())).csrf().disable()
                .authorizeHttpRequests(authorize -> {
                            try {
                                authorize.requestMatchers("/api/auth/**").permitAll().anyRequest()
                                        .authenticated().and().exceptionHandling(exc->exc.authenticationEntryPoint(unauthorizedHandler));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//                http.formLogin(withDefaults())
//                .httpBasic(withDefaults()).logout().logoutUrl("/logout")
//                .deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedPage("/accessDenied");


        return http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();



//        http.cors(cors->cors.configurationSource(corsConfigurationSource())).csrf().disable();
//                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                http.authorizeRequests(authorize -> authorize.
//                        requestMatchers("/login").permitAll().
//                        requestMatchers("/userpage").hasAnyRole("USER").
//                                requestMatchers("/adminpage").hasAnyRole("ADMIN").
//                                requestMatchers("/homepage").hasAnyRole("USER","ADMIN").anyRequest().authenticated()
//
//                                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')").
//
//                                requestMatchers("/userpage")
//                                .access("hasRole('ROLE_USER')")
//                                .requestMatchers("/adminPage")
//                                .access("hasRole('ROLE_ADMIN')").anyRequest().authenticated()
//                )
//                .formLogin(withDefaults())
//                .httpBasic(withDefaults()).logout().logoutUrl("/logout")
//                .deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedPage("/accessDenied");
//        return http.build();
    }



    @Bean
    UserDetailsManager users() {
        System.out.println("users method");
        UserDetails user = User.builder().username("nikhil")
                .password(getPassWordEncoder().encode("nikhil123")).
                roles("USER").build();
        UserDetails admin = User.builder().username("durgesh")
                .password(getPassWordEncoder().encode("durgesh123")).
                roles("ADMIN").build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(persistenceJPAConfig.getdataSource());
        System.out.println(users);

        users.createUser(user);
      users.createUser(admin);
        System.out.println(admin);
        System.out.println(user);

        return users;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200/"));
    configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }


}