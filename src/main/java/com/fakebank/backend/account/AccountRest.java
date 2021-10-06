package com.fakebank.backend.account;

import com.fakebank.backend.customer.Customer;
import com.fakebank.backend.customer.CustomerRepository;
import com.fakebank.backend.transaction.Transaction;
import com.fakebank.backend.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "accounts")
public class AccountRest {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    TransactionRepository transactionRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account) {
        account.setCustomerId(findCustomer(account.getCustomerId()).getId());
        return accountRepo.save(account);
    }

    @GetMapping
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    @GetMapping("{id}")
    public Account findById(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must not be null");
        }
        var optional = accountRepo.findById(id);
        return optional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found: " + id));
    }

    @PutMapping("{id}")
    public Account update(@PathVariable Long id, @RequestBody Account account) {
        findById(id);
        findCustomer(account.getCustomerId());
        account.setId(id);
        return accountRepo.save(account);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        findById(id);
        accountRepo.deleteById(id);
    }

    private Customer findCustomer(Long customerId) {
        if (customerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customerId must not be null");
        }
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer not found"));
    }

    @GetMapping("{fromId}/transfer-to/{toId}")
    public void transfer(@PathVariable Long fromId, @PathVariable Long toId, @RequestParam BigDecimal amount) {
        var from = findById(fromId);
        var to = findById(toId);
        try {
            from.transferTo(to, amount);
            accountRepo.save(from);
            accountRepo.save(to);
            transactionRepo.save(new Transaction(Transaction.WITHDRAWAL, amount, fromId));
            transactionRepo.save(new Transaction(Transaction.DEPOSIT, amount, toId));
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{id}/amount")
    public BigDecimal amount(@PathVariable Long id) {
        return findById(id).getAmount();
    }

    @GetMapping("{id}/transactions")
    public List<Transaction> transactions(@PathVariable Long id) {
        return findById(id).getTransactions();
    }
}
