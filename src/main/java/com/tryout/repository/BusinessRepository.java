package com.tryout.repository;


import com.tryout.entities.Business;
import com.tryout.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BusinessRepository extends JpaRepository<Business, Long> {

}
