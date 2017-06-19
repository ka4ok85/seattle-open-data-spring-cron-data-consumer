package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Call;

public interface CallRepository extends JpaRepository<Call, Long> {

	Call findTopByOrderByDatetimeDesc();
	Call findByIncidentNumber(String incidentNumber);
}
