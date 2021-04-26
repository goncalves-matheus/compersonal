package compasso.estagio.grupo.projeto5.Telas.security;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import compasso.estagio.grupo.projeto5.Telas.Service.Autenticador;

@Configuration
@EnableWebSecurity
public class Seguranca extends WebSecurityConfigurerAdapter {

	@Autowired
	Autenticador autenticador;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/*.css", "/*.jpeg","/*.js").permitAll()
				.antMatchers("/", "/recuperar/**","/cadastro", "/cadastro/*", "/planos").permitAll()
				.antMatchers("/dashboard/aluno").hasAuthority("Usuario")
				.antMatchers("/dashboard/personal","/inseriraula/**","/alunos").hasAuthority("Personal")
				.antMatchers(HttpMethod.GET,"/mensagem/**").hasAuthority("Personal")
				.antMatchers(HttpMethod.POST,"/mensagem/novaMensagemPersonal").hasAuthority("Personal")
				.antMatchers(HttpMethod.POST,"/mensagem/novaMensagemAluno").hasAuthority("Usuario")
				.anyRequest().authenticated()
				.and()
				.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/", true))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticador).passwordEncoder(new BCryptPasswordEncoder());
	}

}
