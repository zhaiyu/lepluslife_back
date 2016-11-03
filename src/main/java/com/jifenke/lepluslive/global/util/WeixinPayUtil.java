package com.jifenke.lepluslive.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.weixin.domain.entities.AccessToken;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by wcg on 16/3/21.
 */
public class WeixinPayUtil {

  public static AccessToken getAccessToken(Long wxId) {
    String
        getUrl =
        "http://www.lepluslife.com:8081/accessToken/" + wxId;
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(getUrl);
    httpGet.addHeader("Content-Type", "application/json;charset=utf8mb4");
    CloseableHttpResponse response = null;
    AccessToken accessToken = null;
    try {
      response = httpclient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      ObjectMapper mapper = new ObjectMapper();
      accessToken =
          mapper.readValue(new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8")),
                           AccessToken.class);
      EntityUtils.consume(entity);
      response.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return accessToken;
  }


  public static Map doXMLParse(String strxml) throws JDOMException, IOException {
    strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

    if (null == strxml || "".equals(strxml)) {
      return null;
    }

    Map m = new HashMap();

    InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(in);
    Element root = doc.getRootElement();
    List list = root.getChildren();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      Element e = (Element) it.next();
      String k = e.getName();
      String v = "";
      List children = e.getChildren();
      if (children.isEmpty()) {
        v = e.getTextNormalize();
      } else {
        v = getChildrenText(children);
      }

      m.put(k, v);
    }

    //关闭流
    in.close();

    return m;
  }


  public static String getChildrenText(List children) {
    StringBuffer sb = new StringBuffer();
    if (!children.isEmpty()) {
      Iterator it = children.iterator();
      while (it.hasNext()) {
        Element e = (Element) it.next();
        String name = e.getName();
        String value = e.getTextNormalize();
        List list = e.getChildren();
        sb.append("<" + name + ">");
        if (!list.isEmpty()) {
          sb.append(getChildrenText(list));
        }
        sb.append(value);
        sb.append("</" + name + ">");
      }
    }

    return sb.toString();
  }
}
