package com.vienna.jaray.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.vienna.jaray.security.GrantedAuthorityImpl;
import com.vienna.jaray.security.JwtAuthenticatioToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: jwtToken工具类
 */
public class JwtTokenUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名称
	 */
	private static final String USERNAME = Claims.SUBJECT;
	/**
	 * 创建时间
	 */
	private static final String CREATED = "created";
	/**
	 * 权限列表
	 */
	private static final String AUTHORITIES = "authorities";
	/**
     * 密钥
     */
    private static final String SECRET = "jaray";
    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
	 * 生成令牌
	 * @return
	 */
	public static String generateToken(Authentication authentication) {
	    Map<String, Object> claims = new HashMap<>(3);
	    claims.put(USERNAME, SecurityUtil.getUsername(authentication));
	    claims.put(CREATED, new Date());
	    claims.put(AUTHORITIES, authentication.getAuthorities());
	    return generateToken(claims);
	}

	/**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public static String getUsernameFromToken(String token) {
	    String username;
	    try {
	        Claims claims = getClaimsFromToken(token);
	        username = claims.getSubject();
	    } catch (Exception e) {
	        username = null;
	    }
	    return username;
	}
	
	/**
	 * 根据请求令牌获取登录认证信息
	 * @return
	 */
	public static Authentication getAuthenticationeFromToken(HttpServletRequest request) {
		Authentication authentication = null;
		// 获取请求携带的令牌
		String token = JwtTokenUtil.getToken(request);
		if(token != null) {
			// 请求令牌不能为空
			if(SecurityUtil.getAuthentication() == null) {
				// 上下文中Authentication为空
				Claims claims = getClaimsFromToken(token);
				if(claims == null) {
					return null;
				}
				String username = claims.getSubject();
				if(username == null) {
					return null;
				}
				if(isTokenExpired(token)) {
					return null;
				}
				Object authors = claims.get(AUTHORITIES);
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				if (authors != null && authors instanceof List) {
					for (Object object : (List<?>) authors) {
						authorities.add(new GrantedAuthorityImpl((String) ((Map<?, ?>) object).get("authority")));
					}
				}
				authentication = new JwtAuthenticatioToken(username, null, authorities, token);
			} else {
				if(validateToken(token, SecurityUtil.getUsername())) {
					// 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
					authentication = SecurityUtil.getAuthentication();
				}
			}
		}
		return authentication;
	}

	/**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
	 * 验证令牌
	 * @param token
	 * @param username
	 * @return
	 */
	public static Boolean validateToken(String token, String username) {
	    String userName = getUsernameFromToken(token);
	    return (userName.equals(username) && !isTokenExpired(token));
	}

	/**
	 * 刷新令牌
	 * @param token
	 * @return
	 */
	public static String refreshToken(String token) {
	    String refreshedToken;
	    try {
	        Claims claims = getClaimsFromToken(token);
	        claims.put(CREATED, new Date());
	        refreshedToken = generateToken(claims);
	    } catch (Exception e) {
	        refreshedToken = null;
	    }
	    return refreshedToken;
	}

	/**
	 * 令牌过期时间
	 *
	 * @param token 令牌
	 * @return 过期时间
	 */
	public static String tokenExpireTime(String token) {
		Claims claims = getClaimsFromToken(token);
		Date expiration = claims.getExpiration();
		return DateTimeUtil.parseDateToString(expiration, DateTimeUtil.DATE_FORMAT_FULL);
	}

	/**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取请求token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
    	String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if(token == null) {
        	token = request.getHeader("token");
        } else if(token.contains(tokenHead)){
        	token = token.substring(tokenHead.length());
        } 
        if("".equals(token)) {
        	token = null;
        }
        return token;
    }

    public static String validateAndrefreshToken(String token){
			Claims claims = getClaimsFromToken(token);
			if(claims == null){
				return null;
			}
			Date expiration = claims.getExpiration();
			Date beforeDate = DateTimeUtil.parseStringToDate(DateTimeUtil.beforeNminsDate(DateTimeUtil.parseDateToString(expiration), 3, DateTimeUtil.DATE_FORMAT_FULL),DateTimeUtil.DATE_FORMAT_FULL);
			Date curDate = DateTimeUtil.parseStringToDate(DateTimeUtil.getCurDateTime(DateTimeUtil.DATE_FORMAT_FULL), DateTimeUtil.DATE_FORMAT_FULL);
			boolean isAfter = curDate.after(beforeDate);
			boolean isBefore = curDate.before(expiration);
			// 如果即将过期，刷新token
			if(isBefore && isAfter){
				token = refreshToken(token);
			}
			return token;
	}

}
