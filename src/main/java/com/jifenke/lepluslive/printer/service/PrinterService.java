package com.jifenke.lepluslive.printer.service;

import com.google.common.collect.Lists;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.printer.domain.MD5;
import com.jifenke.lepluslive.printer.domain.criteria.PrinterCriteria;
import com.jifenke.lepluslive.printer.domain.entities.Printer;
import com.jifenke.lepluslive.printer.repository.PrinterRepository;

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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
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
 * Created by lss on 16-12-22.
 */
@Service
@Transactional(readOnly = false)
public class PrinterService {
    @Inject
    private PrinterRepository printerRepository;

    @Inject
    private MerchantRepository merchantRepository;

    @Value("${printer.partner}")
    private String partner;

    @Value("${printer.apiKey}")
    private String apiKey;

    @Value("${printer.username}")
    private String username;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addPrinter(Printer printer) {
                                                                              //易联云添加打印机接口
        Map<String,String> params=new HashMap<String,String>();
        String machine_code=printer.getMachineCode();
        String msign=printer.getmKey();                                     //打印机密钥
        String  mobilephone="";                                            //打印机内的手机号
        String  printname=printer.getName();                              //打印机名称

        params.put("partner",partner);
        params.put("machine_code",machine_code);
        params.put("username",username);
        params.put("printname",printname);
        params.put("mobilephone",mobilephone);

        String sign=signRequest(params,msign);
        params.put("sign",msign);
        boolean b =addM(params,msign);
                                                                //添加打印机到后台或者返回错误
        if(b){
            printer.setCreateDate(new Date());

            Merchant merchant=merchantRepository.findById(printer.getMerchant().getName());
            printer.setMerchant(merchant);
            if(merchant.getMerchantUser()!=null){
                printer.setMerchantUser(merchant.getMerchantUser());
            }
            printer.setState(1);
            printerRepository.save(printer);
            return true;
        }else {
            return false;
        }

    }
    public  String signRequest(Map<String,String> params,String msign){
        Map<String,String> sortedParams=new TreeMap<String,String>();
        sortedParams.putAll(params);
        Set<Map.Entry<String,String>> paramSet=sortedParams.entrySet();
        StringBuilder query=new StringBuilder();
        query.append(apiKey);
        for (Map.Entry<String, String> param:paramSet) {
            query.append(param.getKey());
            query.append(param.getValue());
        }
        query.append(msign);
        String encryptStr= MD5.MD5Encode(query.toString()).toUpperCase();
        return encryptStr;
    }



    public boolean addM(Map<String,String> params3,String msign) {
        //创建HttpClient对象
        CloseableHttpClient closeHttpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        //发送Post请求
        HttpPost httpPost = new HttpPost("http://open.10ss.net:8888/addprint.php");
        //设置Post参数
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("partner",partner));
        params.add(new BasicNameValuePair("machine_code",params3.get("machine_code")));
        params.add(new BasicNameValuePair("username",params3.get("username")));
        params.add(new BasicNameValuePair("printname",params3.get("printname")));
        params.add(new BasicNameValuePair("mobilephone",params3.get("mobilephone")));
        params.add(new BasicNameValuePair("msign",msign));
        params.add(new BasicNameValuePair("sign",params3.get("sign")));
        try {
            //转换参数并设置编码格式
            httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            //执行Post请求 得到Response对象
            httpResponse = closeHttpClient.execute(httpPost);
            //httpResponse.getStatusLine() 响应头信息
            System.out.println(httpResponse.getStatusLine());
            //返回对象 向上造型
            HttpEntity httpEntity = httpResponse.getEntity();
            if(httpEntity != null){
                //响应输入流
                InputStream is = httpEntity.getContent();
                //转换为字符输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is,Consts.UTF_8));
                String line = null;
                while((line=br.readLine())!=null){
                    if(line.equals("1")){
                        return true;
                    }

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
        }finally{
            if(httpResponse != null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(closeHttpClient != null){
                try {
                    closeHttpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }





    public static Specification<Printer> getWhereClause(PrinterCriteria printerCriteria) {
        return new Specification<Printer>() {
            @Override
            public Predicate toPredicate(Root<Printer> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (printerCriteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("state"),
                                    printerCriteria.getState()));
                }



                return predicate;
            }
        };
    }

    public Page findAllByPage(PrinterCriteria printerCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return printerRepository
                .findAll(getWhereClause(printerCriteria),
                        new PageRequest(printerCriteria.getOffset() - 1, limit, sort));
    }



    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void changePrinter(String printerId) {
        Printer printer=printerRepository.findOne(Long.valueOf(printerId));
        if(printer.getState()==0){
            printer.setState(1);
        } else {
            printer.setState(0);
        }
        printerRepository.save(printer);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Printer findOne(String printerId) {
        Printer printer=printerRepository.findOne(Long.valueOf(printerId));
        return printer;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean editPrinter(Printer printer) {
       boolean a=deletePrinter(printer);
        if(a){
            boolean b=addPrinter(printer);
            if(b){
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }



    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean deletePrinter(Printer printer) {
        Map<String,String> params=new HashMap<String,String>();
        String machine_code=printer.getMachineCode();
        String msign=printer.getmKey();
        params.put("machine_code",machine_code);
        params.put("partner",partner);
        String sign=signRequest(params,msign);
        params.put("sign",sign);
        boolean b=deleteM(params);
        if(b){
            return true;
        }else {
            return false;
        }

    }


    public boolean deleteM(Map<String,String> params){
        try{
            String parameter="partner="+params.get("partner")+"&machine_code="+params.get("machine_code")+"&sign="+params.get("sign");
            byte[] data = (parameter).getBytes();
            URL url = new URL("http://open.10ss.net:8888/removeprint.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Content-Length",String.valueOf(data.length));
            OutputStream outStream = conn.getOutputStream();
            outStream.write(data);
            outStream.flush();
            outStream.close();
            InputStream is = conn.getInputStream();
            if (conn.getResponseCode() == 200) {
                int i = -1;
                byte[] b = new byte[1024];
                StringBuffer result = new StringBuffer();
                while ((i = is.read(b)) != -1) {
                    result.append(new String(b, 0, i));
                }
                String sub = result.toString();
                System.out.println(sub);
                if(sub.equals("1")){//删除成功
                    return true;
                }else{
                    return false;
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
