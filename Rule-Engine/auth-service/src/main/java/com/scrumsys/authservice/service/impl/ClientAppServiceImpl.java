package com.scrumsys.authservice.service.impl;


import com.scrumsys.authservice.dto.request.ClientCreateRequest;
import com.scrumsys.authservice.dto.request.ClientUpdateRequest;
import com.scrumsys.authservice.dto.response.ClientResponse;
import com.scrumsys.authservice.entity.ClientApp;
import com.scrumsys.authservice.repository.ClientAppRepository;
import com.scrumsys.authservice.service.ClientAppService;
import com.scrumsys.common.dto.ClientAppDTO;
import com.scrumsys.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientAppServiceImpl
        implements ClientAppService {

    private final ClientAppRepository repository;
    private final PasswordEncoder encoder;

    // ================= CREATE =================

    @Override
    public ClientResponse create(String credential,ClientCreateRequest req) {


        //  1. Check header first
        if (credential == null || credential.isBlank()) {
            throw new ApplicationException
                    .UnauthorizedException("Missing client credential");
        }

        //  2. Validate client
        validateClient(credential);
        if (repository.existsByClientName(req.getClientName())) {
            throw new ApplicationException
                    .BadRequestException("Client name already exists");
        }

        if (repository.existsByOrgName(req.getOrgName())) {
            throw new ApplicationException
                    .BadRequestException("Organization already exists");
        }

        if (repository.existsByOrgCode(req.getOrgCode())) {
            throw new RuntimeException("Org code already exists");
        }




        String clientId = UUID.randomUUID().toString();
        String rawSecret = UUID.randomUUID().toString();

        String encodedSecret = encoder.encode(rawSecret);

        String orgCode = generateOrgCode();







        ClientApp client = new ClientApp();
        client.setOrgCode(orgCode);
        client.setOrgName(req.getOrgName());
        client.setClientId(clientId);
        client.setClientSecret(encodedSecret);
        client.setClientName(req.getClientName());
        client.setIpAddress(req.getIpAddress());
        client.setExpiryDate(req.getExpiryDate());
        client.setAdditionalInfo(req.getAdditionalInfo());
        client.setSubscriptionFee(req.getSubscriptionFee());
        client.setCreatedBy("SYSTEM");

        repository.save(client);



        return ClientResponse.builder()
                .orgCode(client.getOrgCode())
                .clientId(clientId)
                .clientSecret(client.getClientSecret()) // ONCE
                //.encodedCredential(client.getClientSecret())
                .clientName(client.getClientName())
                .orgName(client.getOrgName())
                .expiryDate(client.getExpiryDate())
                .active(true)
                .build();
    }

    // ================= UPDATE =================

    @Override
    public ClientResponse update(String clientId,
                                 ClientUpdateRequest req) {

        ClientApp client = repository.findByClientId(clientId)
                .orElseThrow(() ->
                        new RuntimeException("Client not found"));

        client.setOrgName(req.getOrgName());
        client.setClientName(req.getClientName());
        client.setIpAddress(req.getIpAddress());
        client.setExpiryDate(req.getExpiryDate());
        client.setAdditionalInfo(req.getAdditionalInfo());
        client.setSubscriptionFee(req.getSubscriptionFee());
        client.setActive(req.getActive());

        repository.save(client);

        return map(client);
    }

    // ================= GET =================

    @Override
    public ClientResponse get(String clientId) {
        return map(
                repository.findByClientId(clientId)
                        .orElseThrow()
        );
    }

    @Override
    public List<ClientResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    // ================= DELETE =================

    @Override
    public void delete(String clientId) {
        ClientApp client = repository.findByClientId(clientId)
                .orElseThrow();
        client.setActive(false);
        repository.save(client);
    }

    // ================= VALIDATION =================

    @Override
    public void validateClient(String clientCredential) {

        //  1. Check header first
        if (clientCredential == null || clientCredential.isBlank()) {
            throw new ApplicationException
                    .UnauthorizedException("Missing client credential");
        }

        // Expect: clientId:encodedSecret
        String[] parts = clientCredential.split(":");

        if (parts.length != 2) {
            throw new ApplicationException
                    .UnauthorizedException("Invalid client credential format");
        }

        String clientId = parts[0];
        String encodedSecretFromHeader = parts[1];

        ClientApp client =
                repository.findByClientId(clientId)
                        .orElseThrow(() ->
                                new ApplicationException
                                        .UnauthorizedException("Invalid clientId"));

        if (!encodedSecretFromHeader
                .equals(client.getClientSecret())) {

            throw new ApplicationException
                    .UnauthorizedException("Invalid clientSecret");
        }

        if (!client.getActive()) {
            throw new ApplicationException
                    .UnauthorizedException("Client inactive");
        }
    }


    private String generateOrgCode() {

        long count = repository.count() + 1;

        return "ORG-" + LocalDate.now().getYear()
                + "-" + String.format("%06d", count);
    }


    // ================= MAPPER =================

    private ClientResponse map(ClientApp c) {

        return ClientResponse.builder()
                .orgCode(c.getOrgCode())
                .clientId(c.getClientId())
                .clientName(c.getClientName())
                .orgName(c.getOrgName())
                .expiryDate(c.getExpiryDate())
                .active(c.getActive())
                .build();
    }
}
