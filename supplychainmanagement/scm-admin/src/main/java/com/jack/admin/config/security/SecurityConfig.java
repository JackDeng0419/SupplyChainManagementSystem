package com.jack.admin.config.security;


import com.jack.admin.filters.CaptchaCodeFilter;
import com.jack.admin.pojo.User;
import com.jack.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@SpringBootConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SmcAuthenticationFailedHandler smcAuthenticationFailedHandler;

    @Autowired
    private SmcAuthenticationSuccessHandler smcAuthenticationSuccessHandler;

    @Autowired
    private SmcLogoutSuccessHandler smcLogoutSuccessHandler;

    @Resource
    private IUserService userService;

    @Resource
    private CaptchaCodeFilter captchaCodeFilter;
    /**
     * 放行静态资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/images/**",
                "/css/**",
                "/js/**",
                "/lib/**",
                "/error/**");
    }

    /**
     * 配置客制化登录方式
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .addFilterBefore(captchaCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable()
                .and()
                    .formLogin()
                    .usernameParameter("userName")
                    .passwordParameter("password")
                    .loginPage("/index")
                    .loginProcessingUrl("/user/login")
                    .successHandler(smcAuthenticationSuccessHandler)
                    .failureHandler(smcAuthenticationFailedHandler)
                .and()
                    .authorizeRequests().antMatchers("/index", "/login", "/image").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutUrl("/signout")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(smcLogoutSuccessHandler);

    }

    /**
     * Spring 会使用这个bean来获得user对象，然后根据这个user对象来对登录进行校验
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User userDetails = userService.findUserByUserName(username);
                return userDetails;
            }
        };
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 告诉系统我们的userDetail是哪个
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
    }
}
