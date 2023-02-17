package com.example.apixml.dao;

import com.example.apixml.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDao extends CrudRepository<Customer, Integer> {
}
