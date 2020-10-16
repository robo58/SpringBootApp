package com.example.webapp.repository;

import com.example.webapp.model.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{
    
}
