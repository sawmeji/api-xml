package com.example.apixml.controller;

import com.example.apixml.dao.CustomerDao;
import com.example.apixml.entity.Customer;
import com.example.apixml.entity.Customers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "CustomerController", description = "CustomerController in api-xml Application")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "provide creating new customer")
    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception{
        Customer cus = customerDao.save(customer);
        return ResponseEntity
                .created(new URI("http://localhost:8080/customers/"+ cus.getId()))
                .body(cus);
    }
//curl http://localhost:8080/customers?type=json
/*    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "provide show all customers")
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
    }*/

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "provide updating customer")
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

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "provide finding customer by id")
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



    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "list all customers media format json")
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customers listCustomersJson(){
        return new Customers(customerDao.findAll());
    }

/*
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_XML_VALUE)
    public Customers listCustomersXml(){
        return new Customers(customerDao.findAll());
    }
*/



}
