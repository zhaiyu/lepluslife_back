package com.jifenke.lepluslive.shortMessage.service;

import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.order.repository.OrderRepository;
import com.jifenke.lepluslive.shortMessage.domain.criteria.ShortMessageCriteria;
import com.jifenke.lepluslive.shortMessage.domain.entities.LeJiaUser_ShortMessage;
import com.jifenke.lepluslive.shortMessage.domain.entities.ReplyShortMessage;
import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessage;
import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessageScene;
import com.jifenke.lepluslive.shortMessage.repository.LeJiaUser_ShortMessageRepository;
import com.jifenke.lepluslive.shortMessage.repository.ReplyShortMessageRepository;
import com.jifenke.lepluslive.shortMessage.repository.ShortMessageRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lss on 2016/10/17.
 */
@Service
@Transactional(readOnly = false)
public class ShortMessageService {

  @Value("${shortMessage.userName}")
  private String userName;

  @Value("${shortMessage.password}")
  private String password;

  @Inject
  private ShortMessageRepository shortMessageRepository;

  @Inject
  private OffLineOrderRepository offLineOrderRepository;

  @Inject
  private OrderRepository orderRepository;

  @Inject
  private LeJiaUser_ShortMessageRepository leJiaUser_ShortMessageRepository;

  @Inject
  private ReplyShortMessageRepository replyShortMessageRepository;

  public static Specification<ShortMessage> getWhereClause(
      ShortMessageCriteria shortMessageCriteria) {
    return new Specification<ShortMessage>() {
      @Override
      public Predicate toPredicate(Root<ShortMessage> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (shortMessageCriteria.getReqCode() != null) {
          if (shortMessageCriteria.getReqCode().equals("00")) {
            predicate.getExpressions().add(
                cb.equal(r.get("reqCode"),
                         "00"));
          } else {
            predicate.getExpressions().add(
                cb.notEqual(r.get("reqCode"),
                            "00"));
          }
        }
        if (shortMessageCriteria.getShortMessageScene() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("shortMessageScene"),
                       shortMessageCriteria.getShortMessageScene()));
        }
        if (shortMessageCriteria.getCategory() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("shortMessageScene").get("category"),
                       shortMessageCriteria.getCategory()));
        }

        if (shortMessageCriteria.getStartDate() != null
            && shortMessageCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("sendDate"), new Date(shortMessageCriteria.getStartDate()),
                         new Date(shortMessageCriteria.getEndDate())));
        }

        return predicate;
      }
    };
  }

  @Transactional(readOnly = true)
  public Long findIdByReqId(String reqId) {
    return shortMessageRepository.findIdByReqId(reqId);
  }

  @Transactional(readOnly = true)
  public List<Object[]> findShortMessageDetailsById(Long shortMessageId) {
    return shortMessageRepository.findShortMessageDetailsById(shortMessageId);
  }

  @Transactional(readOnly = false)
  public boolean sendOneMessage(LeJiaUser leJiaUser, ShortMessageScene scene,
                                String messageContent) {
    try {
      URL url = new URL("http://60.205.14.180:9001/HttpSmsMt");
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
      String name = "name=" + URLEncoder.encode(userName, "utf-8");// 已修改【改为错误数据，以免信息泄露】
      String password1 = "&pwd=" + URLEncoder.encode(
          MD5Util.MD5Encode(password + mttime00, "UTF-8"),
          "utf-8");              // 已修改【改为错误数据，以免信息泄露】
      String phone = "&phone=" + URLEncoder.encode(leJiaUser.getPhoneNumber(), "utf-8");
      String content = "&content=" + URLEncoder.encode(messageContent, "utf-8");
      String mttime = "&mttime=" + URLEncoder.encode(mttime00, "utf-8");
      String rpttype = "&rpttype=" + URLEncoder.encode("1", "utf-8");
      // 格式 parm = aaa=111&bbb=222&ccc=333&ddd=444
      String parm = name + password1 + phone + content + mttime + rpttype;
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
      JSONObject json = JSONObject.fromObject(sb.toString());
      ShortMessage shortMessage = new ShortMessage();
      shortMessage.setContent(messageContent);
      shortMessage.setReqCode(json.get("ReqCode").toString());
      shortMessage.setReqid(json.get("ReqId").toString());
      shortMessage.setShortMessageScene(scene);
      shortMessage.setSendDate(date);
      shortMessage.setReqMsg(json.get("ReqMsg").toString());
      if (json.get("ReqCode").toString().equals("00")) {
        shortMessage.setUserAmount(1);
        shortMessageRepository.save(shortMessage);
        ShortMessage
            shortMessage2 = shortMessageRepository.findByreqId(shortMessage.getReqid());
        LeJiaUser_ShortMessage user_message = new LeJiaUser_ShortMessage();
        user_message.setLeJiaUser_id(leJiaUser.getId());
        user_message.setShortMessage_id(shortMessage2.getId());
        user_message.setState(1);
        leJiaUser_ShortMessageRepository.save(user_message);
        return true;
      } else {
        shortMessage.setUserAmount(1);
        shortMessageRepository.save(shortMessage);
        ShortMessage
            shortMessage2 = shortMessageRepository.findByreqId(shortMessage.getReqid());
        LeJiaUser_ShortMessage user_message = new LeJiaUser_ShortMessage();
        user_message.setLeJiaUser_id(leJiaUser.getId());
        user_message.setShortMessage_id(shortMessage2.getId());
        user_message.setState(0);
        user_message.setSendState(1);
        leJiaUser_ShortMessageRepository.save(user_message);
        return false;
      }
    } catch (Exception e) {
      return false;
    }
  }

  public Page findShortMessageByPage(ShortMessageCriteria shortMessageCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "sendDate");
    return shortMessageRepository
        .findAll(getWhereClause(shortMessageCriteria),
                 new PageRequest(shortMessageCriteria.getOffset() - 1, limit, sort));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveReplyShortMessage(ReplyShortMessage replyShortMessage) {
    replyShortMessageRepository.save(replyShortMessage);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveShortMessage(ShortMessage shortMessage) {
    shortMessageRepository.save(shortMessage);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object[]> findPublicVariable(String userSid) {
    return shortMessageRepository.findPublicVariable(userSid);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object> findRegistryScoreB(String userSid) {
    return shortMessageRepository.findRegistryScoreB(userSid);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object> findRegistryScoreA(String userSid) {
    return shortMessageRepository.findRegistryScoreA(userSid);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object> findShareScoreA(String userSid) {
    return shortMessageRepository.findShareScoreA(userSid);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object> findShareScoreB(String userSid) {
    return shortMessageRepository.findShareScoreB(userSid);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public OnLineOrder findOnLineOrderBySid(String sid) {
    return orderRepository.findByOrderSid(sid);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public OffLineOrder findOffLineOrderBySid(String sid) {
    return offLineOrderRepository.findOneByOrderSid(sid);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object[]> findOnLineOrderVariable(String onLineOrderSid) {
    return shortMessageRepository.findOnLineOrderVariable(onLineOrderSid);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Object[]> findOffLineOrderVariable(String offLineOrderSid) {
    return shortMessageRepository.findOffLineOrderVariable(offLineOrderSid);
  }
}
