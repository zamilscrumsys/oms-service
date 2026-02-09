package com.scrumsys.userservice.repository;
import com.scrumsys.userservice.entity.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long> {
    List<Function> findByNameIn(List<String> names);
}