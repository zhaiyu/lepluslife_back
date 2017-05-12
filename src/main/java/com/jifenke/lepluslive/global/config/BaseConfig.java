package com.jifenke.lepluslive.global.config;

import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.security.KeyStore;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;

/**
 * Created by wcg on 16/5/13.
 */
@Configuration
public class BaseConfig {

  @Inject
  private ResourceLoader resourceLoader;

  @Value("${leplusshop.mchId}")
  private String mchId;


  @Bean
  public SSLContext getTxSSLContext() {
    KeyStore keystore = null;
    SSLContext sslcontext = null;
    try {
      keystore = KeyStore.getInstance("PKCS12");
      keystore.load(resourceLoader.getResource("classpath:apiclient_cert.p12").getInputStream(),
                    mchId.toCharArray());
      sslcontext = SSLContexts.custom()
          .loadKeyMaterial(keystore, mchId.toCharArray())
          .build();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sslcontext;
  }


}
