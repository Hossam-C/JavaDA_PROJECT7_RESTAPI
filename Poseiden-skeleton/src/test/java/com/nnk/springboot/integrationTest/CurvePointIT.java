package com.nnk.springboot.integrationTest;


import com.nnk.springboot.DTO.CurvePointDTO;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class CurvePointIT {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	private CurvePointService curvePointService;

	private CurvePoint curve1 = new CurvePoint();
	private CurvePoint curve2 = new CurvePoint();
	private CurvePoint curve3 = new CurvePoint();

	private CurvePointDTO curveDTO1 = new CurvePointDTO();
	private CurvePointDTO curveDTO2 = new CurvePointDTO();
	private CurvePointDTO curveDTO3 = new CurvePointDTO();

	@BeforeEach
	@Rollback(value = false)
	public void setUp(){

		curve1.setId(1);
		curve1.setCurveId(111);
		curve1.setTerm(100d);
		curve1.setValue(10d);

		curveDTO1.setId(curve1.getId());
		curveDTO1.setCurveId(curve1.getCurveId());
		curveDTO1.setTerm(curve1.getTerm());
		curveDTO1.setValue(curve1.getValue());


		curve2.setId(2);
		curve2.setCurveId(222);
		curve2.setTerm(200d);
		curve2.setValue(20d);

		curveDTO2.setId(curve2.getId());
		curveDTO2.setCurveId(curve2.getCurveId());
		curveDTO2.setTerm(curve2.getTerm());
		curveDTO2.setValue(curve2.getValue());

		curve3.setId(3);
		curve3.setCurveId(333);
		curve3.setTerm(300d);
		curve3.setValue(30d);

		curveDTO3.setId(curve3.getId());
		curveDTO3.setCurveId(curve3.getCurveId());
		curveDTO3.setTerm(curve3.getTerm());
		curveDTO3.setValue(curve3.getValue());

		curve1 = curvePointRepository.save(curve1);
		curve2 = curvePointRepository.save(curve2);
		curve3 = curvePointRepository.save(curve3);
	}

	@AfterEach
	@Rollback(value = false)
	public void cleanDB() {
		curvePointRepository.deleteAll();
	}

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertNotNull(curvePoint.getId());
		Assert.assertTrue(curvePoint.getCurveId() == 10);

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertTrue(curvePoint.getCurveId() == 20);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	}

	@Test
	public void listCurveService() {

		List<CurvePointDTO> listCurve = curvePointService.curvePointList();

		assertThat(listCurve.get(0).getCurveId()).isEqualTo(111);
		assertThat(listCurve.get(0).getTerm()).isEqualTo(100d);
		assertThat(listCurve.get(0).getValue()).isEqualTo(10d);

		assertThat(listCurve.get(1).getCurveId()).isEqualTo(222);
		assertThat(listCurve.get(1).getTerm()).isEqualTo(200d);
		assertThat(listCurve.get(1).getValue()).isEqualTo(20d);

		assertThat(listCurve.get(2).getCurveId()).isEqualTo(333);
		assertThat(listCurve.get(2).getTerm()).isEqualTo(300d);
		assertThat(listCurve.get(2).getValue()).isEqualTo(30d);

	}

	@Test
	public void addCurveTest() {


		CurvePointDTO curveDTO4 = new CurvePointDTO();

		curveDTO4.setId(4);
		curveDTO4.setCurveId(444);
		curveDTO4.setTerm(400d);
		curveDTO4.setValue(40d);

		curvePointService.addCurve(curveDTO4);


		List<CurvePointDTO> listCurve = curvePointService.curvePointList();

		assertThat(listCurve.get(3).getCurveId()).isEqualTo(444);
		assertThat(listCurve.get(3).getTerm()).isEqualTo(400d);
		assertThat(listCurve.get(3).getValue()).isEqualTo(40d);


	}

	@Test
	public void updateCurveTest() {

		curveDTO1.setCurveId(999);
		curveDTO1.setTerm(900d);
		curveDTO1.setValue(99d);

		curvePointService.updateCurve(curveDTO1);

		CurvePoint curve9 = curvePointRepository.getCurvePointByCurveId(999);

		assertThat(curve9.getCurveId()).isEqualTo(999);
		assertThat(curve9.getTerm()).isEqualTo(900d);
		assertThat(curve9.getValue()).isEqualTo(99d);

	}

	@Test
	public void deleteCurveTest() {

		curve1 = curvePointRepository.getCurvePointByCurveId(111);
		curveDTO1.setId(curve1.getId());

		curvePointService.deleteCurve(curveDTO1);


		assertThat(curvePointRepository.getCurvePointByCurveId(111)).isNull();
		assertThat(curvePointRepository.getCurvePointByCurveId(222).getTerm()).isEqualTo(200d);
		assertThat(curvePointRepository.getCurvePointByCurveId(333).getTerm()).isEqualTo(300d);
	}

	@Test
	public void checkCurveTest() {

		CurvePointDTO curveCheck = curvePointService.checkCurve(curvePointRepository.getCurvePointByCurveId(222).getId());

		assertThat(curveCheck.getCurveId()).isEqualTo(222);
		assertThat(curveCheck.getTerm()).isEqualTo(200d);
		assertThat(curveCheck.getValue()).isEqualTo(20d);

	}

	@Test
	public void checkCurveNotFoundTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			curvePointService.checkCurve(999);
		});

	}

}
