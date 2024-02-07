package edu.iastate.coms309.flatfinder.chat;

import jakarta.servlet.ServletContext;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CustomSpringConfigurator extends ServerEndpointConfig.Configurator {
    private static ServletContext servletContext;

    public static void setServletContext(ServletContext context) {
        servletContext = context;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        if (servletContext == null) {
            throw new InstantiationException("ServletContext is not set in CustomSpringConfigurator");
        }
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        return context.getBean(clazz);
    }
}