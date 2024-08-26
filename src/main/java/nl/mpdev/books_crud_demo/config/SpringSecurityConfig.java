package nl.mpdev.books_crud_demo.config;

import nl.mpdev.books_crud_demo.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // This annotation is not needed in Spring Boot 2.0 (Spring Security 5.0) and later versions of Spring Boot because
// Spring Boot automatically enables Spring Security.
public class SpringSecurityConfig {

  private final DataSource dataSource;
//  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Value("${APP_USER_USERNAME}")
  private String userUsername;

  @Value("${APP_USER_PASSWORD}")
  private String userPassword;

  @Value("${APP_ADMIN_USERNAME}")
  private String adminUsername;

  @Value("${APP_ADMIN_PASSWORD}")
  private String adminPassword;


  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
//  public SpringSecurityConfig(DataSource dataSource, JwtAuthenticationFilter jwtAuthenticationFilter) {
  public SpringSecurityConfig(DataSource dataSource) {

    this.dataSource = dataSource;
//    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    provider.setUserDetailsService(users());
    return provider;
  }

  @Bean
  public UserDetailsService users() {
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    if (!users.userExists(userUsername)) {
      UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder().encode(userPassword))
        .roles("USER")
        .build();
      users.createUser(user);
    }
    if (!users.userExists("admin")) {
      UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("password"))
        .authorities("ROLE_USER", "READ_PRIVILEGE", "WRITE_PRIVILEGE", "DELETE_PRIVILEGE", "ROLE_ADMIN", "ROLE_USER")
        .build();

      users.createUser(admin);
    }
    return users;
  }

  // Step 3: Initialize dependent beans next
  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
    return new JwtAuthenticationFilter(jwtService, userDetailsService);
  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/info").hasAuthority("WRITE_PRIVILEGE")
        .requestMatchers("/api/csrf-token").hasAnyRole("ADMIN", "USER")
        .anyRequest().denyAll())
      .httpBasic(Customizer.withDefaults())
//      .formLogin(Customizer.withDefaults())
//      .logout(logout -> logout.logoutSuccessUrl("/"))
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

  }

//  private final DataSource dataSource;
//
//  @Autowired
//  public SpringSecurityConfig(DataSource dataSource) {
//    this.dataSource = dataSource;
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

//  @Bean
//  public JdbcUserDetailsManager users(DataSource dataSource) {
//    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//    if (!users.userExists("user")) {
//      UserDetails user = User.builder()
//        .username("user")
//        .password(passwordEncoder().encode("password"))
//        .roles("USER")
//        .build();
//      users.createUser(user);
//    }
//    if (!users.userExists("admin")) {
//      UserDetails admin = User.builder()
//        .username("admin")
//        .password(passwordEncoder().encode("password"))
//        .roles("USER", "ADMIN")
//        .build();
//      users.createUser(admin);
//    }
//    return users;
//  }

//  @Bean
//  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//    authenticationManagerBuilder
//      .jdbcAuthentication()
//      .dataSource(dataSource)
//      .passwordEncoder(passwordEncoder())
//      .usersByUsernameQuery(
//        "SELECT username, password, enabled" +
//          " FROM users" +
//          " WHERE username=?"
//      )
//      .authoritiesByUsernameQuery(
//        "SELECT username, authority" +
//          " FROM authorities " +
//          " WHERE username=?"
//      );
//    return authenticationManagerBuilder.build();
//  }

//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//      .csrf(csrf -> csrf.disable())
//      .authorizeHttpRequests(auth -> auth
//        .requestMatchers("/api/**").hasRole("USER")
//        .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
//        .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
//        .requestMatchers("/admins").hasRole("ADMIN")
//        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
//        .requestMatchers("/authenticate").permitAll()
//        .anyRequest().denyAll())
//      .httpBasic(Customizer.withDefaults());
//
//    return http.build();
//  }
//
//  @Bean
//  UserDetailsManager users(DataSource dataSource) {
//    UserDetails user = User.builder()
//      .username("user")
//      .password(passwordEncoder().encode("password"))
//      .roles("USER")
//      .build();
//    UserDetails admin = User.builder()
//      .username("admin")
//      .password(passwordEncoder().encode("password"))
//      .roles("USER", "ADMIN")
//      .build();
//    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//    users.createUser(user);
//    users.createUser(admin);
//    return users;
//  }

//  @Bean
//  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

//    authenticationManagerBuilder.inMemoryAuthentication()
//      .withUser("user")
//      .password(passwordEncoder()
//        .encode("password"))
//      .roles("USER")
//      .and()
//      .withUser("admin")
//      .password(passwordEncoder()
//        .encode("password"))
//      .roles("ADMIN");

//    return authenticationManagerBuilder.build();
//  }

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
