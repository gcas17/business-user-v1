package com.nisum.repository;

import com.nisum.model.Phone;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PhoneRepository extends ReactiveCrudRepository<Phone, Long> {
  Flux<Phone> findByUserId(Long userId);
}
