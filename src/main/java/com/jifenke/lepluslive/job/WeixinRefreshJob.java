package com.jifenke.lepluslive.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wcg on 16/5/5.
 */
@Component
public class WeixinRefreshJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(WeixinRefreshJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    HashMap<String, String[]> apps = new HashMap<>();
//    String[] app1 = {"wxec4f3a2fb6ee8f06", "437e62266d3ff047ea0803b234ad0801", "7", "8"};
//    String[] app2 = {"wxec4f3a2fb6ee8f06", "437e62266d3ff047ea0803b234ad0801", "9", "10"};
    String[] app1 = {"wxe2190d22ce025e4f", "4a3f22e2ac05822b8b284e5a7c93b280", "7", "8"};
    String[] app2 = {"wx16edfa0dda02edd5", "2ff98b94441224bf584181e844a8af66", "9", "10"};
    apps.put("乐加生活", app1);
    apps.put("乐加支付", app2);

    ApplicationContext
        applicationContext = null;
    try {
      applicationContext =
          (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
    } catch (SchedulerException e) {
      log.error("jobExecutionContext.getScheduler().getContext() error!", e);
      throw new RuntimeException(e);
    }
    DictionaryService dictionaryService =
        (DictionaryService) applicationContext.getBean("dictionaryService");

    for (Map.Entry<String, String[]> entry : apps.entrySet()) {
      //获取并保存access_token
      String
          getUrl =
          "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + entry
              .getValue()[0]
          + "&secret=" + entry.getValue()[1];
      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(getUrl);
      httpGet.addHeader("Content-Type", "application/json;charset=utf8mb4");
      CloseableHttpResponse response = null;
      try {
        response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object>
            map =
            mapper
                .readValue(new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8")),
                           Map.class);
        EntityUtils.consume(entity);
        response.close();
        //  System.out.println(map.toString());
        if (map.get("errcode") == null) {
          dictionaryService.updateAccessToken((String) map.get("access_token"),
                                              Long.valueOf(entry.getValue()[2]));
          //更新并保存jsApiTicket
          getUrl =
              "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="
              + map.get("access_token");
          HttpGet httpGet2 = new HttpGet(getUrl);
          httpGet.addHeader("Content-Type", "application/json;charset=utf8mb4");
          CloseableHttpResponse response2 = httpClient.execute(httpGet2);
          HttpEntity entity2 = response2.getEntity();
          ObjectMapper mapper2 = new ObjectMapper();
          Map<String, Object>
              map2 =
              mapper2
                  .readValue(
                      new BufferedReader(new InputStreamReader(entity2.getContent(), "utf-8")),
                      Map.class);
          EntityUtils.consume(entity2);
          response2.close();
          httpClient.close();

          if (map2.get("errcode") == null || (Integer) map2.get("errcode") == 0) {
            dictionaryService
                .updateJsApiTicket((String) map2.get("ticket"), Long.valueOf(entry.getValue()[3]));
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}
