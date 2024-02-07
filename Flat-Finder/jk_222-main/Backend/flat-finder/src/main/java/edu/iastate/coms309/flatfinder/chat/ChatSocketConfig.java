package edu.iastate.coms309.flatfinder.chat;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class ChatSocketConfig {
    @Autowired
    private ServletContext servletContext;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        CustomSpringConfigurator.setServletContext(servletContext);
        return new ServerEndpointExporter();
    }
}