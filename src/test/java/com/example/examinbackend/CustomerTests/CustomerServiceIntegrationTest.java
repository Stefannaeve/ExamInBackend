package com.example.examinbackend.CustomerTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.CustomerRepository;
import com.example.examinbackend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceIntegrationTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    void shouldGetCustomerById(){
        Customer customerTwo = new Customer("Customer 2", "Address 2", "Phone 2");
        Customer savedCustomer = customerRepository.save(customerTwo);
        var foundCustomer = customerService.getCustomerById(savedCustomer.getId());
        assert foundCustomer.get().getCustomerName().equals("Customer 2");
    }
    @Test
    @Transactional
    void shouldCreateANewCustomer() {
        Customer customer = new Customer("Customer 1", "Address 1", "Phone 1");
        Customer savedCustomer = customerRepository.save(customer);
        var createdCustomer = customerService.createCustomer(savedCustomer);
        assert createdCustomer.getCustomerName().equals("Customer 1");
    }
    @Test
    @Transactional
    void shouldGetAllCustomers() {
        Customer customerOne = new Customer("Customer 1", "Address 1", "Phone 1");
        Customer customerTwo = new Customer("Customer 2", "Address 2", "Phone 2");
        customerRepository.save(customerOne);
        customerRepository.save(customerTwo);
        var customers = customerService.getAllCustomers();
        assert customers.size() == 2;
    }
    @Test
    @Transactional
    void shouldDeleteCustomerById(){
        Customer customer = new Customer("Customer 1");
        customerRepository.save(customer);
        customerService.deleteCustomer(customer.getId());
        assert customerService.getAllCustomers().size() == 0;
    }
    @Test
    @Transactional
    void shouldUpdateCustomerName() {
        Customer customer = new Customer("Customer 1");
        Customer savedCustomer = customerRepository.save(customer);
        var customerById = customerService.getCustomerById(savedCustomer.getId());
        customerById.get().setCustomerName("New name");
        assert customerById.get().getCustomerName().equals("New name");
    }
    @Test
    @Transactional
    void shouldUpdateCustomerEmail(){
        Customer customer = new Customer("Customer 1");
        Customer savedCustomer = customerRepository.save(customer);
        var customerById = customerService.getCustomerById(savedCustomer.getId());
        customerById.get().setEmail("New email");
        assert customerById.get().getEmail().equals("New email");
    }
    @Test
    @Transactional
    void shouldUpdateCustomerPhone(){
        Customer customer = new Customer("Customer 1");
        Customer savedCustomer = customerRepository.save(customer);
        var customerById = customerService.getCustomerById(savedCustomer.getId());
        customerById.get().setPhone("New phone");
        assert customerById.get().getPhone().equals("New phone");
    }
    @Test
    @Transactional
    void shouldAddCustomerAddress(){
        Customer customer = new Customer("Customer 1");
        Customer savedCustomer = customerRepository.save(customer);
        var customerById = customerService.getCustomerById(savedCustomer.getId());
        Address address = new Address("Address 1");
        Address address2 = new Address("Address 2");
        customerById.get().getAddresses().add(address);
        customerById.get().getAddresses().add(address2);
        assert customerById.get().getAddresses().size() == 2;
    }
    @Test
    @Transactional
    void shouldGetAllCustomerAddresses(){
        Customer customer = new Customer("Customer 1");
        Customer savedCustomer = customerRepository.save(customer);
        var customerById = customerService.getCustomerById(savedCustomer.getId());
        Address address = new Address("Address 1");
        customerById.get().getAddresses().add(address);
        assert customerById.get().getAddresses().size() == 1;
    }
}
