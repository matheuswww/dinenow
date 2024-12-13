package com.github.matheuswwwp.dinenow.conf.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);
    @Autowired
    private JwtTokenProvider jwtProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String token = extractTokenFromUrl(request.getURI().toString());
            if (token == null || !jwtProvider.validateToken(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
            var clamis = jwtProvider.getClaimFromToken(token);
            if(!clamis.getRoles().contains("ADMIN")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("beforeHandShake - error trying beforeHandshake: {}", e.getMessage());
            return false;
        }
    }

    private String extractTokenFromUrl(String url) {
        String[] urlParts = url.split("\\?");
        if (urlParts.length < 2) {
            return null;
        }
        String query = urlParts[1];
        if (query.startsWith("token=")) {
            return query.length() > 6 ? query.substring(6) : null;
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
