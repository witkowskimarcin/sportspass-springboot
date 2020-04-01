package paczka.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private DataSource dataSource;

	private final String USERS_QUERY = "select email, password, active " + "from user where email=?";
	private final String ROLES_QUERY = "select u.email, r.role " + "from user u "
			+ "inner join user_role ur on (u.id = ur.user_id) " + "inner join role r on (ur.role_id=r.role_id) "
			+ "where u.email=?";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(USERS_QUERY).authoritiesByUsernameQuery(ROLES_QUERY)
				.dataSource(dataSource).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.authorizeRequests().antMatchers("/panel").permitAll();
//		http.authorizeRequests().antMatchers("/panel/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();

//        http.authorizeRequests().antMatchers("/").permitAll();
//		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
//		http.authorizeRequests().antMatchers("/client/**").hasAnyAuthority("CLIENT","COACH","OWNER","ADMIN").and().httpBasic();
//		http.authorizeRequests().antMatchers("/coach/**").hasAnyAuthority("COACH","OWNER","ADMIN").and().httpBasic();
//		http.authorizeRequests().antMatchers("/owner/**").hasAnyAuthority("OWNER","ADMIN").and().httpBasic();
//		http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN").and().httpBasic();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);

		return db;
	}

//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("OPTIONS");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("DELETE");
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}
}