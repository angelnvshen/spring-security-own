package com.apress.pss.filter;

import com.apress.pss.common.util.HeaderUtil;
import com.apress.pss.security.transfer.JwtUserDto;
import com.apress.pss.security.util.JwtTokenValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by CHANEL on 2017/2/19.
 */
@Component
public class PaseJwtTokenFilter extends OncePerRequestFilter {
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println(url);
        if(url.indexOf("admin") > 0){
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Basic ")) {
                System.out.println("no token ");
                return ;
            }
            JwtTokenValidator jwtTokenValidator = new JwtTokenValidator();
            String authToken = header.substring(6);
            JwtUserDto parsedUser = jwtTokenValidator.parseToken(authToken);
            System.out.println(parsedUser.getId());
            System.out.println(parsedUser.getUsername());
            System.out.println(parsedUser.getRole());
            String new_token = HeaderUtil.encodeByBase64("car:scarvarez");

            HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(request);
            wrapper.addHeader("Authorization", new_token);

            filterChain.doFilter(wrapper, response);
        }else {
            filterChain.doFilter(request, response);
        }
    }

    public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
        /**
         * construct a wrapper for this request
         *
         * @param request
         */
        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        private Map<String, String> headerMap = new HashMap<String, String>();

        /**
         * add a header with given name and value
         *
         * @param name
         * @param value
         */
        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        /**
         * get the Header names
         */
        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                names.add(name);
            }
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values.add(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }

    }
}
