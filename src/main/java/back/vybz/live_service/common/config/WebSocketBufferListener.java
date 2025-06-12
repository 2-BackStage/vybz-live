package back.vybz.live_service.common.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class WebSocketBufferListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setInitParameter("org.apache.tomcat.websocket.textBufferSize", "524288");
        sce.getServletContext().setInitParameter("org.apache.tomcat.websocket.binaryBufferSize", "524288");
    }
}
