package com.example.mcb.pet_project.client_module.security;

import com.example.mcb.pet_project.client_module.entities.Client;
import com.example.mcb.pet_project.client_module.repositories.ClientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Client client = clientRepository.getClientsByLogin(authentication.getName());

        if (client.getRole().equals("ADMIN")) {
            response.sendRedirect("/clients/admin");
        } else {
            response.sendRedirect("/clients/user/" + client.getId());
        }
    }
}