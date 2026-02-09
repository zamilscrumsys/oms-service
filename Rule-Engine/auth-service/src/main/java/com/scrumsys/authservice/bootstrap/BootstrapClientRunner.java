package com.scrumsys.authservice.bootstrap;


import com.scrumsys.authservice.entity.ClientApp;
import com.scrumsys.authservice.repository.ClientAppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapClientRunner implements CommandLineRunner {

    private final ClientAppRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        if (repository.existsByOrgCode("ORG-BOOTSTRAP")) {
            log.info("Bootstrap client already exists");
            return;
        }

        String clientId = UUID.randomUUID().toString();
        String rawSecret = UUID.randomUUID().toString();

        ClientApp client = new ClientApp();
        client.setOrgCode("ORG-BOOTSTRAP");
        client.setOrgName("SYSTEM");
        client.setClientId(clientId);
        client.setClientSecret(encoder.encode(rawSecret));
        client.setClientName("BOOTSTRAP-CLIENT");
        client.setExpiryDate(LocalDate.now().plusYears(10));
        client.setActive(true);
        client.setCreatedBy("SYSTEM");

        repository.save(client);

        log.warn("========================================");
        log.warn("BOOTSTRAP CLIENT CREATED");
        log.warn("CLIENT ID     : {}", clientId);
        log.warn("CLIENT SECRET : {}", client.getClientSecret());
        log.warn("HEADER FORMAT : {}:{}", clientId, client.getClientSecret());
        log.warn("========================================");
    }
}

