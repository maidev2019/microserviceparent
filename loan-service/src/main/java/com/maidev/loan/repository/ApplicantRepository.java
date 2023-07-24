package com.maidev.loan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maidev.loan.dto.ApplicantRequest;
import com.maidev.loan.model.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant,Long>{
	@Query("select a from Applicant a where a.firstname = :#{#req.firstname} and a.lastname = :#{#req.lastname} and a.email = :#{#req.email}")
    Optional<Applicant> findApplicant(@Param("req") ApplicantRequest req);
}