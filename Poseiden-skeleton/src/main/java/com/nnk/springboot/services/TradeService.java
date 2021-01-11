package com.nnk.springboot.services;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;

import java.util.List;

public interface TradeService {

    public List<TradeDTO> tradeList();

    public void addTrade(TradeDTO tradeDTO);

    public void updateTrade(TradeDTO tradeDTO);

    public void deleteTrade(TradeDTO tradeDTO);

    public TradeDTO checkTrade(Integer tradeId);
}
