package com.jifenke.lepluslive.merchant.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.jifenke.lepluslive.merchant.controller.dto.BussineLicenseImgDto;
import com.jifenke.lepluslive.merchant.controller.dto.BussineProtocalDto;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantLicense;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantProtocol;
import com.jifenke.lepluslive.merchant.repository.MerchantLicenseRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantProtocolRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zxf on 2016/9/9. 图片文件 Service
 */
@Service
@Transactional(readOnly = true)
public class MerchantImageService {

  private OSSClient ossClient;

  @Value("${bucket.image}")
  private String imageBucket;

  @Value("${bucket.endpoint}")
  private String entryPoint;

  @Value("${bucket.ossAccessSecret}")
  private String ossAccessSecret;

  @Value("${bucket.ossAccessId}")
  private String ossAccessId;

  @Inject
  private MerchantLicenseRepository licenseRepository;

  @Inject
  private MerchantProtocolRepository protocolRepository;

  private OSSClient getOssClient() {
    if (ossClient == null) {
      synchronized (this) {
        if (ossClient == null) {
          ossClient = new OSSClient(entryPoint, ossAccessId, ossAccessSecret);
        }
      }
    }
    return ossClient;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveImage(MultipartFile fileData, String filePath, Integer type, Long imgType,
                        Long merchantId, Long index) throws IOException {
    //  将图片上传到云
    OSSClient ossClient = getOssClient();
    InputStream is = fileData.getInputStream();
    ObjectMetadata metaData = new ObjectMetadata();
    metaData.setContentLength(fileData.getSize());
    PutObjectResult putObject = ossClient.putObject(imageBucket, filePath, is, metaData);

    //  将新路径保存到数据库  type  0-执照  1-协议
    if (type == 0) {
      //  将之前该位置的图片状态置为不可用
      licenseRepository.updateLiceImgByMercIdAndTypeAndIndex(merchantId, imgType, index);
      //  新路径保存并置为可用
      MerchantLicense merchantLicense = new MerchantLicense();
      merchantLicense.setPicture(filePath);
      Merchant merchant = new Merchant();
      merchant.setId(merchantId);
      merchantLicense.setMerchant(merchant);
      merchantLicense.setLicenseType(imgType);
      merchantLicense.setPicState(0L);
      merchantLicense.setPicIndex(index);
      licenseRepository.save(merchantLicense);
    }
    if (type == 1) {
      //  将之前该位置的图片状态置为不可用
      protocolRepository.updateProtImgByMercIdAndTypeAndIndex(merchantId, imgType, index);
      //  新路径保存并置为可用
      MerchantProtocol merchantProtocol = new MerchantProtocol();
      merchantProtocol.setPicture(filePath);
      Merchant merchant = new Merchant();
      merchant.setId(merchantId);
      merchantProtocol.setMerchant(merchant);
      merchantProtocol.setProtocolType(imgType);
      merchantProtocol.setPicIndex(index);
      merchantProtocol.setPicState(0L);
      protocolRepository.save(merchantProtocol);
    }
  }

  @Transactional(readOnly = true)
  public BussineProtocalDto getProtImg(Long merchantId) {
    List<MerchantProtocol> list = protocolRepository.getProtImgByMerchantId(merchantId);
    BussineProtocalDto bpDto = new BussineProtocalDto();     // 图片类型:
    for (MerchantProtocol mp : list) {
      if (mp.getProtocolType() == 1) {                //  1-平台受理服务协议书
        if (mp.getPicIndex() == 0) {
          bpDto.setPlatServerProcImg1(mp.getPicture());
        }
        if (mp.getPicIndex() == 1) {
          bpDto.setPlatServerProcImg2(mp.getPicture());
        }
        if (mp.getPicIndex() == 2) {
          bpDto.setPlatServerProcImg3(mp.getPicture());
        }
        if (mp.getPicIndex() == 3) {
          bpDto.setPlatServerProcImg4(mp.getPicture());
        }
        if (mp.getPicIndex() == 4) {
          bpDto.setPlatServerProcImg5(mp.getPicture());
        }
      }
      if (mp.getProtocolType() == 2) {               //  2-商户基础资料表
        if (mp.getPicIndex() == 0) {
          bpDto.setMercBaseInfoImg1(mp.getPicture());
        }
        if (mp.getPicIndex() == 1) {
          bpDto.setMercBaseInfoImg2(mp.getPicture());
        }
      }
      if (mp.getProtocolType() == 3) {               // 3-中汇支付收单特约商户信息调查表
        if (mp.getPicIndex() == 0) {
          bpDto.setCnepaySpecialMercInfoImg1(mp.getPicture());
        }
        if (mp.getPicIndex() == 1) {
          bpDto.setCnepaySpecialMercInfoImg2(mp.getPicture());
        }
        if (mp.getPicIndex() == 2) {
          bpDto.setCnepaySpecialMercInfoImg3(mp.getPicture());
        }
      }
      if (mp.getProtocolType() == 4) {                // 4-结算授权书
        if (mp.getPicIndex() == 0) {
          bpDto.setAccountAuthorizationImg(mp.getPicture());
        }
      }
    }
    return bpDto;
  }


  @Transactional(readOnly = true)
  public BussineLicenseImgDto getLiceImg(Long merchantId) {
    BussineLicenseImgDto licDto = new BussineLicenseImgDto();
    List<MerchantLicense> list = licenseRepository.getLiceImgByMerchantId(merchantId);
    for (MerchantLicense lic : list) {             // 图片类型：

      if (lic.getLicenseType() == 1) {       // 1-营业执照
        licDto.setLicenseImg(lic.getPicture());
      }
      if (lic.getLicenseType() == 2) {       // 2-法人身份证
        licDto.setIdCardImg(lic.getPicture());
      }
      if (lic.getLicenseType() == 3) {       // 3-税务登记证
        licDto.setTaxRegistrationImg(lic.getPicture());
      }
      if (lic.getLicenseType() == 4) {       // 4-结算银行卡持有身份证
        licDto.setBankIdCardImg(lic.getPicture());
      }
      if (lic.getLicenseType() == 5) {       // 5-组织结构照
        licDto.setOrgConstructionImg(lic.getPicture());
      }
      if (lic.getLicenseType() == 6) {       // 6-结算银行卡
        licDto.setBankCardImg(lic.getPicture());
      }

    }
    return licDto;
  }

}
