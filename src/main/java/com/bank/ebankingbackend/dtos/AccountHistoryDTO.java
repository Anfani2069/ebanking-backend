package com.bank.ebankingbackend.dtos;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

//@Data pour les getters et setters
@Data
public class AccountHistoryDTO {

    private int currentPage;
    private int totalPages;
    private int pageSize;
    private String accountId;
    private double balance;
    private List<AccountOperationDTO> accountOperationDTOS;
}
