package com.nnk.springboot.DTO;


import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
public class BidListDTO {

    private Integer Id;

    @NotNull
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotNull
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotNull(message = "Bid Quantity is mandatory")
    private Double bidQuantity;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
