package com.nnk.springboot.integrationTest;

import com.nnk.springboot.DTO.RatingDTO;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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
public class RatingIT {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private RatingService ratingService;

	private Rating rating1 = new Rating();
	private Rating rating2 = new Rating();
	private Rating rating3 = new Rating();

	private RatingDTO ratingDTO1 = new RatingDTO();
	private RatingDTO ratingDTO2 = new RatingDTO();
	private RatingDTO ratingDTO3 = new RatingDTO();

	@BeforeEach
	@Rollback(value = false)
	public void setUp(){

		rating1.setId(1);
		rating1.setMoodysRating("1");
		rating1.setSandPRating("10");
		rating1.setFitchRating("100");
		rating1.setOrderNumber(11);

		ratingDTO1.setId(rating1.getId());
		ratingDTO1.setMoodysRating(rating1.getMoodysRating());
		ratingDTO1.setSandPRating(rating1.getSandPRating());
		ratingDTO1.setFitchRating(rating1.getFitchRating());
		ratingDTO1.setOrderNumber(rating1.getOrderNumber());


		rating2.setId(2);
		rating2.setMoodysRating("2");
		rating2.setSandPRating("20");
		rating2.setFitchRating("200");
		rating2.setOrderNumber(22);

		ratingDTO2.setId(rating2.getId());
		ratingDTO2.setMoodysRating(rating2.getMoodysRating());
		ratingDTO2.setSandPRating(rating2.getSandPRating());
		ratingDTO2.setFitchRating(rating2.getFitchRating());
		ratingDTO2.setOrderNumber(rating2.getOrderNumber());

		rating3.setId(3);
		rating3.setMoodysRating("3");
		rating3.setSandPRating("30");
		rating3.setFitchRating("300");
		rating3.setOrderNumber(33);

		ratingDTO3.setId(rating3.getId());
		ratingDTO3.setMoodysRating(rating3.getMoodysRating());
		ratingDTO3.setSandPRating(rating3.getSandPRating());
		ratingDTO3.setFitchRating(rating3.getFitchRating());
		ratingDTO3.setOrderNumber(rating3.getOrderNumber());

		rating1 = ratingRepository.save(rating1);
		rating2 = ratingRepository.save(rating2);
		rating3 = ratingRepository.save(rating3);

	}

	@AfterEach
	@Rollback(value = false)
	public void cleanDB() {
		ratingRepository.deleteAll();
	}

	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		Assert.assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}


	@Test
	public void listRatingService() {

		List<RatingDTO> listRating = ratingService.ratingList();

		assertThat(listRating.get(0).getMoodysRating()).isEqualTo("1");
		assertThat(listRating.get(0).getSandPRating()).isEqualTo("10");
		assertThat(listRating.get(0).getFitchRating()).isEqualTo("100");
		assertThat(listRating.get(0).getOrderNumber()).isEqualTo(11);

		assertThat(listRating.get(1).getMoodysRating()).isEqualTo("2");
		assertThat(listRating.get(1).getSandPRating()).isEqualTo("20");
		assertThat(listRating.get(1).getFitchRating()).isEqualTo("200");
		assertThat(listRating.get(1).getOrderNumber()).isEqualTo(22);

		assertThat(listRating.get(2).getMoodysRating()).isEqualTo("3");
		assertThat(listRating.get(2).getSandPRating()).isEqualTo("30");
		assertThat(listRating.get(2).getFitchRating()).isEqualTo("300");
		assertThat(listRating.get(2).getOrderNumber()).isEqualTo(33);

	}

	@Test
	public void addRatingTest() {


		RatingDTO ratingDTO4 = new RatingDTO();

		ratingDTO4.setId(4);
		ratingDTO4.setMoodysRating("4");
		ratingDTO4.setSandPRating("40");
		ratingDTO4.setFitchRating("400");
		ratingDTO4.setOrderNumber(44);

		ratingService.addRating(ratingDTO4);


		List<RatingDTO> listRating = ratingService.ratingList();

		assertThat(listRating.get(3).getMoodysRating()).isEqualTo("4");
		assertThat(listRating.get(3).getSandPRating()).isEqualTo("40");
		assertThat(listRating.get(3).getFitchRating()).isEqualTo("400");
		assertThat(listRating.get(3).getOrderNumber()).isEqualTo(44);


	}

	@Test
	public void updateRatingTest() {

		ratingDTO1.setMoodysRating("9");
		ratingDTO1.setSandPRating("90");
		ratingDTO1.setFitchRating("900");
		ratingDTO1.setOrderNumber(99);

		ratingService.updateRating(ratingDTO1);

		Rating rating9 = ratingRepository.getRatingByOrderNumber(99);

		assertThat(rating9.getMoodysRating()).isEqualTo("9");
		assertThat(rating9.getSandPRating()).isEqualTo("90");
		assertThat(rating9.getFitchRating()).isEqualTo("900");
		assertThat(rating9.getOrderNumber()).isEqualTo(99);

	}

	@Test
	public void deleteRatingTest() {

		rating1 = ratingRepository.getRatingByOrderNumber(11);
		ratingDTO1.setId(rating1.getId());

		ratingService.deleteRating(ratingDTO1);


		assertThat(ratingRepository.getRatingByOrderNumber(11)).isNull();
		assertThat(ratingRepository.getRatingByOrderNumber(22).getMoodysRating()).isEqualTo("2");
		assertThat(ratingRepository.getRatingByOrderNumber(33).getSandPRating()).isEqualTo("30");
	}

	@Test
	public void checkRatingTest() {

		RatingDTO ratingCheck = ratingService.checkRating(ratingRepository.getRatingByOrderNumber(22).getId());

		assertThat(ratingCheck.getMoodysRating()).isEqualTo("2");
		assertThat(ratingCheck.getSandPRating()).isEqualTo("20");
		assertThat(ratingCheck.getFitchRating()).isEqualTo("200");
		assertThat(ratingCheck.getOrderNumber()).isEqualTo(22);

	}

	@Test
	public void checkRatingNotFoundTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			ratingService.checkRating(999);
		});

	}
}

