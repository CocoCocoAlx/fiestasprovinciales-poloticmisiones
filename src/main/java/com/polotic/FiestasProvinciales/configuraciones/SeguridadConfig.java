package com.polotic.FiestasProvinciales.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.polotic.FiestasProvinciales.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConfig {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/css/*", "/js/*", "/fonts/*", "/images/*", "/images/*/*", "/", "/registro", "/fiestas")
                .permitAll()
                .and()
                // .authorizeHttpRequests().antMatchers("/fiestas")
                // .hasAnyRole("Administrador", "Usuario")
                // .and()
                .authorizeHttpRequests().antMatchers("/fiestas/agregar")
                .hasRole("Administrador")
                .and()
                .authorizeHttpRequests().antMatchers("/fiestas/editar/*")
                .hasRole("Administrador")
                .and()
                .authorizeHttpRequests().antMatchers("/artistas")
                .hasAnyRole("Administrador", "Usuario")
                .and()
                .authorizeHttpRequests().antMatchers("/artistas/agregar")
                .hasRole("Administrador")
                .and()
                .authorizeHttpRequests().antMatchers("/artistas/editar/*")
                .hasRole("Administrador")
                .and()
                .authorizeHttpRequests().antMatchers("/predios")
                .hasAnyRole("Administrador", "Usuario")
                .and()
                .authorizeHttpRequests().antMatchers("/predios/agregar")
                .hasRole("Administrador")
                .and()
                .authorizeHttpRequests().antMatchers("/predios/editar/*")
                .hasRole("Administrador")
                //faltan agregar los formularios de Provincias y Localidades
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/logincheck")
                .usernameParameter("correo").passwordParameter("clave")
                .defaultSuccessUrl("/loginSuccess").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();

        return http.build();
    }

}