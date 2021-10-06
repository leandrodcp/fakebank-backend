package com.fakebank.backend.customer;

import com.fakebank.backend.account.Account;
import com.fakebank.backend.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "customers")
public class CustomerRest {

    @Autowired
    CustomerRepository customerRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @GetMapping("{id}")
    public Customer findById(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must not be null");
        }
        var optional = customerRepo.findById(id);
        return optional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found: " + id));
    }

    @PutMapping("{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        var old = findById(id);
        customer.setId(id);
        customer.setAccounts(old.getAccounts());
        return customerRepo.save(customer);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        findById(id);
        customerRepo.deleteById(id);
    }
}
