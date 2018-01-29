package com.twq.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 云动路由接口
 */
public interface ApiRestService {
    String callingApi(HttpServletRequest request, HttpServletResponse response);
}

