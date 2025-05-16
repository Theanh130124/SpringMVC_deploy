/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trantheanh1301.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trantheanh1301.filters.JwtFilter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true) // dùng cho PreAuthorize phải bật cả bên WebAppContext nữa

@ComponentScan(basePackages = {
    "com.trantheanh1301.controllers",
    "com.trantheanh1301.repository",
    "com.trantheanh1301.service",
    "com.trantheanh1301.utils",
    "com.trantheanh1301.filters"
})
//thằng này chạy trc

public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector
            mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    //Xem là mọi api đều được quyền -> fix lại
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        //Muốn chứng thực bằng jwt thì chỉ permit api bên này (còn muốn cho không jwt và form luôn thì bỏ trong cả 2 ) -> còn muốn chứng thực bằng form thì authenticated bên này
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(c -> c.disable()).authorizeHttpRequests(requests
                -> requests.requestMatchers("/", "/home").authenticated()
                        .requestMatchers("/api/**").permitAll() // nhưng thằng muốn không dùng jwt  thì bỏ bên jwtfilter WHITELIST
                        .requestMatchers("/js/**").permitAll().requestMatchers("/css/**").permitAll().requestMatchers("/images/**").permitAll()
                        .requestMatchers("/stats").hasAnyAuthority("Admin", "Doctor").requestMatchers("/license").hasAuthority("Admin")
                        .requestMatchers("/registerdoctor").hasAuthority("Admin").requestMatchers("/createclinic").hasAuthority("Admin")
                        .requestMatchers("/createspecialty").hasAuthority("Admin")
                        .anyRequest().authenticated()// mọi thứ còn lại đều cần token 

        )
                .formLogin(form -> form.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true").permitAll())
                .logout(logout
                        -> logout.logoutSuccessUrl("/login").permitAll());
        // Thêm JwtFilter vào filter chain
        http.addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dp9lw5n5g",
                        "api_key", "794682182282692",
                        "api_secret", "QKmsUnjc9uwBfY9VYb4dxoyWDcw",
                        "secure", true));
        return cloudinary;
    }

//        @Bean
//    @Order(0)
//    public StandardServletMultipartResolver multipartResolver() {
//        return new StandardServletMultipartResolver();
//    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000")); // frontend origin-> nữa có thể thay đổi 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); // Nếu dùng cookie/session

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    
}
