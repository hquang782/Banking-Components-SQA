package org.studytest.savings_deposit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080")); // Thay đổi nguồn gốc được phép nếu cần
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Cấu hình bộ lọc bảo mật
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf((csrf) -> csrf.disable()) // Vô hiệu hóa CSRF protection
                .authorizeHttpRequests( // Cấu hình quyền truy cập cho các request
                        (authorize) -> authorize
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/api/v1/**").permitAll() // Cho phép tất cả các request tới "/api/v1/" không cần xác thực
                                .requestMatchers("/api/auth/**").permitAll() // Cho phép tất cả các request tới "/api/auth/" không cần xác thực
                                .requestMatchers("/savings-list","/home",
                                        "/login","/","/signin","/enter","/confirmSA","/nhapOTP","/api/v1/savings-accounts/sa/save").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/menu/**").hasRole("ADMIN") // Cấu hình quyền truy cập dựa trên vai trò người dùng
                                .anyRequest().authenticated()
                        // Các request khác cần phải được xác thực
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Cấu hình session không lưu trữ trạng thái
                )
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        return http.build(); // Trả về đối tượng SecurityFilterChain
    }
}
