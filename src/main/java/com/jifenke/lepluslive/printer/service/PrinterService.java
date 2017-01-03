package com.jifenke.lepluslive.printer.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.printer.domain.MD5;
import com.jifenke.lepluslive.printer.domain.criteria.PrinterCriteria;
import com.jifenke.lepluslive.printer.domain.entities.Printer;
import com.jifenke.lepluslive.printer.repository.PrinterRepository;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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

    public boolean addM(Map<String,String> params,String msign){
        try{
            byte[] data = ("partner="+partner+"&machine_code="+params.get("machine_code")+"&username="+params.get("username")+"&printname="+params.get("printname")+"&mobilephone="+params.get("mobilephone")+"&msign="+msign+"&sign="+params.get("sign")).getBytes();
            URL url = new URL("http://open.10ss.net:8888/addprint.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","text/html; charset=utf-8");
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
                if(sub.equals("1")){//数据已经发送到客户端
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
            byte[] data = ("partner="+params.get("partner")+"&machine_code="+params.get("machine_code")+"&sign="+params.get("sign")).getBytes();
            URL url = new URL("http://open.10ss.net:8888/removeprint.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","text/html; charset=utf-8");
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
