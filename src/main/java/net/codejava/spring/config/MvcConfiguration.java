package net.codejava.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import net.codejava.spring.dao.SearchAnythingDAO;
import net.codejava.spring.dao.SearchAnythingDAOImpl;
import net.codejava.spring.dao.SearchAnythingFormDAO;
import net.codejava.spring.dao.SearchAnythingFormDAOImpl;

@Configuration
@ComponentScan(basePackages="net.codejava.spring")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
/*		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/contactdb");
		dataSource.setUsername("root");
		dataSource.setPassword("P@ssw0rd");*/
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		//dataSource.setUrl("jdbc:mysql://localhost:3306/contactdb");
		dataSource.setUrl("jdbc:mysql://localhost:3306/searching");
		dataSource.setUsername("root");
		dataSource.setPassword("");

		return dataSource;
	}

	@Bean
	public SearchAnythingDAO getSearchAnythingDAO() {
		return new SearchAnythingDAOImpl(getDataSource());
	}

	@Bean
	public SearchAnythingFormDAO getSearchAnythingFormDAO() {
		return new SearchAnythingFormDAOImpl(getDataSource());
	}
	/*@Bean
	public ContactDAO getContactDAO() {
		return new ContactDAOImpl(getDataSource());
	}

	@Bean
	public LearnJapanDAO getLearnJapanDAO() {
		return new LearnJapanDAOImpl(getDataSource());
	}*/
}
