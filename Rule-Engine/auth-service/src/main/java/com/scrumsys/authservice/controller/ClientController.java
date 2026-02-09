package com.scrumsys.authservice.controller;

import com.scrumsys.authservice.dto.request.ClientCreateRequest;
import com.scrumsys.authservice.dto.request.ClientUpdateRequest;
import com.scrumsys.authservice.dto.request.CreateClientRequest;
import com.scrumsys.authservice.dto.response.ClientAppResponse;
import com.scrumsys.authservice.dto.response.ClientResponse;
import com.scrumsys.authservice.service.ClientAppService;
import com.scrumsys.common.controller.BaseController;
import com.scrumsys.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientAppService service;

    @PostMapping
    public ClientResponse create(
            @RequestHeader( value = "X-CLIENT-CREDENTIAL", required = false) String credential,
            @RequestBody ClientCreateRequest request) {

        service.validateClient(credential);   // NEW
        return service.create(credential,request);
    }


    //existing code working
//    @PostMapping
//    public ClientResponse create(
//            @RequestBody ClientCreateRequest request) {
//        return service.create(request);
//    }

    @PutMapping("/{clientId}")
    public ClientResponse update(
            @PathVariable String clientId,
            @RequestBody ClientUpdateRequest request) {
        return service.update(clientId, request);
    }

    @GetMapping("/{clientId}")
    public ClientResponse get(
            @PathVariable String clientId) {
        return service.get(clientId);
    }

    @GetMapping
    public List<ClientResponse> all() {
        return service.getAll();
    }

    @DeleteMapping("/{clientId}")
    public void delete(
            @PathVariable String clientId) {
        service.delete(clientId);
    }
}


