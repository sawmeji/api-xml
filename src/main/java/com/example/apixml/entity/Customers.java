package com.example.apixml.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@JacksonXmlRootElement(localName = "customers")
public class Customers {

    @JacksonXmlProperty(localName = "customer")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Customer> customers;

    public Customers(Iterable<Customer> customers){
        this.customers = StreamSupport.stream(
                customers.spliterator(), false
        ).collect(Collectors.toList());
    }
}
