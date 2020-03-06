package com.sirzhangs.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.micrometer.core.instrument.util.StringUtils;

public class TokenFilter extends ZuulFilter{

	private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return "pre"; // 可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

		String authorities = request.getHeader("Authorization");// 获取请求头的参数
		
		if (StringUtils.isNotBlank(authorities)) {
		    ctx.setSendZuulResponse(true); //对请求进行路由
		    ctx.setResponseStatusCode(200);
		    ctx.set("isSuccess", true);
		    return null;
		} else {
		    ctx.setSendZuulResponse(false); //不对其进行路由
		    ctx.setResponseStatusCode(400);
		    ctx.setResponseBody("The resource need to get authenticty");
		    ctx.set("isSuccess", false);
		    return null;
		}
	}

}
