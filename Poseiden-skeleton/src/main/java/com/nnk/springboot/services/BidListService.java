package com.nnk.springboot.services;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BidListService {

    public List<BidListDTO> bidList();

    public void addBid(BidListDTO bidListDTO);

    public void updateBid(BidListDTO bidListDTO);

    public void deleteBid(BidListDTO bidListDTO);

    public BidListDTO checkBid(Integer id);

}
