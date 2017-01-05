package com.jifenke.lepluslive.printer.service;

import com.google.common.collect.Lists;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.printer.domain.MD5;
import com.jifenke.lepluslive.printer.domain.criteria.ReceiptCriteria;
import com.jifenke.lepluslive.printer.domain.entities.MeasurementUrl;
import com.jifenke.lepluslive.printer.domain.entities.Printer;
import com.jifenke.lepluslive.printer.domain.entities.Receipt;
import com.jifenke.lepluslive.printer.repository.MeasurementRepository;
import com.jifenke.lepluslive.printer.repository.PrinterRepository;
import com.jifenke.lepluslive.printer.repository.ReceiptRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import net.sf.json.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lss on 2016/12/27.
 */
@Service
@Transactional(readOnly = false)
public class ReceiptService {
    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private OffLineOrderRepository offLineOrderRepository;

    @Inject
    private PrinterRepository printerRepository;


    @Inject
    private MeasurementRepository measurementRepository;

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

                if (receiptCriteria.getOfflineOrderSid() != null && receiptCriteria.getOfflineOrderSid() != "") {
                    predicate.getExpressions().add(
                            cb.equal(r.<LeJiaUser>get("offLineOrder").get("orderSid"),
                                     receiptCriteria.getOfflineOrderSid()));
                }

                if (receiptCriteria.getMachineCode() != null && receiptCriteria.getMachineCode() != "") {
                    predicate.getExpressions().add(
                            cb.equal(r.<LeJiaUser>get("printer").get("machineCode"),
                                    receiptCriteria.getMachineCode()));
                }


