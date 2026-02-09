package com.scrumsys.authservice.repository;

import com.scrumsys.authservice.entity.ClientApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientAppRepository extends JpaRepository<ClientApp, Long> {


    Optional<ClientApp> findByClientId(String clientId);

    Optional<ClientApp> findByOrgCode(String orgCode);

    boolean existsByClientId(String clientId);

    boolean existsByOrgCode(String orgCode);

    boolean existsByClientName(String clientName);

    boolean existsByOrgName(String orgName);

}