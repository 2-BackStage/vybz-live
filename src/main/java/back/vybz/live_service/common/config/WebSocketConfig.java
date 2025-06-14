package back.vybz.live_service.common.config;

import back.vybz.live_service.common.util.ViewerWebSocketHandler;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;

import back.vybz.live_service.common.util.StreamWebSocketHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final StreamWebSocketHandler streamWebSocketHandler;
    private final ViewerWebSocketHandler viewerWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(streamWebSocketHandler, "/ws/stream")
                .setAllowedOrigins("*");

        registry.addHandler(viewerWebSocketHandler, "/ws/viewer")
                .setAllowedOrigins("*");
    }


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(524288);
        container.setMaxBinaryMessageBufferSize(524288);
        return container;
    }
}
