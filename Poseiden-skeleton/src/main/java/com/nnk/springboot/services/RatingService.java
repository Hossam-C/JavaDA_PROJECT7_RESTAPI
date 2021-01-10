package com.nnk.springboot.services;

import com.nnk.springboot.DTO.RatingDTO;

import java.util.List;

public interface RatingService {

    public List<RatingDTO> ratingList();

    public void addRating(RatingDTO ratingDTO);

    public void updateRating(RatingDTO ratingDTO);

    public void deleteRating(RatingDTO ratingDTO);

    public RatingDTO checkRating(Integer id);
}
