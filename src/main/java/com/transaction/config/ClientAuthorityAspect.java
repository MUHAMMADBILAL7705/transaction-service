package com.transaction.config;

import com.transaction.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@RequiredArgsConstructor
public class ClientAuthorityAspect {

    private static final String AUTHORIZATION = "token";
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Before("@annotation(checkAuthority)")
    public void checkAuthority(CheckClientAuthority checkAuthority) throws AuthenticationException {
        try {
            String jwtToken = getJWTToken();
            String subject = getSubject(jwtToken);
            if (!subject.equalsIgnoreCase("account-service"))
                throw new AuthenticationException("Unauthorized request");
        }
        catch (Exception ex){
            throw new AuthenticationException("Unauthorized request");
        }
    }

    public String getJWTToken() {
        final ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String headerAuth = requestAttributes.getRequest().getHeader(AUTHORIZATION);
        // Not a servlet request.
        if (headerAuth == null) {
            return null;
        }
        if (org.springframework.util.StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
