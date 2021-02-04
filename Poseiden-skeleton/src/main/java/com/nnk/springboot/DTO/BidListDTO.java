package com.nnk.springboot.DTO;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BidListDTO {

    private Integer Id;
    private String Account;
    private String type;
    private Double bidQuantity;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

}
