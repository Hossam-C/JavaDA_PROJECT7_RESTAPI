package com.nnk.springboot.services.impl;


import com.nnk.springboot.DTO.RatingDTO;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger logger = LogManager.getLogger(RatingService.class);

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public List<RatingDTO> ratingList(){

        logger.debug("ratingList");

        List<RatingDTO>  ratingListDTO = new ArrayList<>();

        List<Rating> ratingList = ratingRepository.findAll();

        for ( Rating rlist : ratingList) {
            RatingDTO ratingDTO = new RatingDTO();
            ratingDTO.setId(rlist.getId());
            ratingDTO.setMoodysRating(rlist.getMoodysRating());
            ratingDTO.setSandPRating(rlist.getSandPRating());
            ratingDTO.setFitchRating(rlist.getFitchRating());
            ratingDTO.setOrderNumber(rlist.getOrderNumber());

            ratingListDTO.add(ratingDTO);
        }


        return ratingListDTO;
    }

    @Override
    public void addRating(RatingDTO ratingDTO){

        logger.debug("addRating");

        Rating rating = new Rating();
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setSandPRating(ratingDTO.getSandPRating());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setOrderNumber(ratingDTO.getOrderNumber());

        ratingRepository.save(rating);

    }

    @Override
    public void updateRating(RatingDTO ratingDTO) {

        logger.debug("updateRating");

        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setSandPRating(ratingDTO.getSandPRating());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setOrderNumber(ratingDTO.getOrderNumber());

        ratingRepository.save(rating);


    }

    @Override
    public void deleteRating(RatingDTO ratingDTO){

        logger.debug("deleteRating");

        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setSandPRating(ratingDTO.getSandPRating());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setOrderNumber(ratingDTO.getOrderNumber());


        ratingRepository.delete(rating);

    }

    @Override
    public RatingDTO checkRating(Integer id){

        logger.debug("checkRating");

        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
        RatingDTO ratingDTO = new RatingDTO();

        ratingDTO.setId(rating.getId());
        ratingDTO.setMoodysRating(rating.getMoodysRating());
        ratingDTO.setSandPRating(rating.getSandPRating());
        ratingDTO.setFitchRating(rating.getFitchRating());
        ratingDTO.setOrderNumber(rating.getOrderNumber());


        return ratingDTO;
    }
}
