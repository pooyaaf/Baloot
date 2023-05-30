package Baloot.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class jwtAuthFilter implements Filter {

    private static String SECRET_KEY = "baloot2023";

    private static final ArrayList<String> excludedUrls = new ArrayList<>() {
        {
            add("login");
            add("register");
            add("auth");
        }
    };

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String[] path = ((HttpServletRequest) servletRequest).getRequestURI().split("/");
        if (path.length > 1 && excludedUrls.contains(path[1])) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String header = httpRequest.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            return;
        }

        String token = header.substring(7);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            String issuer = claims.getBody().getIssuer();
            System.out.println(issuer);
            Date expiration = claims.getBody().getExpiration();
            if (expiration.before(new Date())) {
                throw new IllegalArgumentException("Token has expired");
            }
            String userId = claims.getBody().get("userId", String.class);
            servletRequest.setAttribute("userId", userId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}


