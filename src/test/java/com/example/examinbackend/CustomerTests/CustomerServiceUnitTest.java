package com.example.examinbackend.CustomerTests;

import com.example.examinbackend.model.Address;
import com.example.examinbackend.model.Customer;
import com.example.examinbackend.repository.CustomerRepository;
import com.example.examinbackend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceUnitTest {
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    void shouldGetCustomerById(){
        Customer customerOne = new Customer("Customer 1", "Address 1", "Phone 1");
        Customer customerTwo = new Customer("Customer 2", "Address 2", "Phone 2");
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customerTwo));
        var foundCustomer = customerService.getCustomerById(2L);
        assert foundCustomer.get().getCustomerName().equals("Customer 2");
    }
    @Test
    void shouldCreateANewCustomer() {
        Customer customer = new Customer("Customer 1", "Address 1", "Phone 1");
        when(customerRepository.save(customer)).thenReturn(customer);
        var createdCustomer = customerService.createCustomer(customer);
        assert createdCustomer.getCustomerName().equals("Customer 1");
    }
    @Test
    void shouldGetAllCustomers() {
        List<Customer> listOfCustomers = List.of(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(listOfCustomers);
        var customers = customerService.getAllCustomers();
        assert customers.size() == 3;
    }
    @Test
    void shouldDeleteANewCustomerById() {
        Customer customer = new Customer(1L,"Customer 1");
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(1L);
        assert customerService.getAllCustomers().size() == 0;
    }
    @Test
    void shouldUpdateCustomerName() {
        Customer customer = new Customer(1L,"Customer 1");
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        var customerById = customerService.getCustomerById(1L);
        customerById.get().setCustomerName("Customer 2");
        assert customerService.getCustomerById(1L).get().getCustomerName().equals("Customer 2");
    }
    @Test
    void shouldUpdateCustomerEmail(){
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        var customerById = customerService.getCustomerById(1L);
        customerById.get().setEmail("Customer Email");
        assert customerService.getCustomerById(1L).get().getEmail().equals("Customer Email");
    }

    @Test
    void shouldUpdateCustomerPhone(){
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        var customerById = customerService.getCustomerById(1L);
        customerById.get().setPhone("Customer Phone");
        assert customerService.getCustomerById(1L).get().getPhone().equals("Customer Phone");
    }
    @Test
    void shouldAddNewCustomerAddress(){
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        var customerById = customerService.getCustomerById(1L);
        customerById.get().getAddresses().add(new Address("Address 1"));
        customerById.get().getAddresses().add(new Address("Address 2"));
        assert customerService.getCustomerById(1L).get().getAddresses().size() == 2;
    }
    @Test
    void shouldGetAllCustomerAddresses(){
        Customer customer = new Customer();
        customer.setAddresses(List.of(new Address("Address 1"), new Address("Address 2")));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        assert customerService.getCustomerById(1L).get().getAddresses().size() == 2;
    }
}
