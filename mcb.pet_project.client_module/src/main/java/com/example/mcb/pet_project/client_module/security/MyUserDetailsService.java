package com.example.mcb.pet_project.client_module.security;

import com.example.mcb.pet_project.client_module.entities.Client;
import com.example.mcb.pet_project.client_module.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = Optional.ofNullable(clientRepository.getClientsByLogin(username));

        return client.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(client + " not found"));
    }
}