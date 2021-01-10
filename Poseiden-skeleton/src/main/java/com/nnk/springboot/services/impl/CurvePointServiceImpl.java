package com.nnk.springboot.services.impl;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.CurvePointDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePointDTO> curvePointList(){
        List<CurvePointDTO>  curvePointDTOList = new ArrayList<>();

        List<CurvePoint> curvePointList = curvePointRepository.findAll();

        for ( CurvePoint curvelist : curvePointList) {
            CurvePointDTO curvePointDTO = new CurvePointDTO();
            curvePointDTO.setId(curvelist.getId());
            curvePointDTO.setCurveId(curvelist.getCurveId());
            curvePointDTO.setTerm(curvelist.getTerm());
            curvePointDTO.setValue(curvelist.getValue());

            curvePointDTOList.add(curvePointDTO);
        }

        return curvePointDTOList;
    }

    @Override
    public void addCurve(CurvePointDTO curvePointDTO){

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(curvePointDTO.getCurveId());
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());

        curvePointRepository.save(curvePoint);

    }

    @Override
    public void updateCurve(CurvePointDTO curvePointDTO) {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(curvePointDTO.getId());
        curvePoint.setCurveId(curvePointDTO.getCurveId());
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());

        curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurve(CurvePointDTO curvePointDTO){

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(curvePointDTO.getId());
        curvePoint.setCurveId(curvePointDTO.getCurveId());
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());



        curvePointRepository.delete(curvePoint);

    }

    @Override
    public CurvePointDTO checkCurve(Integer id){
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Curve Id:" + id));
        CurvePointDTO curvePointDTO = new CurvePointDTO();

        curvePointDTO.setId(curvePoint.getId());
        curvePointDTO.setCurveId(curvePoint.getCurveId());
        curvePointDTO.setTerm(curvePoint.getTerm());
        curvePointDTO.setValue(curvePoint.getValue());


        return curvePointDTO;
    }


}
