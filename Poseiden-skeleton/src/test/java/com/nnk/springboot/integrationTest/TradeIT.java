package com.nnk.springboot.integrationTest;


import com.nnk.springboot.DTO.TradeDTO;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeIT {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TradeService tradeService;

	private Trade trade1 = new Trade();
	private Trade trade2 = new Trade();
	private Trade trade3 = new Trade();

	private TradeDTO tradeDTO1 = new TradeDTO();
	private TradeDTO tradeDTO2 = new TradeDTO();
	private TradeDTO tradeDTO3 = new TradeDTO();

	@BeforeEach
	@Rollback(value = false)
	public void setUp(){

		trade1.setTradeId(1);
		trade1.setAccount("Account1");
		trade1.setType("Type1");
		trade1.setBuyQuantity(10d);

		tradeDTO1.setTradeId(trade1.getTradeId());
		tradeDTO1.setAccount(trade1.getAccount());
		tradeDTO1.setType(trade1.getType());
		tradeDTO1.setBuyQuantity(trade1.getBuyQuantity());


		trade2.setTradeId(2);
		trade2.setAccount("Account2");
		trade2.setType("Type2");
		trade2.setBuyQuantity(20d);

		tradeDTO2.setTradeId(trade2.getTradeId());
		tradeDTO2.setAccount(trade2.getAccount());
		tradeDTO2.setType(trade2.getType());
		tradeDTO2.setBuyQuantity(trade2.getBuyQuantity());

		trade3.setTradeId(3);
		trade3.setAccount("Account3");
		trade3.setType("Type3");
		trade3.setBuyQuantity(30d);

		tradeDTO3.setTradeId(trade3.getTradeId());
		tradeDTO3.setAccount(trade3.getAccount());
		tradeDTO3.setType(trade3.getType());
		tradeDTO3.setBuyQuantity(trade3.getBuyQuantity());

		trade1 = tradeRepository.save(trade1);
		trade2 = tradeRepository.save(trade2);
		trade3 = tradeRepository.save(trade3);

	}

	@AfterEach
	@Rollback(value = false)
	public void cleanDB() {
		tradeRepository.deleteAll();
	}


	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type", 10d);

		// Save
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getTradeId());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}

	@Test
	public void listTradeService() {

		List<TradeDTO> listTrade = tradeService.tradeList();

		assertThat(listTrade.get(0).getAccount()).isEqualTo("Account1");
		assertThat(listTrade.get(0).getType()).isEqualTo("Type1");
		assertThat(listTrade.get(0).getBuyQuantity()).isEqualTo(10d);

		assertThat(listTrade.get(1).getAccount()).isEqualTo("Account2");
		assertThat(listTrade.get(1).getType()).isEqualTo("Type2");
		assertThat(listTrade.get(1).getBuyQuantity()).isEqualTo(20d);

		assertThat(listTrade.get(2).getAccount()).isEqualTo("Account3");
		assertThat(listTrade.get(2).getType()).isEqualTo("Type3");
		assertThat(listTrade.get(2).getBuyQuantity()).isEqualTo(30d);

	}

	@Test
	public void addTradeTest() {


		TradeDTO tradeDTO4 = new TradeDTO();

		tradeDTO4.setTradeId(4);
		tradeDTO4.setAccount("Account4");
		tradeDTO4.setType("Type4");
		tradeDTO4.setBuyQuantity(40d);

		tradeService.addTrade(tradeDTO4);


		List<TradeDTO> listTrade = tradeService.tradeList();

		assertThat(listTrade.get(3).getAccount()).isEqualTo("Account4");
		assertThat(listTrade.get(3).getType()).isEqualTo("Type4");
		assertThat(listTrade.get(3).getBuyQuantity()).isEqualTo(40d);


	}

	@Test
	public void updateTradeTest() {

		tradeDTO1.setAccount("Account9");
		tradeDTO1.setType("Type9");
		tradeDTO1.setBuyQuantity(99d);

		tradeService.updateTrade(tradeDTO1);

		Trade trade9 = tradeRepository.getTradeByAccount("Account9");

		assertThat(trade9.getAccount()).isEqualTo("Account9");
		assertThat(trade9.getType()).isEqualTo("Type9");
		assertThat(trade9.getBuyQuantity()).isEqualTo(99d);

	}

	@Test
	public void deleteTradeTest() {

		trade1 = tradeRepository.getTradeByAccount("Account1");
		tradeDTO1.setTradeId(trade1.getTradeId());

		tradeService.deleteTrade(tradeDTO1);


		assertThat(tradeRepository.getTradeByAccount("Account1")).isNull();
		assertThat(tradeRepository.getTradeByAccount("Account2").getType()).isEqualTo("Type2");
		assertThat(tradeRepository.getTradeByAccount("Account3").getType()).isEqualTo("Type3");
	}

	@Test
	public void checkTradeTest() {

		TradeDTO tradeCheck = tradeService.checkTrade(tradeRepository.getTradeByAccount("Account2").getTradeId());

		assertThat(tradeCheck.getAccount()).isEqualTo("Account2");
		assertThat(tradeCheck.getType()).isEqualTo("Type2");
		assertThat(tradeCheck.getBuyQuantity()).isEqualTo(20d);

	}

	@Test
	public void checkTradeNotFoundTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			tradeService.checkTrade(999);
		});

	}
}
