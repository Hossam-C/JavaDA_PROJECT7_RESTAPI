package com.nnk.springboot.services.impl;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidListDTO> bidList(){

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

        BidList bidList = new BidList();
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());

        bidListRepository.save(bidList);

    }

    @Override
    public void updateBid(BidListDTO bidListDTO) {

        BidList bidList = new BidList();
        bidList.setBidListId(bidListDTO.getId());
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());

        bidListRepository.save(bidList);


    }

    @Override
    public void deleteBid(BidListDTO bidListDTO){

        BidList bidList = new BidList();
        bidList.setBidListId(bidListDTO.getId());
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());


        bidListRepository.delete(bidList);

    }

    @Override
    public BidListDTO checkBid(Integer id){
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        BidListDTO bidListDTO = new BidListDTO();

        bidListDTO.setId(bidList.getBidListId());
        bidListDTO.setAccount(bidList.getAccount());
        bidListDTO.setType(bidList.getType());
        bidListDTO.setBidQuantity(bidList.getBidQuantity());


        return bidListDTO;
    }
}
