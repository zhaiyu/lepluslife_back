package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.activity.service.ActivityPhoneOrderService;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.fuyou.service.ScanCodeOrderService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;
import com.jifenke.lepluslive.order.service.FinanicalStatisticService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.PosOrderService;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;
import com.jifenke.lepluslive.score.repository.ScoreARepository;
import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
import com.jifenke.lepluslive.user.repository.WeiXinUserRepository;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;
import com.jifenke.lepluslive.yinlian.service.UnionPayStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wcg on 16/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
public class ttt {


  @Inject
  private WeiXinUserRepository weiXinUserRepository;

  @Inject
  private LeJiaUserRepository leJiaUserRepository;

  @Inject
  private ScoreARepository scoreARepository;

  @Inject
  private MerchantRepository merchantRepository;

  @Inject
  private MerchantService merchantService;

  @Inject
  private BarcodeService barcodeService;

  private String barCodeRootUrl = "http://lepluslive-barcode.oss-cn-beijing.aliyuncs.com";

  @Inject
  private PartnerWalletRepository partnerWalletRepository;

  @Inject
  private ScoreAAccountService scoreAAccountService;

  @Inject
  private PartnerRepository partnerRepository;

  @Inject
  private OffLineOrderService offLineOrderService;

  @Inject
  private FinanicalStatisticService finanicalStatisticService;

  @Inject
  private ActivityPhoneOrderService phoneOrderService;

  @Inject
  private ScanCodeOrderService scanCodeOrderServic;

  @Inject
  private MerchantUserService merchantUserService;

  @Inject
  private UnionPayStoreService unionPayStoreService;

  //活动注册
  @Test
  public void qqqq() {
    Map map = unionPayStoreService.process();
    System.out.println(map.toString());
  }

  //会员注册
  @Test
  public void qqqq2() {
    Map map = unionPayStoreService.register("6212141200000000068", "15501066812");
    System.out.println(map.toString());
  }

  @Test
  public void tttt() {
//    UnionPayStore unionPayStore = new UnionPayStore();
//    unionPayStore.setShopName("尹瑞优惠券银行活动实时测试门店");
//    unionPayStore.setAddress("凯信国际广场1号楼");
//    Merchant merchant = merchantService.findMerchantById(15L);
//    unionPayStore.setMerchant(merchant);
//    Map
//        map =
//        unionPayStoreService.shopQuery(unionPayStore.getShopName(), unionPayStore.getAddress());
    Map
        map =
        unionPayStoreService.shopQuery("", "");

    if (map != null) {
      System.out.println(map.toString());
      String resultCode = String.valueOf(map.get("msg_rsp_code"));
      if ("0000".equals(resultCode)) {
        List<Map> shops = (List<Map>) map.get("shops");
        if (shops == null) {
          System.out.println(501 + "未找到相关门店信息");
        } else if (shops.size() > 1) {
          System.out.println(502 + "异常,找到" + shops.size() + "个相关门店信息");
        }
        for(Map<String, String> shop : shops){
          UnionPayStore unionPayStore = new UnionPayStore();
//    unionPayStore.setShopName("尹瑞优惠券银行活动实时测试门店");
//    unionPayStore.setAddress("凯信国际广场1号楼");
          Merchant merchant = merchantService.findMerchantById(15L);
          unionPayStore.setMerchant(merchant);
          try {
            unionPayStoreService.saveStore(shop, unionPayStore);
          } catch (Exception e) {
            e.printStackTrace();
            System.out.println(503 + "数据存储异常");
          }
        }
//        Map<String, String> shop = shops.get(1);

      } else {
        System.out.println(504 + String.valueOf(map.get("msg_rsp_desc")) + "(" + resultCode + ")");
      }
    } else {
      System.out.println(505 + "系统异常");
    }
    System.out.println("==============保存成功===============");
  }


  @Inject
  private PosOrderService posOrderService;

  @Test
  public void testBill() {
    //todo:未完成的任务
    String s = "12duc353ed43";
    //fixme:需要修正的任务
    System.out.println("=======================================");
    //XXX:已完成的任务
    System.out.println(s.indexOf('d'));
  }

  @Test
  public void testErrlog() {
    List<PosDailyBill> erroPosDailyBill = posOrderService.findErrorPosDailyBill(1, 3);
    List<List<PosErrorLog>>
        posErrorLogByBill =
        posOrderService.findPosErrorLogByBill(erroPosDailyBill);
    for (List<PosErrorLog> posErrorLog : posErrorLogByBill) {
      System.out.println(posErrorLog.size());
    }
  }

  @Test
  public void testDouble() {
    System.out.println((5 / 2.0));
    System.out.println((5 / 2));
    System.out.println((5.0 / 2));
    System.out.println((5.0 / 2.0));
    System.out.println(Math.ceil(5 / 2.0));
    System.out.println(new Double(Math.ceil(5 / 2.0)).intValue());
  }


  @Test
  public void testNull() {
    Integer ii = null;
    if(ii != null && 2 == ii){
      System.out.println("11111111");
    } else {
      System.out.println("2222222222");
    }
  }

  @Test
  public void testPage() {
    Long count = posOrderService.countErroPosDailyBill();
    Integer pageCount = posOrderService.pageCountErroPosDailyBill(3);
    System.out.println(count + "---------" + pageCount);
  }

  @Test
  public void shortMessagebalance() {
    try {
      URL url = new URL("http://183.203.28.226:9000/HttpSmsBalance");
      // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
      HttpURLConnection
          connection =
          (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中
      // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
      connection.setDoOutput(true);
      // 设置连接输入流为true
      connection.setDoInput(true);
      // 设置请求方式为post
      connection.setRequestMethod("POST");
      // post请求缓存设为false
      connection.setUseCaches(false);
      // 设置该HttpURLConnection实例是否自动执行重定向
      connection.setInstanceFollowRedirects(true);
      // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
      // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
      // ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】
      connection
          .setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

      // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
      connection.connect();
      // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
      DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
      Date date = new Date();
      String mttime00 = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
      String name = "name=" + URLEncoder.encode("jfk-yx", "utf-8");// 已修改【改为错误数据，以免信息泄露】
      String
          password1 =
          "&pwd=" + URLEncoder.encode("d41d8cd98f00b204e9800998ecf8427e",
                                      "utf-8");              // 已修改【改为错误数据，以免信息泄露】

      String rpttype = "&rpttype=" + URLEncoder.encode("1", "utf-8");
      // 格式 parm = aaa=111&bbb=222&ccc=333&ddd=444
      String parm = name + password1 + rpttype;
      // 将参数输出到连接
      dataout.writeBytes(parm);
      // 输出完成后刷新并关闭流
      dataout.flush();
      dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
      BufferedReader
          bf =
          new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
      String line;
      StringBuilder sb = new StringBuilder(); // 用来存储响应数据
      // 循环读取流,若不到结尾处
      while ((line = bf.readLine()) != null) {
//                sb.append(bf.readLine());
        sb.append(line).append(System.getProperty("line.separator"));
      }
      bf.close();    // 重要且易忽略步骤 (关闭流,切记!)
      connection.disconnect(); // 销毁连接
      System.out.println(sb.toString());
    } catch (Exception e) {

    }
  }

}
