package com.jifenke.lepluslive.printer.service;

import com.google.common.collect.Lists;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.order.service.ScanCodeOrderService;
import com.jifenke.lepluslive.printer.domain.MD5;
import com.jifenke.lepluslive.printer.domain.criteria.ReceiptCriteria;
import com.jifenke.lepluslive.printer.domain.entities.Printer;
import com.jifenke.lepluslive.printer.domain.entities.Receipt;
import com.jifenke.lepluslive.printer.repository.PrinterRepository;
import com.jifenke.lepluslive.printer.repository.ReceiptRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by lss on 2016/12/27.
 */
@Service
public class ReceiptService {

  private static Logger log = LoggerFactory.getLogger(ReceiptService.class);

  @Inject
  private ReceiptRepository receiptRepository;

  @Inject
  private OffLineOrderRepository offLineOrderRepository;

  @Inject
  private PrinterRepository printerRepository;

  @Inject
  private ScanCodeOrderService scanCodeOrderService;

  @Value("${printer.partner}")
  private String partner;

  @Value("${printer.apiKey}")
  private String apiKey;

  @Value("${printer.username}")
  private String username;


  public static Specification<Receipt> getWhereClause(ReceiptCriteria receiptCriteria) {
    return new Specification<Receipt>() {
      @Override
      public Predicate toPredicate(Root<Receipt> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (receiptCriteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       receiptCriteria.getState()));
        }

        if (receiptCriteria.getOfflineOrderSid() != null
            && receiptCriteria.getOfflineOrderSid() != "") {
          predicate.getExpressions().add(
              cb.equal(r.get("orderSid"), receiptCriteria.getOfflineOrderSid()));
        }

        if (receiptCriteria.getMachineCode() != null && receiptCriteria.getMachineCode() != "") {
          predicate.getExpressions().add(
              cb.equal(r.<LeJiaUser>get("printer").get("machineCode"),
                       receiptCriteria.getMachineCode()));
        }

        if (receiptCriteria.getMerchantName() != null && receiptCriteria.getMerchantName() != "") {
          predicate.getExpressions().add(
              cb.like(r.<Printer>get("printer").get("merchant").get("name"),
                      "%" + receiptCriteria.getMerchantName() + "%"));
        }

        if (receiptCriteria.getReceiptSid() != null && receiptCriteria.getReceiptSid() != "") {
          predicate.getExpressions().add(
              cb.equal(r.<LeJiaUser>get("receiptSid"),
                       receiptCriteria.getReceiptSid()));
        }

        if (receiptCriteria.getStartDate() != null && receiptCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("completeDate"), new Date(receiptCriteria.getStartDate()),
                         new Date(receiptCriteria.getEndDate())));
        }

        return predicate;
      }
    };
  }


  public Page findReceiptByPage(ReceiptCriteria receiptCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "completeDate");
    return receiptRepository
        .findAll(getWhereClause(receiptCriteria),
                 new PageRequest(receiptCriteria.getOffset() - 1, limit, sort));
  }


  //打印上一次未成功订单
  @Transactional(propagation = Propagation.REQUIRED)
  public void printUnsuccessfulOrder(String sid) {
    Receipt unsuccessfulReceipt = receiptRepository.findBySid(sid);
    if (unsuccessfulReceipt != null) {
      if (unsuccessfulReceipt.getState() == 0) {
        Printer printer = unsuccessfulReceipt.getPrinter();
        addReceipt(unsuccessfulReceipt.getOrderSid(),
                   unsuccessfulReceipt.getType() == 1 ? "leJia" : "scan",
                   printer.getMerchant().getId(), 2);
      } else {
        log.info("重新打印失败---orderSid=" + unsuccessfulReceipt.getOrderSid());
      }
    }
  }


  private boolean sendSupplementReceipt(String content, Receipt receipt) {
    JSONObject obj = sendContent(content, receipt);
    if (obj != null) {
      if (obj.get("state").equals("1")) {//数据已经发送到客户端
        receipt.setState(4);
        receipt.setReceiptSid(obj.get("id").toString());
        receiptRepository.save(receipt);
        return true;
      } else {
        receipt.setState(4);
        receiptRepository.save(receipt);
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * 发送打印命令
   *
   * @param orderSid   订单号
   * @param orderType  订单类型  leJia=乐加|scan=通道
   * @param merchantId 门店ID
   * @param type       1=第一次打印|2=第二次打印
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void addReceipt(String orderSid, String orderType, Long merchantId, int type) {
    try {
      Printer printer = printerRepository.findPrinterByMerchantId(merchantId);
      if (printer != null) {
        if (printer.getState() == 1) {
          Receipt receipt = new Receipt();
          String sb = "";
          if ("scan".equals(orderType)) {//1=乐加|2=通道
            receipt.setType(2);
            ScanCodeOrder scanCodeOrder = scanCodeOrderService.findByOrderSid(orderSid);
            if (scanCodeOrder == null) {
              for (int i = 0; i < 10; i++) {
                Thread.sleep(2000);
                scanCodeOrder = scanCodeOrderService.findByOrderSid(orderSid);
                if (scanCodeOrder != null) {
                  break;
                }
              }
            }
            sb = createReceiptContent(scanCodeOrder, printer);
          } else {
            receipt.setType(1);
            OffLineOrder offLineOrder = offLineOrderRepository.findOneByOrderSid(orderSid);
            if (offLineOrder == null) {
              Thread.sleep(2000);
              offLineOrder = offLineOrderRepository.findOneByOrderSid(orderSid);
            }
            sb = createReceiptContent(offLineOrder, printer);
          }
          receipt.setCompleteDate(new Date());
          receipt.setOrderSid(orderSid);
          receipt.setPrinter(printer);
          if (type == 1) {
            sendFirsitContent(sb, receipt);
          } else {
            sendSupplementReceipt(sb, receipt);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String createReceiptContent(OffLineOrder offLineOrder, Printer printer) {
    Integer printerCount = 1;
    if (printer.getPrinterCount() != null) {
      printerCount = printer.getPrinterCount();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String merchantShop = offLineOrder.getMerchant().getName();
    String merchantNumber = offLineOrder.getMerchant().getMerchantSid();
    String orderNumer = offLineOrder.getOrderSid();
    String tradeTime = sdf.format(offLineOrder.getCompleteDate());

    String
        payAmount =
        String.valueOf(Double.valueOf(offLineOrder.getTotalPrice().toString()) / 100.0);
    String
        scoreaPay =
        String.valueOf(Double.valueOf(offLineOrder.getTrueScore().toString()) / 100.0);
    String weiXinPay = String.valueOf(Double.valueOf(offLineOrder.getTruePay().toString()) / 100.0);
    String orderKind = "普通订单";
    if (offLineOrder.getRebateWay() == 1 || offLineOrder.getRebateWay() == 3) {
      orderKind = "乐加订单";
    }
    String userSid = offLineOrder.getLeJiaUser().getUserSid();

    StringBuffer sb = new StringBuffer("");
    sb.append("<MN>" + printerCount + "</MN>");
    sb.append("----------------------\r");
    sb.append("商户名称：" + merchantShop + "\r");
    sb.append("商户编号：" + merchantNumber + "\r");
    sb.append("订单编号：" + orderNumer + "\r");
    sb.append("交易时间：" + tradeTime + "\r");
    sb.append("支付金额:\r");
    sb.append("<FS2>RMB" + "  " + payAmount + "</FS2>\r");
    sb.append("(使用鼓励金" + scoreaPay + "元,实付" + weiXinPay + "元)\r");
    sb.append("----------------------\r");
    sb.append("订单类型：" + orderKind + "\r");
    if (offLineOrder.getRebate() != null && offLineOrder.getRebate() > 0) {
      sb.append("乐加会员ID:" + userSid + "\r");
      sb.append("奖励鼓励金：<FB>" + "￥" + Double.valueOf(offLineOrder.getRebate().toString()) / 100.0
                + "</FB>\r");
    } else {
      sb.append("\r");
      sb.append("\r");
    }
    if (offLineOrder.getScoreC() != null && offLineOrder.getScoreC() > 0) {

      sb.append("奖励金币：<FB>" + Double.valueOf(offLineOrder.getScoreC().toString()) / 100.0 + "金币"
                + "</FB>\r");
    }
    sb.append("----------------------\r");
    sb.append("     扫码进入\"乐加生活公众号\"\r\n");
    sb.append("        立即购买<FB>超值商品</FB>\r\n");
    sb.append("          1金币=1块钱\n");
    sb.append("<QR>http://weixin.qq.com/r/gD_2rnnEBiR5rT3N92qS</QR>");
    return sb.toString();
  }


  private String createReceiptContent(ScanCodeOrder order, Printer printer) {
    Integer printerCount = 1;
    if (printer.getPrinterCount() != null) {
      printerCount = printer.getPrinterCount();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String merchantShop = order.getMerchant().getName();
    String merchantNumber = order.getMerchant().getMerchantSid();
    String orderNumer = order.getOrderSid();
    String
        tradeTime =
        sdf.format(order.getCompleteDate() == null ? new Date() : order.getCompleteDate());

    String
        payAmount =
        String.valueOf(Double.valueOf(order.getTotalPrice().toString()) / 100.0);
    String
        scorePay =
        String.valueOf(Double.valueOf(order.getTrueScore().toString()) / 100.0);
    String weiXinPay = String.valueOf(Double.valueOf(order.getTruePay().toString()) / 100.0);
    String orderKind = "普通订单";
    Long orderType = order.getOrderType();
    if (orderType == 12004L || orderType == 12005L) {
      orderKind = "乐加订单";
    }
    String userSid = order.getLeJiaUser().getUserSid();

    StringBuffer sb = new StringBuffer("");
    sb.append("<MN>" + printerCount + "</MN>");
    sb.append("----------------------\r");
    sb.append("商户门店：" + merchantShop + "\r");
    sb.append("商户编号：" + merchantNumber + "\r");
    sb.append("订单编号：" + orderNumer + "\r");
    sb.append("交易时间：" + tradeTime + "\r");
    sb.append("支付金额:\r");
    sb.append("<FS2>RMB" + "  " + payAmount + "</FS2>\r");
    sb.append("(使用鼓励金" + scorePay + "元,实付" + weiXinPay + "元)\r");
    sb.append("----------------------\r");
    sb.append("订单类型：" + orderKind + "\r");
    if (order.getRebate() != null && order.getRebate() > 0) {
      sb.append("乐加会员ID:" + userSid + "\r");
      sb.append("奖励鼓励金：<FB>" + "￥" + Double.valueOf(order.getRebate().toString()) / 100.0
                + "</FB>\r");
    } else {
      sb.append("\r");
      sb.append("\r");
    }
    if (order.getScoreC() != null && order.getScoreC() > 0) {
      sb.append("奖励金币：<FB>" + Double.valueOf(order.getScoreC().toString()) / 100.0 + "金币"
                + "</FB>\r");
    }
    sb.append("----------------------\r");
    sb.append("     扫码进入\"乐加生活公众号\"\r\n");
    sb.append("        立即购买<FB>超值商品</FB>\r\n");
    sb.append("          1金币=1块钱\n");
    sb.append("<QR>http://weixin.qq.com/r/gD_2rnnEBiR5rT3N92qS</QR>");
    return sb.toString();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public boolean sendFirsitContent(String content, Receipt receipt) {
    JSONObject obj = sendContent(content, receipt);
    if (obj != null) {
      if ("1".equals(obj.getString("state"))) {//数据已经发送到客户端
        receipt.setReceiptSid(obj.getString("id"));
        receiptRepository.save(receipt);
        return true;
      } else {
        receiptRepository.save(receipt);
        return false;
      }
    } else {
      return false;
    }
  }


  public JSONObject sendContent(String content, Receipt receipt) {
    String machine_code = receipt.getPrinter().getMachineCode();
    String mKey = receipt.getPrinter().getmKey();
    Map<String, String> params2 = new HashMap<>();
    params2.put("partner", partner);
    params2.put("machine_code", machine_code);
    String time = String.valueOf(System.currentTimeMillis());
    params2.put("time", time);
    String sign = signRequest(params2, mKey);
    //创建HttpClient对象
    CloseableHttpClient closeHttpClient = HttpClients.createDefault();
    CloseableHttpResponse httpResponse = null;
    //发送Post请求
    HttpPost httpPost = new HttpPost("http://open.10ss.net:8888");
    //设置Post参数
    List<NameValuePair> params = Lists.newArrayList();
    params.add(new BasicNameValuePair("partner", partner));
    params.add(new BasicNameValuePair("machine_code", machine_code));
    params.add(new BasicNameValuePair("content", content));
    params.add(new BasicNameValuePair("sign", sign));
    params.add(new BasicNameValuePair("time", time));
    try {
      //转换参数并设置编码格式
      httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
      //执行Post请求 得到Response对象
      httpResponse = closeHttpClient.execute(httpPost);
      //httpResponse.getStatusLine() 响应头信息
      System.out.println(httpResponse.getStatusLine());
      //返回对象 向上造型
      HttpEntity httpEntity = httpResponse.getEntity();
      if (httpEntity != null) {
        //响应输入流
        InputStream is = httpEntity.getContent();
        //转换为字符输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
        String line = null;
        while ((line = br.readLine()) != null) {
          JSONObject obj = new JSONObject().fromObject(line);
          return obj;
        }
        //关闭输入流
        is.close();
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (httpResponse != null) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (closeHttpClient != null) {
        try {
          closeHttpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }


  public String signRequest(Map<String, String> params, String mKey) {
    Map<String, String> sortedParams = new TreeMap<String, String>();
    sortedParams.putAll(params);
    Set<Map.Entry<String, String>> paramSet = sortedParams.entrySet();
    StringBuilder query = new StringBuilder();
    query.append(apiKey);
    for (Map.Entry<String, String> param : paramSet) {
      query.append(param.getKey());
      query.append(param.getValue());
    }
    query.append(mKey);
    String encryptStr = MD5.MD5Encode(query.toString()).toUpperCase();
    return encryptStr;
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Receipt findBySid(String sid) {
    return receiptRepository.findBySid(sid);
  }


  @Transactional(propagation = Propagation.REQUIRED)
  public void saveOne(Receipt receipt) {
    receiptRepository.save(receipt);
  }

}
