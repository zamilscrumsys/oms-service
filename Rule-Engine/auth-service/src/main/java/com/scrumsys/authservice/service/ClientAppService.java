package com.scrumsys.authservice.service;

import com.scrumsys.authservice.dto.request.ClientCreateRequest;
import com.scrumsys.authservice.dto.request.ClientUpdateRequest;
import com.scrumsys.authservice.dto.request.CreateClientRequest;
import com.scrumsys.authservice.dto.response.ClientAppResponse;
import com.scrumsys.authservice.dto.response.ClientResponse;
import com.scrumsys.authservice.entity.ClientApp;
import com.scrumsys.common.dto.ClientAppDTO;
import com.scrumsys.common.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientAppService {


    // this is last new added


        ClientResponse create(String credential,ClientCreateRequest request);

        ClientResponse update(String clientId,
                              ClientUpdateRequest request);

        ClientResponse get(String clientId);

        List<ClientResponse> getAll();

        void delete(String clientId);

        void validateClient(String encodedCredential);
    }