                if (receiptCriteria.getMerchantName() != null && receiptCriteria.getMerchantName() != "") {
                    predicate.getExpressions().add(
                            cb.like(r.<LeJiaUser>get("offLineOrder").get("merchant").get("name"),
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public  void printUnsuccessfulOrder(String machine_code){
       Printer printer= printerRepository.findPrinterByMerchantCode(machine_code);
            Receipt unsuccessfulReceipt =receiptRepository.printUnsuccessfulOrder(printer.getId());
            if(unsuccessfulReceipt!=null){
            OffLineOrder offLineOrder=unsuccessfulReceipt.getOffLineOrder();
            String sb=createReceiptContent(offLineOrder,printer);
            Receipt receipt=new Receipt();
            receipt.setCompleteDate(new Date());
            receipt.setOffLineOrder(offLineOrder);
            receipt.setPrinter(printer);
            boolean b=sendSupplementReceipt(sb,receipt);
            if(b){
                unsuccessfulReceipt.setState(1);
                receiptRepository.save(unsuccessfulReceipt);
            }

        }


    }



    public  boolean sendSupplementReceipt(String content,Receipt receipt){
        JSONObject obj=sendContent(content,receipt);
        if(obj!=null){
            if(obj.get("state").equals("1")){//数据已经发送到客户端
                receipt.setState(4);
                receipt.setReceiptSid(obj.get("id").toString());
                receiptRepository.save(receipt);
                return true;
            }else{
                receipt.setState(4);
                receiptRepository.save(receipt);
                return false;
            }
        }else {
            return  false;
        }
    }


    public  void addReceipt(String  paramms){
        String a[] = paramms.split("&");
        String  orderSid= a[0].split("=")[1];
        String  sign= a[1].split("=")[1];
        OffLineOrder offLineOrder =offLineOrderRepository.findOneByOrderSid(orderSid);
        Long merchantId=offLineOrder.getMerchant().getId();
        Printer printer  =printerRepository.findPrinterByMerchantId(merchantId);
        if(printer!=null){
            if( printer.getState()==1){
                String sb=createReceiptContent(offLineOrder,printer);
                Receipt receipt=new Receipt();
                receipt.setCompleteDate(new Date());
                receipt.setOffLineOrder(offLineOrder);
                receipt.setPrinter(printer);
                boolean b=sendFirsitContent(sb,receipt);
            }
        }
    }

    public  String createReceiptContent(OffLineOrder offLineOrder, Printer printer){
        Integer printerCount=1;
        if(printer.getPrinterCount()!=null){
            printerCount=printer.getPrinterCount();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String merchantName="----";
        if(offLineOrder.getMerchant().getMerchantUser()!=null&&offLineOrder.getMerchant().getMerchantUser().getMerchantName()!=null){
            merchantName=offLineOrder.getMerchant().getMerchantUser().getMerchantName();
        }
        String merchantShop=offLineOrder.getMerchant().getName();
        String merchantNumber=offLineOrder.getMerchant().getMerchantSid();
        String orderNumer=offLineOrder.getOrderSid();
        String tradeTime=sdf.format(offLineOrder.getCompleteDate());
        Long totalPrice=offLineOrder.getTotalPrice();
        if(totalPrice==null){
            totalPrice=0L;
        }
        Long trueScore=offLineOrder.getTrueScore();
        if(trueScore==null){
            trueScore=0L;
        }
        Long truePay=offLineOrder.getTruePay();
        if(truePay==null){
            truePay=0L;
        }
        Long rebate=offLineOrder.getRebate();
        if(rebate==null){
            rebate=0L;
        }
        Long scoreB=offLineOrder.getScoreB();
        if(scoreB==null){
            scoreB=0L;
        }
        String payAmount=String.valueOf(Double.valueOf(offLineOrder.getTotalPrice().toString())/100.0);
        String scoreaPay=String.valueOf(Double.valueOf(offLineOrder.getTrueScore().toString())/100.0);
        String weiXinPay=String.valueOf(Double.valueOf(offLineOrder.getTruePay().toString())/100.0);
        String orderKind="";
        if(offLineOrder.getRebateWay()==1){
             orderKind="导流订单";
        }else if(offLineOrder.getRebateWay()==3){
             orderKind="会员订单";
        }else if (offLineOrder.getRebateWay()==6){
             orderKind="会员订单";
        }else{
             orderKind="普通订单";
        }
        String userSid=offLineOrder.getLeJiaUser().getUserSid();
        String scoreaSend=String.valueOf(Double.valueOf(offLineOrder.getRebate().toString())/100.0);
        String scorebSend=offLineOrder.getScoreB().toString();

        StringBuffer sb = new StringBuffer("");
        sb.append("<MN>"+printerCount+"</MN>");
        sb.append("----------------------\r");
        sb.append("商户名称："+merchantName+"\r");
        sb.append("商户门店："+merchantShop+"\r");
        sb.append("商户编号："+merchantNumber+"\r");
        sb.append("订单编号："+orderNumer+"\r");
        sb.append("交易时间："+tradeTime+"\r");
        sb.append("支付金额:\r");
        sb.append("<FS2>RMB"+"  "+payAmount+"</FS2>\r");
        sb.append("(使用红包"+scoreaPay+"元,微信支付"+weiXinPay+"元)\r");
        sb.append("----------------------\r");
        sb.append("订单类型："+orderKind+"\r");
        if(offLineOrder.getRebateWay()==1||offLineOrder.getRebateWay()==3||offLineOrder.getRebateWay()==6){
            sb.append("乐加会员ID:"+userSid+"\r");
            sb.append("奖励红包：<FB>"+"￥"+scoreaSend+"</FB>\r");
        }else {
            sb.append("\r");
            sb.append("\r");
        }

        sb.append("奖励积分：<FB>"+scorebSend+"积分"+"</FB>\r");
        sb.append("----------------------\r");
        sb.append("     扫码进入\"乐加生活公众号\"\r\n");
        sb.append("        立即购买<FB>超值商品</FB>\r\n");
        sb.append("          1积分=1块钱\n");
        sb.append("<QR>http://weixin.qq.com/r/gD_2rnnEBiR5rT3N92qS</QR>");
        String encode=null;
        try {
            encode =URLEncoder.encode(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean sendFirsitContent(String content,Receipt receipt){
            JSONObject obj=sendContent(content,receipt);
        sendContent1(content,receipt);
        sendContent2(content,receipt);
        sendContent3(content,receipt);
        sendContent4(content,receipt);
        sendContent5(content,receipt);
        if(obj!=null){
            if(obj.get("state").equals("1")){//数据已经发送到客户端
                receipt.setState(0);
                receipt.setReceiptSid(obj.get("id").toString());
                receiptRepository.save(receipt);
                return true;
            }else{
                receipt.setState(0);
                receiptRepository.save(receipt);
                return false;
            }
        }else {
            return  false;
        }
    }

    public JSONObject sendContent(String content,Receipt receipt){
        try {
            MeasurementUrl measurementUrl=measurementRepository.findUrlByName("sendContentUrl");
            String machine_code = receipt.getPrinter().getMachineCode();
            String mKey = receipt.getPrinter().getmKey();
            Map<String, String> params = new HashMap<String, String>();
            params.put("partner", partner);
            params.put("machine_code", machine_code);
            String time = String.valueOf(System.currentTimeMillis());
            params.put("time", time);
            String sign = signRequest(params, mKey);
            String parameter="partner=" + partner + "&machine_code=" + machine_code + "&content=" + content + "&sign=" + sign + "&time=" + time;
            byte[] data = (parameter).getBytes();
            URL url = new URL(measurementUrl.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获取输出流
            OutputStream outStream = conn.getOutputStream();
            //传入参数
            outStream.write(data);
            outStream.flush();
            outStream.close();
            //获取输入流
            InputStream is = conn.getInputStream();

            if (conn.getResponseCode() == 200) {
                int i = -1;
                byte[] b = new byte[1024];
                StringBuffer result = new StringBuffer();
                while ((i = is.read(b)) != -1) {
                    result.append(new String(b, 0, i));
                }
                String sub = result.toString();
                JSONObject obj = new JSONObject().fromObject(sub);
                return obj;
            }
        }catch (Exception e){
            return null;
        }
             return  null;
    }
//测试第一种情况
    public JSONObject sendContent1(String content,Receipt receipt){
        try {
            MeasurementUrl measurementUrl=measurementRepository.findUrlByName("ceshi1");
            String machine_code = receipt.getPrinter().getMachineCode();
            String mKey = receipt.getPrinter().getmKey();
            Map<String, String> params = new HashMap<String, String>();
            params.put("partner", partner);
            params.put("machine_code", machine_code);
            String time = String.valueOf(System.currentTimeMillis());
            params.put("time", time);
            String sign = signRequest(params, mKey);
            String parameter="partner=" + partner + "&machine_code=" + machine_code + "&content=" + content + "&sign=" + sign + "&time=" + time;
            byte[] data = (parameter).getBytes("UTF-8");
            URL url = new URL(measurementUrl.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获取输出流
            OutputStream outStream = conn.getOutputStream();
            //传入参数
            outStream.write(data);
            outStream.flush();
            outStream.close();
            //获取输入流
            InputStream is = conn.getInputStream();

            if (conn.getResponseCode() == 200) {
                int i = -1;
                byte[] b = new byte[1024];
                StringBuffer result = new StringBuffer();
                while ((i = is.read(b)) != -1) {
                    result.append(new String(b, 0, i));
                }
                String sub = result.toString();
                JSONObject obj = new JSONObject().fromObject(sub);
                return obj;
            }
        }catch (Exception e){
            return null;
        }
        return  null;
    }
    //测试第二种情况
    public JSONObject sendContent2(String content,Receipt receipt) {
        try {
            MeasurementUrl measurementUrl = measurementRepository.findUrlByName("ceshi2");
            URL url = new URL(measurementUrl.getUrl());
            HttpURLConnection
                    connection =
                    (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection
                    .setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            connection.connect();
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());

            String machine_code = receipt.getPrinter().getMachineCode();
            String mKey = receipt.getPrinter().getmKey();
            Map<String, String> params = new HashMap<String, String>();
            params.put("partner", partner);
            params.put("machine_code", machine_code);
            String time = String.valueOf(System.currentTimeMillis());
            params.put("time", time);
            String sign = signRequest(params, mKey);

            Date date = new Date();
            String partner3 = "partner=" + URLEncoder.encode(partner, "utf-8");
            String machine_code3 = "&machine_code=" + URLEncoder.encode(machine_code, "utf-8");
            String content3 = "&content=" + content;
            String sign3 = "&sign=" + URLEncoder.encode(sign, "utf-8");
            String time3 = "&time=" + URLEncoder.encode(time, "utf-8");

            String parm = partner3 + machine_code3 + content3 + sign3 + time3;


            dataout.writeBytes(parm);
            dataout.flush();
            dataout.close();
            BufferedReader
                    bf =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();
            connection.disconnect();
            System.out.println(sb.toString());
            JSONObject json = JSONObject.fromObject(sb.toString());
            return json;

        } catch (Exception e) {
            return null;
        }
    }
    //测试第三种情况
    public JSONObject sendContent3(String content,Receipt receipt) {
        try {
            MeasurementUrl measurementUrl = measurementRepository.findUrlByName("ceshi3");
            URL url = new URL(measurementUrl.getUrl());
            HttpURLConnection
                    connection =
                    (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection
                    .setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            connection.connect();
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());

            String machine_code = receipt.getPrinter().getMachineCode();
            String mKey = receipt.getPrinter().getmKey();
            Map<String, String> params = new HashMap<String, String>();
            params.put("partner", partner);
            params.put("machine_code", machine_code);
            String time = String.valueOf(System.currentTimeMillis());
            params.put("time", time);
            String sign = signRequest(params, mKey);

            Date date = new Date();
            String partner3 = "partner=" + URLEncoder.encode(partner, "utf-8");
            String machine_code3 = "&machine_code=" + URLEncoder.encode(machine_code, "utf-8");
            String content3 = "&content=" + URLEncoder.encode(content, "utf-8");
            String sign3 = "&sign=" + URLEncoder.encode(sign, "utf-8");
            String time3 = "&time=" + URLEncoder.encode(time, "utf-8");

            String parm = partner3 + machine_code3 + content3 + sign3 + time3;


            dataout.writeBytes(parm);
            dataout.flush();
            dataout.close();
            BufferedReader
                    bf =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();
            connection.disconnect();
            System.out.println(sb.toString());
            JSONObject json = JSONObject.fromObject(sb.toString());
            return json;

        } catch (Exception e) {
            return null;
        }
    }

    //测试第四种情况
    public JSONObject sendContent4(String content,Receipt receipt) {
        MeasurementUrl measurementUrl = measurementRepository.findUrlByName("ceshi4");
        String machine_code = receipt.getPrinter().getMachineCode();
        String mKey = receipt.getPrinter().getmKey();
        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("partner", partner);
        params2.put("machine_code", machine_code);
        String time = String.valueOf(System.currentTimeMillis());
        params2.put("time", time);
        String sign = signRequest(params2, mKey);
        //创建HttpClient对象
        CloseableHttpClient closeHttpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        //发送Post请求
        HttpPost httpPost = new HttpPost(measurementUrl.getUrl());
        //设置Post参数
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("partner",partner));
        params.add(new BasicNameValuePair("machine_code",machine_code));
        params.add(new BasicNameValuePair("content",content));
        params.add(new BasicNameValuePair("sign",sign));
        params.add(new BasicNameValuePair("time",time));
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
        return null;
    }
    //第五种情况
    public JSONObject sendContent5(String content,Receipt receipt) {
        MeasurementUrl measurementUrl = measurementRepository.findUrlByName("ceshi5");
        String machine_code = receipt.getPrinter().getMachineCode();
        String mKey = receipt.getPrinter().getmKey();
        Map<String, String> params = new HashMap<String, String>();

        String time = String.valueOf(System.currentTimeMillis());


        params.put("partner", partner);
        params.put("machine_code", machine_code);
        params.put("time", time);
        String sign = signRequest(params, mKey);
        params.put("content",content);
        params.put("sign",sign);

        try {
            HttpPost httpPost = new HttpPost(measurementUrl.getUrl());
            HttpClient client = new DefaultHttpClient();
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                valuePairs.add(nameValuePair);
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "UTF-8");
            httpPost.setEntity(formEntity);
            HttpResponse resp = client.execute(httpPost);

            HttpEntity entity = resp.getEntity();
            String respContent = EntityUtils.toString(entity, "UTF-8").trim();
            httpPost.abort();
            client.getConnectionManager().shutdown();
            JSONObject obj  = new JSONObject().fromObject(respContent);
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public  String signRequest(Map<String,String> params,String mKey){
        Map<String,String> sortedParams=new TreeMap<String,String>();
        sortedParams.putAll(params);
        Set<Map.Entry<String,String>> paramSet=sortedParams.entrySet();
        StringBuilder query=new StringBuilder();
        query.append(apiKey);
        for (Map.Entry<String, String> param:paramSet) {
            query.append(param.getKey());
            query.append(param.getValue());
        }
        query.append(mKey);
        String encryptStr= MD5.MD5Encode(query.toString()).toUpperCase();
        return encryptStr;
    }



    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Receipt findBySid(String sid) {
         return receiptRepository.findBySid(sid);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOne(Receipt receipt) {
         receiptRepository.save(receipt);
    }

}
