package com.nnk.springboot.DTO;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TradeDTO {

    private Integer tradeId;

    @NotNull
    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotNull
    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotNull(message = "Bid Quantity is mandatory")
    private Double buyQuantity;

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
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

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
}
