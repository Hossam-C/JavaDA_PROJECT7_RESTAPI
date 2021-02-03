package com.nnk.springboot.integrationTest;


import com.nnk.springboot.DTO.RuleNameDTO;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleIT {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	private RuleNameService ruleNameService;

	private RuleName rule1 = new RuleName();
	private RuleName rule2 = new RuleName();
	private RuleName rule3 = new RuleName();

	private RuleNameDTO ruleDTO1 = new RuleNameDTO();
	private RuleNameDTO ruleDTO2 = new RuleNameDTO();
	private RuleNameDTO ruleDTO3 = new RuleNameDTO();


	@BeforeEach
	@Rollback(value = false)
	public void setUp(){

		rule1.setId(1);
		rule1.setName("rule1");
		rule1.setDescription("test1");
		rule1.setJson("1");
		rule1.setTemplate("11");
		rule1.setSqlStr("111");
		rule1.setSqlPart("1111");

		ruleDTO1.setId(rule1.getId());
		ruleDTO1.setName(rule1.getName());
		ruleDTO1.setDescription(rule1.getDescription());
		ruleDTO1.setJson(rule1.getJson());
		ruleDTO1.setTemplate(rule1.getTemplate());
		ruleDTO1.setSqlStr(rule1.getSqlStr());
		ruleDTO1.setSqlPart(rule1.getSqlPart());


		rule2.setId(2);
		rule2.setName("rule2");
		rule2.setDescription("test2");
		rule2.setJson("2");
		rule2.setTemplate("22");
		rule2.setSqlStr("222");
		rule2.setSqlPart("2222");

		ruleDTO2.setId(rule2.getId());
		ruleDTO2.setName(rule2.getName());
		ruleDTO2.setDescription(rule2.getDescription());
		ruleDTO2.setJson(rule2.getJson());
		ruleDTO2.setTemplate(rule2.getTemplate());
		ruleDTO2.setSqlStr(rule2.getSqlStr());
		ruleDTO2.setSqlPart(rule2.getSqlPart());

		rule3.setId(3);
		rule3.setName("rule3");
		rule3.setDescription("test3");
		rule3.setJson("3");
		rule3.setTemplate("33");
		rule3.setSqlStr("333");
		rule3.setSqlPart("3333");

		ruleDTO3.setId(rule3.getId());
		ruleDTO3.setName(rule3.getName());
		ruleDTO3.setDescription(rule3.getDescription());
		ruleDTO3.setJson(rule3.getJson());
		ruleDTO3.setTemplate(rule3.getTemplate());
		ruleDTO3.setSqlStr(rule3.getSqlStr());
		ruleDTO3.setSqlPart(rule3.getSqlPart());

		rule1 = ruleNameRepository.save(rule1);
		rule2 = ruleNameRepository.save(rule2);
		rule3 = ruleNameRepository.save(rule3);

	}

	@AfterEach
	@Rollback(value = false)
	public void cleanDB() {
		ruleNameRepository.deleteAll();
	}

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}

	@Test
	public void listRatingService() {

		List<RuleNameDTO> listRule = ruleNameService.ruleList();

		assertThat(listRule.get(0).getName()).isEqualTo("rule1");
		assertThat(listRule.get(0).getDescription()).isEqualTo("test1");
		assertThat(listRule.get(0).getJson()).isEqualTo("1");
		assertThat(listRule.get(0).getTemplate()).isEqualTo("11");
		assertThat(listRule.get(0).getSqlStr()).isEqualTo("111");
		assertThat(listRule.get(0).getSqlPart()).isEqualTo("1111");

		assertThat(listRule.get(1).getName()).isEqualTo("rule2");
		assertThat(listRule.get(1).getDescription()).isEqualTo("test2");
		assertThat(listRule.get(1).getJson()).isEqualTo("2");
		assertThat(listRule.get(1).getTemplate()).isEqualTo("22");
		assertThat(listRule.get(1).getSqlStr()).isEqualTo("222");
		assertThat(listRule.get(1).getSqlPart()).isEqualTo("2222");

		assertThat(listRule.get(2).getName()).isEqualTo("rule3");
		assertThat(listRule.get(2).getDescription()).isEqualTo("test3");
		assertThat(listRule.get(2).getJson()).isEqualTo("3");
		assertThat(listRule.get(2).getTemplate()).isEqualTo("33");
		assertThat(listRule.get(2).getSqlStr()).isEqualTo("333");
		assertThat(listRule.get(2).getSqlPart()).isEqualTo("3333");

	}

	@Test
	public void addRuleTest() {


		RuleNameDTO ruleDTO4 = new RuleNameDTO();

		ruleDTO4.setId(4);
		ruleDTO4.setName("rule4");
		ruleDTO4.setDescription("test4");
		ruleDTO4.setJson("4");
		ruleDTO4.setTemplate("44");
		ruleDTO4.setSqlStr("444");
		ruleDTO4.setSqlPart("4444");

		ruleNameService.addRule(ruleDTO4);


		List<RuleNameDTO> listRule = ruleNameService.ruleList();

		assertThat(listRule.get(3).getName()).isEqualTo("rule4");
		assertThat(listRule.get(3).getDescription()).isEqualTo("test4");
		assertThat(listRule.get(3).getJson()).isEqualTo("4");
		assertThat(listRule.get(3).getTemplate()).isEqualTo("44");
		assertThat(listRule.get(3).getSqlStr()).isEqualTo("444");
		assertThat(listRule.get(3).getSqlPart()).isEqualTo("4444");

	}

	@Test
	public void updateRuleTest() {

		ruleDTO1.setName("rule9");
		ruleDTO1.setDescription("test9");
		ruleDTO1.setJson("9");
		ruleDTO1.setTemplate("99");
		ruleDTO1.setSqlStr("999");
		ruleDTO1.setSqlPart("9999");

		ruleNameService.updateRule(ruleDTO1);

		RuleName rule9 = ruleNameRepository.getRuleNameByName("rule9");

		assertThat(rule9.getName()).isEqualTo("rule9");
		assertThat(rule9.getDescription()).isEqualTo("test9");
		assertThat(rule9.getJson()).isEqualTo("9");
		assertThat(rule9.getTemplate()).isEqualTo("99");
		assertThat(rule9.getSqlStr()).isEqualTo("999");
		assertThat(rule9.getSqlPart()).isEqualTo("9999");

	}

	@Test
	public void deleteRuleTest() {

		rule1 = ruleNameRepository.getRuleNameByName("rule1");
		ruleDTO1.setId(rule1.getId());

		ruleNameService.deleteRule(ruleDTO1);


		assertThat(ruleNameRepository.getRuleNameByName("rule1")).isNull();
		assertThat(ruleNameRepository.getRuleNameByName("rule2").getDescription()).isEqualTo("test2");
		assertThat(ruleNameRepository.getRuleNameByName("rule3").getJson()).isEqualTo("3");
	}

	@Test
	public void checkRuleTest() {

		RuleNameDTO ruleCheck = ruleNameService.checkRule(ruleNameRepository.getRuleNameByName("rule2").getId());

		assertThat(ruleCheck.getName()).isEqualTo("rule2");
		assertThat(ruleCheck.getDescription()).isEqualTo("test2");
		assertThat(ruleCheck.getJson()).isEqualTo("2");
		assertThat(ruleCheck.getTemplate()).isEqualTo("22");
		assertThat(ruleCheck.getSqlStr()).isEqualTo("222");
		assertThat(ruleCheck.getSqlPart()).isEqualTo("2222");

	}

	@Test
	public void checkRuleNotFoundTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			ruleNameService.checkRule(999);
		});

	}
}
