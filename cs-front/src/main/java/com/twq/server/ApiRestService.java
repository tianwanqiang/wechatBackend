package com.twq.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/services/api")
public interface ApiRestService {
    String callingApi(HttpServletRequest request, HttpServletResponse response) ;
}
