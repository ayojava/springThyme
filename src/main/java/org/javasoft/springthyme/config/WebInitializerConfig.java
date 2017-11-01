/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author ayojava
 */
@Configuration
public class WebInitializerConfig implements WebApplicationInitializer{
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "localhost");
        //servletContext.setInitParameter("spring.profiles.active", "openshift");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ThymeleafConfig.class);
        context.setServletContext(servletContext);
        // Spring MVC front controller
        Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
}
}

/*
    http://nixmash.com/post/adding-bootswatch-webjar-in-spring-and-thymeleaf
http://www.nakov.com/blog/2016/08/05/creating-a-blog-system-with-spring-mvc-thymeleaf-jpa-and-mysql/
https://ultraq.github.io/thymeleaf-layout-dialect/Examples.html#layouts
https://qtzar.com/2017/03/22/reuse-more-code-with-thymeleaf-layouts/
http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-layout
https://medium.com/@trevormydata/week-5-thymeleaf-with-spring-mvc-rapid-introduction-to-the-essentials-799f1fba8c07

http://www.baeldung.com/spring-thymeleaf-3-expressions
http://www.baeldung.com/thymeleaf-spring-layouts
http://www.baeldung.com/dates-in-thymeleaf

*/