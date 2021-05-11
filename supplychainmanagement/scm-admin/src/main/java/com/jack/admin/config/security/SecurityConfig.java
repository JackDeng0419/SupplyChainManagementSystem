package com.jack.admin.config.security;


import com.jack.admin.config.ClassPathTldsLoader;
import com.jack.admin.filters.CaptchaCodeFilter;
import com.jack.admin.pojo.User;
import com.jack.admin.service.IRbacService;
import com.jack.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

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
    private CaptchaCodeFilter captchaCodeFilter;


    @Resource
    private IUserService userService;

    @Resource
    private DataSource dataSource;

    @Resource
    private IRbacService rbacService;

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
                    .rememberMe()
                    .rememberMeParameter("rememberMe")
                    //保存在浏览器端的cookie的名称，如果不设置默认也是remember-me
                    .rememberMeCookieName("remember-me-cookie")
                    //设置token的有效期，即多长时间内可以免除重复登录，单位是秒。
                    .tokenValiditySeconds(7  * 24 * 60 * 60)
                    //自定义
                    .tokenRepository(persistentTokenRepository())
                .and()
                    .logout()
                    .logoutUrl("/signout")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(smcLogoutSuccessHandler);

    }


    /**
     * 配置从数据库中获取token
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // 此处的dataSource在application.yml中定义，对应的dataSource对象由Spring IOC容器维护
        return tokenRepository;
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

                //根据用户名查询角色名称
                List<String> roleNames = rbacService.findRolesByUserName(username);
                //根据角色名称获取权限码
                List<String> authorities = rbacService.findAuthoritiesByRoleName(roleNames);
                System.out.println("权限："+authorities);
                roleNames = roleNames.stream().map(role->"ROLE_"+role).collect(Collectors.toList());
                authorities.addAll(roleNames);
                userDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities)));
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

    /**
     * 加载 ClassPathTldsLoader
     */
    @Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
    public ClassPathTldsLoader classPathTldsLoader(){
        return new ClassPathTldsLoader();
    }
}
