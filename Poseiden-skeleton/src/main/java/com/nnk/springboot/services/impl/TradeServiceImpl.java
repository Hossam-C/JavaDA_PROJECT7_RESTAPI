package com.nnk.springboot.services.impl;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<TradeDTO> tradeList(){

        List<TradeDTO>  tradeListDTO = new ArrayList<>();

        List<Trade> tradeList = tradeRepository.findAll();

        for ( Trade tlist : tradeList) {
            TradeDTO tradeDTO = new TradeDTO();
            tradeDTO.setTradeId(tlist.getTradeId());
            tradeDTO.setAccount(tlist.getAccount());
            tradeDTO.setType(tlist.getType());
            tradeDTO.setBuyQuantity(tlist.getBuyQuantity());

            tradeListDTO.add(tradeDTO);
        }


        return tradeListDTO;
    }

    @Override
    public void addTrade(TradeDTO tradeDTO){

        Trade trade = new Trade();
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());

        tradeRepository.save(trade);

    }

    @Override
    public void updateTrade(TradeDTO tradeDTO) {

        Trade trade = new Trade();
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());

        tradeRepository.save(trade);


    }

    @Override
    public void deleteTrade(TradeDTO tradeDTO){

        Trade trade = new Trade();
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());


        tradeRepository.delete(trade);

    }

    @Override
    public TradeDTO checkTrade(Integer tradeId){
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + tradeId));
        TradeDTO tradeDTO = new TradeDTO();

        tradeDTO.setTradeId(trade.getTradeId());
        tradeDTO.setAccount(trade.getAccount());
        tradeDTO.setType(trade.getType());
        tradeDTO.setBuyQuantity(trade.getBuyQuantity());


        return tradeDTO;
    }


}
