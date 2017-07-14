package com.jifenke.lepluslive.yibao;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.service.MerchantUserLedgerService;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
public class MerchantUserLedgerTest {


  @Inject
  private MerchantUserLedgerService merchantUserLedgerService;

  //子商户注册
  @Test
  public void registerTest() {

    MerchantUserLedger ledger = new MerchantUserLedger();
    ledger.setId(0L);
    ledger.setAccountName("张文");
    ledger.setBankAccountNumber("6228481108572810978");
    ledger.setBankAccountType(1);
    ledger.setBankCity("北京市");
    ledger.setBankName("北京农业银行西三旗支行");
    ledger.setBankProvince("北京市");
    ledger.setBindMobile("15501066812");
    ledger.setIdCard("370481199201076437");
    ledger.setLinkman("张文");
    ledger.setMinSettleAmount(10000);
    ledger.setSignedName("张文");

    ledger.setCustomerType(1);
    ledger.setBusinessLicence("fksdfdierwer3323242");
    ledger.setLegalPerson("张文");

    merchantUserLedgerService.edit(ledger);
  }

}
