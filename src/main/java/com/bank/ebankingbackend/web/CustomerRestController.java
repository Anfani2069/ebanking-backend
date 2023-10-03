package com.bank.ebankingbackend.web;

import com.bank.ebankingbackend.dtos.CustomerDTO;
import com.bank.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bank.ebankingbackend.service.BankAccountService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/e-bank/")
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomer();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword){


        return bankAccountService.searchCustomer("%" + keyword + "%");
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customer/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
    customerDTO.setId(customerId);
    return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customer/{Id}")
    public void deleteCustomer(@PathVariable Long Id){
        bankAccountService.deleteCustomer(Id);
    }
}
