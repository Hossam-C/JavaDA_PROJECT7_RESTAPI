package com.nnk.springboot.services;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.CurvePointDTO;

import java.util.List;

public interface CurvePointService {

    public List<CurvePointDTO> curvePointList();

    public void addCurve(CurvePointDTO curvePointDTO);

    public void updateCurve(CurvePointDTO curvePointDTO);

    public void deleteCurve(CurvePointDTO curvePointDTO);

    public CurvePointDTO checkCurve(Integer id);
}
