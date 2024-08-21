package nl.mpdev.books_crud_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig {

  private final DataSource dataSource;

  public SpringSecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder.inMemoryAuthentication()
      .withUser("user")
      .password(passwordEncoder()
        .encode("password"))
      .roles("USER")
      .and()
      .withUser("admin")
      .password(passwordEncoder()
        .encode("password"))
      .roles("ADMIN");

//    authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
//        "SELECT username, password, enabled" +
//          " FROM users" +
//          " WHERE username=?"
//      )
//      .authoritiesByUsernameQuery(
//        "SELECT username, authority" +
//          " FROM authorities " +
//          " WHERE username=?"
//      );

    return authenticationManagerBuilder.build();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
        .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/admins").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
        .requestMatchers("/authenticate").permitAll()
        .anyRequest().denyAll())
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }


//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//      .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
//      .csrf(csrf -> csrf.disable())
//      .httpBasic(Customizer.withDefaults())
//      .cors(Customizer.withDefaults())
//      .authorizeHttpRequests(auth -> auth
//        .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
//        .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
//        .requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
//        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
//        .requestMatchers("/authenticate").permitAll()
//        .anyRequest().denyAll()
//      )
//      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//    return http.build();
//  }

//  @Bean
//  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//    http
//      .csrf().disable()
//      .httpBasic().disable()
//      .cors().and()
//      .authorizeHttpRequests()
//      .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
//      .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
//      .requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
//      .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
//      .requestMatchers("/authenticate").permitAll()
//      .anyRequest().denyAll()
//      .and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//    return  http.build();
//
//  }

//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService, UserService userService) throws Exception {
//    http
//      .httpBasic(hp -> hp.disable())
//      .authorizeHttpRequests(auth -> auth
//        .requestMatchers("/albums/**").hasAnyRole("USER")
//        .anyRequest().denyAll())
//      .addFilterBefore(new JwtRequestFilter(jwtService, userService), UsernamePasswordAuthenticationFilter.class)
//      .csrf(csrf -> csrf.disable())
//      .cors(cors -> {
//      })
//      .sessionManagement(session ->
//        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//    ;
//    return http.build();
//  }
}
