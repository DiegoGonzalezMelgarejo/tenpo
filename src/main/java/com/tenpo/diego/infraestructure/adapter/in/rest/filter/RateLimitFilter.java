package com.tenpo.diego.infraestructure.adapter.in.rest.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.MessageError;
import org.springframework.http.HttpStatus;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 3;
    private static final long TIME_WINDOW = 60 * 1000L;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConcurrentHashMap<String, List<Long>> requestLogs = new ConcurrentHashMap<>();



    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        List<Long> timestamps = requestLogs.computeIfAbsent(clientIp, k -> new ArrayList<>());
        long currentTime = System.currentTimeMillis();
        Iterator<Long> iterator = timestamps.iterator();
        while (iterator.hasNext()) {
            if (currentTime - iterator.next() > TIME_WINDOW) {
                iterator.remove();
            }
        }

        if (timestamps.size() >= MAX_REQUESTS) {
            String errorJson = objectMapper.writeValueAsString(MessageError.builder().message("Too many requests. Please try again later.").build());  // Serializar a JSON
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(errorJson);
        } else {

            timestamps.add(currentTime);
            filterChain.doFilter(request, response);
        }
    }
}