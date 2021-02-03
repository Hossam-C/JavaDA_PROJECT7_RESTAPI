package com.nnk.springboot.integrationTest;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
public class BidIT {

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private BidListService bidListService;

	private BidList bid1 = new BidList();
	private BidList bid2 = new BidList();
	private BidList bid3 = new BidList();

	private BidListDTO bidDTO1 = new BidListDTO();
	private BidListDTO bidDTO2 = new BidListDTO();
	private BidListDTO bidDTO3 = new BidListDTO();

	@BeforeEach
	@Rollback(value = false)
	public void setUp(){

		bid1.setBidListId(1);
		bid1.setAccount("Account1");
		bid1.setType("Type1");
		bid1.setBidQuantity(10d);

		bidDTO1.setId(bid1.getBidListId());
		bidDTO1.setAccount(bid1.getAccount());
		bidDTO1.setType(bid1.getType());
		bidDTO1.setBidQuantity(bid1.getBidQuantity());


		bid2.setBidListId(2);
		bid2.setAccount("Account2");
		bid2.setType("Type2");
		bid2.setBidQuantity(20d);

		bidDTO2.setId(bid2.getBidListId());
		bidDTO2.setAccount(bid2.getAccount());
		bidDTO2.setType(bid2.getType());
		bidDTO2.setBidQuantity(bid2.getBidQuantity());

		bid3.setBidListId(3);
		bid3.setAccount("Account3");
		bid3.setType("Type3");
		bid3.setBidQuantity(30d);

		bidDTO3.setId(bid3.getBidListId());
		bidDTO3.setAccount(bid3.getAccount());
		bidDTO3.setType(bid3.getType());
		bidDTO3.setBidQuantity(bid3.getBidQuantity());

		bid1 = bidListRepository.save(bid1);
		bid2 = bidListRepository.save(bid2);
		bid3 = bidListRepository.save(bid3);

	}

	@AfterEach
	@Rollback(value = false)
	public void cleanDB() {
		bidListRepository.deleteAll();
	}

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getBidListId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}

	@Test
	public void listBidService() {

		List<BidListDTO> listBid = bidListService.bidList();

		assertThat(listBid.get(0).getAccount()).isEqualTo("Account1");
		assertThat(listBid.get(0).getType()).isEqualTo("Type1");
		assertThat(listBid.get(0).getBidQuantity()).isEqualTo(10d);

		assertThat(listBid.get(1).getAccount()).isEqualTo("Account2");
		assertThat(listBid.get(1).getType()).isEqualTo("Type2");
		assertThat(listBid.get(1).getBidQuantity()).isEqualTo(20d);

		assertThat(listBid.get(2).getAccount()).isEqualTo("Account3");
		assertThat(listBid.get(2).getType()).isEqualTo("Type3");
		assertThat(listBid.get(2).getBidQuantity()).isEqualTo(30d);

	}

	@Test
	public void addBidTest() {


		BidListDTO bidDTO4 = new BidListDTO();

		bidDTO4.setId(4);
		bidDTO4.setAccount("Account4");
		bidDTO4.setType("Type4");
		bidDTO4.setBidQuantity(40d);

		bidListService.addBid(bidDTO4);


		List<BidListDTO> listBid = bidListService.bidList();

		assertThat(listBid.get(3).getAccount()).isEqualTo("Account4");
		assertThat(listBid.get(3).getType()).isEqualTo("Type4");
		assertThat(listBid.get(3).getBidQuantity()).isEqualTo(40d);


	}

	@Test
	public void updateBidTest() {

		bidDTO1.setAccount("Account9");
		bidDTO1.setType("Type9");
		bidDTO1.setBidQuantity(99d);

		bidListService.updateBid(bidDTO1);

		BidList bid9 = bidListRepository.getBidListByAccount("Account9");

		assertThat(bid9.getAccount()).isEqualTo("Account9");
		assertThat(bid9.getType()).isEqualTo("Type9");
		assertThat(bid9.getBidQuantity()).isEqualTo(99d);

	}

	@Test
	public void deleteBidTest() {

		bid1 = bidListRepository.getBidListByAccount("Account1");
		bidDTO1.setId(bid1.getBidListId());

		bidListService.deleteBid(bidDTO1);


		assertThat(bidListRepository.getBidListByAccount("Account1")).isNull();
		assertThat(bidListRepository.getBidListByAccount("Account2").getType()).isEqualTo("Type2");
		assertThat(bidListRepository.getBidListByAccount("Account3").getType()).isEqualTo("Type3");
	}

	@Test
	public void checkBidTest() {

		BidListDTO bidCheck = bidListService.checkBid(bidListRepository.getBidListByAccount("Account2").getBidListId());

		assertThat(bidCheck.getAccount()).isEqualTo("Account2");
		assertThat(bidCheck.getType()).isEqualTo("Type2");
		assertThat(bidCheck.getBidQuantity()).isEqualTo(20d);

	}

	@Test
	public void checkBidNotFoundTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			bidListService.checkBid(999);
		});

	}
}
