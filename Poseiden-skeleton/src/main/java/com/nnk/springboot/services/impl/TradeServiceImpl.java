package com.nnk.springboot.services.impl;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TradeServiceImpl implements TradeService {

    private static final Logger logger = LogManager.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<TradeDTO> tradeList(){

        logger.debug("tradeList");

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

        logger.debug("addTrade");

        Trade trade = new Trade();
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());

        tradeRepository.save(trade);

    }

    @Override
    public void updateTrade(TradeDTO tradeDTO) {

        logger.debug("updateTrade");

        Trade trade = new Trade();
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());

        tradeRepository.save(trade);


    }

    @Override
    public void deleteTrade(TradeDTO tradeDTO){

        logger.debug("deleteTrade");

        Trade trade = new Trade();
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());


        tradeRepository.delete(trade);

    }

    @Override
    public TradeDTO checkTrade(Integer tradeId){

        logger.debug("checkTrade");

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + tradeId));
        TradeDTO tradeDTO = new TradeDTO();

        tradeDTO.setTradeId(trade.getTradeId());
        tradeDTO.setAccount(trade.getAccount());
        tradeDTO.setType(trade.getType());
        tradeDTO.setBuyQuantity(trade.getBuyQuantity());


        return tradeDTO;
    }


}
