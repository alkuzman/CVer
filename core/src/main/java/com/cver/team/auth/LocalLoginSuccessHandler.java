package com.cver.team.auth;

import com.cver.team.config.StaticConstants;
import com.cver.team.filters.helper.CsrfCookieAddingHelper;
import com.cver.team.model.entity.Person;
import com.cver.team.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dimitar on 6/3/2016.
 */
@Component
public class LocalLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    PersonService personService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        System.out.println("Successful login!");

        String name = authentication.getName();
        Person user = personService.getPersonByLoginEmail(name);
        httpServletRequest.getSession().setAttribute("user",user);

        httpServletResponse.addHeader("Access-Control-Allow-Origin", StaticConstants.ANGULAR_APP_URL);
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        CsrfCookieAddingHelper.addingCSRFasCookie(httpServletRequest,httpServletResponse);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        // super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);

    }
}