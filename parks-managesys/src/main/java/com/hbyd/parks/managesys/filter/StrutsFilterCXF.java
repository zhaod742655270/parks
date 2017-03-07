package com.hbyd.parks.managesys.filter;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 由于 Struts 拦截了 WebService 的访问请求, 导致无法访问 WebServie.
 * 该类重写 StrutsPrepareAndExecuteFilter，将 WebService 请求转交给 CXFServlet 处理
 */
public class StrutsFilterCXF extends StrutsPrepareAndExecuteFilter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
//		Struts 2 拦截了 WebService 的访问请求： /ws/*
		String uri = ((HttpServletRequest)req).getRequestURI();
		
		if(uri.indexOf("ws")!=-1){//如果访问 WebService, 调用 Filter 接口的 doFilter
			chain.doFilter(req, resp);
		}else{//转交给 StrutsPrepareAndExecuteFilter
			super.doFilter(req, resp, chain);
		}
	}
}
