package nl.mpdev.books_crud_demo.config;

import nl.mpdev.books_crud_demo.services.security.CustomUserDetailsService;
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
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // This annotation is not needed in Spring Boot 2.0 (Spring Security 5.0) and later versions of Spring Boot because
// Spring Boot automatically enables Spring Security.
public class SpringSecurityConfig {

  private final DataSource dataSource;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  UserDetailsService userDetailsService;

  // the @Value annotation is used to inject values from the application.properties file
  // The could be used to set the default user and admin
  @Value("${APP_USER_USERNAME}")
  private String userUsername;
  @Value("${APP_USER_PASSWORD}")
  private String userPassword;
  @Value("${APP_ADMIN_USERNAME}")
  private String adminUsername;
  @Value("${APP_ADMIN_PASSWORD}")
  private String adminPassword;

  @Autowired
  public SpringSecurityConfig(DataSource dataSource, @Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
//  public SpringSecurityConfig(DataSource dataSource) {

    this.dataSource = dataSource;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  // Bean for the password encoder to encode the password
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // The userDetailsService is custom and is autowired als a field in this class, i coulld however use constructor injection
// The main benifits:  Custom Authentication Logic, Flexibility, Integration, Reusability, Control
  //The UserDetailsService is can not be used in combination with this method
//  @Bean
//  public AuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setPasswordEncoder(passwordEncoder());
//    provider.setUserDetailsService(userDetailsService);
//    return provider;
//  }


  @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  // the filterChain method is used to configure the security filter chain
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login").permitAll()
        .requestMatchers("/register").permitAll()
        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/info").hasAuthority("WRITE_PRIVILEGE")
        .requestMatchers(HttpMethod.POST, "/register").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/api/csrf-token").hasAnyRole("ADMIN", "USER")
        .anyRequest().denyAll())
      .httpBasic(Customizer.withDefaults())
//      .formLogin(Customizer.withDefaults())
//      .logout(logout -> logout.logoutSuccessUrl("/"))
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
  }

    //This is the default way to create a user
    //This is not needed if you use the JdbcUserDetailsManager
    //This is not needed if you use the AuthenticationProvider
    // This is mainly used for setting up a default user
    // Also the default provider is overtaken by the JdbcUserDetailsManager if you use it. So the jdbc one is used
//  @Bean
//  public UserDetailsService users() {
//    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//    if (!users.userExists(userUsername)) {
//      UserDetails user = User.builder()
//        .username("user")
//        .password(passwordEncoder().encode(userPassword))
//        .roles("USER")
//        .build();
//      users.createUser(user);
//    }
//    if (!users.userExists("admin")) {
//      UserDetails admin = User.builder()
//        .username("admin")
//        .password(passwordEncoder().encode("password"))
//        .authorities("ROLE_USER", "READ_PRIVILEGE", "WRITE_PRIVILEGE", "DELETE_PRIVILEGE", "ROLE_ADMIN", "ROLE_USER")
//        .build();
//
//      users.createUser(admin);
//    }
//    return users;
//  }

// This could be use if there is circular dependency in combination with the method below
// However you could also put the lazy annotation on the dependency injection in the constructor
//  @Bean
//  public JwtAuthenticationFilter jwtAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
//    return new JwtAuthenticationFilter(jwtService, userDetailsService);
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
//  }

    //Benificial because of: Centralized Configuration, Custom Queries, Flexibility
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

  }
