package com.natal.subscriptionservice.infrastructure.repository;

import com.natal.subscriptionservice.infrastructure.entity.AddressEntity;
import com.natal.subscriptionservice.infrastructure.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
