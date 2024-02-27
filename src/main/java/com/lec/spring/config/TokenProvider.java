package com.lec.spring.config;


import com.lec.spring.repository.TokensRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private Key key;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;


    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long tokenExpiration ,
            @Value("${jwt.refresh-token.expiration}") long refreshTokenExpiration){
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenExpiration;
        this.refreshTokenValidityInMilliseconds = refreshTokenExpiration ;
    }

    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Long userId = null;
        String nickName = "";
        String name = "";

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            userId = userDetails.getId(); // 'userId'를 추출합니다.
            nickName = userDetails.getNickname(); // 'nickName'을 추출합니다.
            name = userDetails.getName(); // 'name'을 추출합니다.
        } else if (principal instanceof String) {
            // 여기서 principal이 String 타입인 경우, 즉 username인 경우의 처리를 할 수 있습니다.
            // 이 예제에서는 username을 사용하지 않으므로 별도의 처리를 하지 않습니다.
            // 필요에 따라 username을 사용하는 로직을 추가할 수 있습니다.
        }

        // 토큰의 만료 시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", userId)
                .claim("nickName", nickName)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    ///리플레쉬
    public String createRefreshToken(Authentication authentication) {
        // 리프레시 토큰의 만료 시간은 액세스 토큰보다 길게 설정
        long  now = (new Date()).getTime();
        Date validityInMilliseconds = new Date(now + this.refreshTokenValidityInMilliseconds);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validityInMilliseconds)
                .compact();
    }






    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {

            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }



    public Authentication getAuthenticationFromRefreshToken(String token) {
        // 토큰에서 클레임을 추출합니다.
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        // 클레임에서 권한 정보를 추출합니다.
        Collection<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 생성합니다.
        User principal = new User(claims.getSubject(), "", authorities);

        // 최종적으로 Authentication 객체를 생성하여 반환합니다.
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }



    ///////////////////////////////////
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

//}
public String getUsernameFromExpiredToken(String token) {
        Claims claims = parseClaims(token);
        System.out.println(claims);
        System.out.println(token);
        return claims.getSubject();

}
}