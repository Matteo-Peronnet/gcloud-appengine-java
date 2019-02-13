package com.zenika.zencontact.resource;

import com.google.gson.Gson;
import com.zenika.zencontact.domain.Email;
import com.zenika.zencontact.email.EmailService;

import javax.servlet.*;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmailToSendResource", value = "/api/v0/email")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin"}))
public class EmailToSendResource extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Email email = new Gson().fromJson(request.getReader(), Email.class);
        EmailService.getInstance().sendEmail(email);
    }
}
