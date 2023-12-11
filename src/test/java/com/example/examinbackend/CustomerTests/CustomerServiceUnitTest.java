package com.example.examinbackend.CustomerTests;

import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.CustomerRepository;
import com.example.examinbackend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceUnitTest {
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    void shouldGetCustomerById(){
        Customer customerOne = new Customer("Customer 1", "Address 1", "Phone 1");
        Customer customerTwo = new Customer("Customer 2", "Address 2", "Phone 2");
        when(customerRepository.findById(2L)).thenReturn(java.util.Optional.of(customerTwo));
        var customerById = customerService.getCustomerById(2L);
        assert customerById.getCustomerName().equals("Customer 2");
    }

    @Test
    void shouldCreateANewCustomer() {
        Customer customer = new Customer("Customer 1", "Address 1", "Phone 1");
        when(customerRepository.save(customer)).thenReturn(customer);
        var createdCustomer = customerService.createCustomer(customer);
        assert createdCustomer.getCustomerName().equals("Customer 1");
    }

    @Test
    void shouldDeleteANewCustomer() {
        Customer customer = new Customer("Customer 1", "Address 1", "Phone 1");
        customerService.deleteCustomer(1L);
        assert customerService.getAllCustomers().size() == 0;
    }

    @Test
    void shouldGetAllCustomers() {
        List<Customer> listOfCustomers = List.of(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(listOfCustomers);
        var customers = customerService.getAllCustomers();
        assert customers.size() == 3;
    }
}
