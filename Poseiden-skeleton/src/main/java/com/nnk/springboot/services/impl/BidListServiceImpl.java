package com.nnk.springboot.services.impl;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BidListServiceImpl implements BidListService {

    private static final Logger logger = LogManager.getLogger(BidListService.class);


    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidListDTO> bidList(){

        logger.debug("bidList");

        List<BidListDTO>  bidListDTO = new ArrayList<>();

        List<BidList> bidList = bidListRepository.findAll();

        for ( BidList blist : bidList) {
            BidListDTO blistDTO = new BidListDTO();
            blistDTO.setId(blist.getBidListId());
            blistDTO.setAccount(blist.getAccount());
            blistDTO.setType(blist.getType());
            blistDTO.setBidQuantity(blist.getBidQuantity());

            bidListDTO.add(blistDTO);
        }


        return bidListDTO;
    }

    @Override
    public void addBid(BidListDTO bidListDTO){

        logger.debug("addBid");

        BidList bidList = new BidList();
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());

        bidListRepository.save(bidList);

    }

    @Override
    public void updateBid(BidListDTO bidListDTO) {

        logger.debug("updateBid");

        BidList bidList = new BidList();
        bidList.setBidListId(bidListDTO.getId());
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());

        bidListRepository.save(bidList);


    }

    @Override
    public void deleteBid(BidListDTO bidListDTO){

        logger.debug("deleteBid");

        BidList bidList = new BidList();
        bidList.setBidListId(bidListDTO.getId());
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());


        bidListRepository.delete(bidList);

    }

    @Override
    public BidListDTO checkBid(Integer id){

        logger.debug("checkBid");

        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        BidListDTO bidListDTO = new BidListDTO();

        bidListDTO.setId(bidList.getBidListId());
        bidListDTO.setAccount(bidList.getAccount());
        bidListDTO.setType(bidList.getType());
        bidListDTO.setBidQuantity(bidList.getBidQuantity());


        return bidListDTO;
    }

    @Override
    public void checkBidByAccount(String account) {

        logger.debug("checkBidByAccount");

        BidList bidList = bidListRepository.getBidListByAccount(account);
        if (bidList != null){
            throw new IllegalArgumentException("Account already existant:" + account);
        }
    }
}
