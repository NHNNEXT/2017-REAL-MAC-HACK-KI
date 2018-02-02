package com.amigotrip.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by NEXT on 2017. 12. 27..
 */
@Component
@Slf4j
public class SpringSecurityHelper implements Helper<Object> {

    public static final String NAME = "authorize";

    @Autowired
    private ApplicationContext appContext;

    @Override
    public Object apply(final Object context, final Options options) throws IOException {
        Options.Buffer buffer = options.buffer();
        if (authorizeUsingAccessExpression(context.toString())) {
            buffer.append(options.fn());
        } else {
            buffer.append(options.inverse());
        }
        return buffer;
    }

    private boolean authorizeUsingAccessExpression(String access) throws IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return false;
        }

        SecurityExpressionHandler<FilterInvocation> handler = getExpressionHandler();

        Expression accessExpression;
        try {
            accessExpression = handler.getExpressionParser().parseExpression(access);

        } catch (ParseException e) {
            IOException ioException = new IOException();
            ioException.initCause(e);
            throw ioException;
        }

        return ExpressionUtils.evaluateAsBoolean(accessExpression, createExpressionEvaluationContext(handler));
    }

    protected EvaluationContext createExpressionEvaluationContext(SecurityExpressionHandler<FilterInvocation> handler) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        FilterInvocation f = new FilterInvocation(sra.getRequest(), sra.getResponse(), new FilterChain() {
            public void doFilter(ServletRequest request, ServletResponse response)
                    throws IOException, ServletException {
                throw new UnsupportedOperationException();
            }
        });

        return handler.createEvaluationContext(SecurityContextHolder.getContext().getAuthentication(), f);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private SecurityExpressionHandler<FilterInvocation> getExpressionHandler() throws IOException {
        Map<String, SecurityExpressionHandler> handlers = appContext.getBeansOfType(SecurityExpressionHandler.class);

        for (SecurityExpressionHandler h : handlers.values()) {
            if (FilterInvocation.class
                    .equals(GenericTypeResolver.resolveTypeArgument(h.getClass(), SecurityExpressionHandler.class))) {
                return h;
            }
        }

        throw new IOException("No visible WebSecurityExpressionHandler instance could be found in the application "
                + "context. There must be at least one in order to support expressions in JSP 'authorize' tags.");
    }
}
