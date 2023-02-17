package com.example.apixml.controller;

import com.example.apixml.dao.CustomerDao;
import com.example.apixml.entity.Customer;
import com.example.apixml.entity.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;

    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception{
        Customer cus = customerDao.save(customer);
        return ResponseEntity
                .created(new URI("http://localhost:8080/customers/"+ cus.getId()))
                .body(cus);
    }
//curl http://localhost:8080/customers?type=json
    @GetMapping("/customers")
    public ResponseEntity<Customers> listCustomersXMLorJson(@RequestParam("type") String type){
        if(type.equalsIgnoreCase("xml")){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(new Customers(customerDao.findAll()));
        }
        if(type.equalsIgnoreCase("json")){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Customers(customerDao.findAll()));
        }
        return null;
    }

    @Transactional
    @PutMapping(value = "/customers/customer",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Customer> updateCustomer(@RequestParam("id")int id,
                                                   @RequestBody Customer customer){
        if(customerDao.existsById(id)){
            Customer existingCustomer = customerDao.findById(id).get();
            existingCustomer.setId(customer.getId());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
//            customerDao.save(existingCustomer);
            return ResponseEntity.ok(existingCustomer);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customers/customer")
    public ResponseEntity<Customer> findCustomerById(@RequestParam("id")int id,
                                                     @RequestParam("type") String type){
        /*Customer c = customerDao.findById(id).get();
        return ResponseEntity.ok(c);*/
        if(type.equalsIgnoreCase("xml")){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(customerDao.findById(id).get());
        }
        if(type.equalsIgnoreCase("json")){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customerDao.findById(id).get());
        }
        return null;
    }



    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customers listCustomersJson(){
        return new Customers(customerDao.findAll());
    }

    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_XML_VALUE)
    public Customers listCustomersXml(){
        return new Customers(customerDao.findAll());
    }



}
