package me.yeonnex.myspringsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인가
        http.authorizeRequests()
                .anyRequest()
                .authenticated(); // 모든 요청은 인증되어야 함 🤨

        // 인증
        http
                .formLogin()
//                .loginPage("/loginPage")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .usernameParameter("userId")
                .passwordParameter("passwd")
                .loginProcessingUrl("/login_proc")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{
                        System.out.println("로그인 실패! exception: " + exception.getMessage());
                        response.sendRedirect("/login");
                    }
                })
                .successHandler(((request, response, authentication) -> {
                    System.out.println("로그인 성공! authentication: " + authentication.getName());
                response.sendRedirect("/");
                }))
                .permitAll(); // 단 로그인 경로만 뺴고 🤔

        http.logout()
                .logoutUrl("/logout") // POST 방식
                .logoutSuccessUrl("/login") // 스프링 시큐리티는 로그아웃시 post 방식이 디폴트.
                .addLogoutHandler(new LogoutHandler() { // 스프링 시큐리티가 해주는 로그아웃 핸들러 이외에도, 추가하고 싶은 행동 등록
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession();
                        session.invalidate(); // 세션 무효화
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                })
                .deleteCookies("remember-me"); // 쿠키 삭제

        http.rememberMe() // rememberMe 기능이 작동함
                .rememberMeParameter("remember") // 기본 파라미터명은 remember-me
                .tokenValiditySeconds(3600) // 1시간으로 설정. 디폴트는 14일
                .alwaysRemember(true) // 리멤버미 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService);

        /**
         * 동시 세션 제어
         */
        http.sessionManagement() // 세션 관리 기능이 작동함
                .maximumSessions(1) // 최대 가능 세션수. -1 은 무제한 로그인 세션 허용
                .maxSessionsPreventsLogin(false) // 최대 가능 허용개수를 초과했을 떄 어떻게 할 것인가? true: 현재 사용자 인증 실패. false 는 이전 세션 만료.
                .expiredUrl("/expired"); // 세션이 만료된 경우 이동할 페이지

        /**
         * 세션 고정 보호 (로그인 전, 후 세션 id 를 바꿔주는 것이 기본으로 설정되어있음. 보안상 매우 필요한 기능을 스프링 시큐리티가 편리하게 제공하고 있음.)
         */
        http.sessionManagement()
                .sessionFixation().changeSessionId();

    }
}
