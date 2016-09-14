package com.jifenke.lepluslive.merchant.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPosImage;
import com.jifenke.lepluslive.merchant.repository.MerchantPosImageRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;

import javax.inject.Inject;

/**
 * Created by xf on 2016/9/9. 图片文件 Service
 */
@Service
@Transactional(readOnly = true)
public class MerchantPosImageService {

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
  private MerchantPosImageRepository merchantPosImageRepostory;

  @Inject
  private MerchantRepository merchantRepository;

  private static String ossImageReadRoot = "http://lepluslive-image.oss-cn-beijing.aliyuncs.com/";

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
  public void saveImage(MultipartFile fileData, String filePath,Long merchantId,String fieldName) throws Exception {
    //  将图片上传到云
    OSSClient ossClient = getOssClient();
    InputStream is = fileData.getInputStream();
    ObjectMetadata metaData = new ObjectMetadata();
    metaData.setContentLength(fileData.getSize());
    PutObjectResult putObject = ossClient.putObject(imageBucket, filePath, is, metaData);
    // 将图片保存到数据库
    Merchant merchant = merchantRepository.findOne(merchantId);
    MerchantPosImage merchantImage = merchantPosImageRepostory.findByMerchant(merchant);
    if(merchantImage==null) {
      merchantImage = new MerchantPosImage();
      merchantImage.setMerchant(merchant);
    }
    String allFilePath = ossImageReadRoot+filePath;
    Class<? extends MerchantPosImage> clazz = merchantImage.getClass();
    Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(merchantImage,allFilePath);
    merchantPosImageRepostory.save(merchantImage);
  }

  // 根据商家 id 查询图片
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public MerchantPosImage findByMerchant(Long merchantId) {
    Merchant merchant = merchantRepository.getOne(merchantId);
    return merchantPosImageRepostory.findByMerchant(merchant);
  }


}
