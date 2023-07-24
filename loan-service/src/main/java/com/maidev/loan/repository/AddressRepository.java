package com.maidev.loan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maidev.loan.dto.AddressRequest;
import com.maidev.loan.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    @Query("select a from Address a where a.street = :#{#req.street} and a.streetAdditionalLine = :#{#req.streetAdditionalLine} and a.city = :#{#req.city} and a.state = :#{#req.state} and a.postalcode = :#{#req.postalcode}")
    Optional<Address> findAddressRequest(@Param("req") AddressRequest req);
}

