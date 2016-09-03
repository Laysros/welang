package com.dac.welang.init;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/contact/thank").setViewName("thank");
        registry.addViewController("/").setViewName("chat_box");
        registry.addViewController("/PageNotFound").setViewName("PageNotFound");
        registry.addViewController("/call_template").setViewName("call_template");
        registry.addViewController("/call").setViewName("call");
        registry.addViewController("/find_friend_header").setViewName("find_friend_header");
        //registry.addViewController("/chat_content").setViewName("chat_content");
        
        //registry.addViewController("/register").setViewName("register");
    }
    
   /* @Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}*/
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**")
                    .addResourceLocations("/resources/");
    }
    

}