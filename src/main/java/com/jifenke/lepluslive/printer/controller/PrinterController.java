package com.jifenke.lepluslive.printer.controller;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.printer.domain.MD5;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by lss on 16-11-11.
 */
@RestController
@RequestMapping("/manage")
public class PrinterController {
    private static String partner="5088";//用户id
    private	static String machine_code="4004515187";//打印机终端号
    private	static String apiKey="290558c570e64b3704953bd94afe3416a5656b0e";//API密钥
    private	static String mKey="wru7zwxywwnf";//打印机密钥
    @RequestMapping(value = "/printer", method = RequestMethod.GET)
    public ModelAndView printer() {
        String merchantName="棉花糖KTV";
        String merchantShop="棉花糖KTV(朝阳路)";
        String merchantNumber="1231144243253666";
        String orderNumer="13131144243253666";
        String tradeTime="2016.6.6 14:25:11";
        String payAmount="100.00";
        String scoreaPay="20";
        String weiXinPay="80";
        String orderKind="导流订单";
        String userSid="12131313133144";
        String scoreaSend="3.56";
        String scorebSend="56";

        StringBuffer sb = new StringBuffer("");

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
        sb.append("乐加会员ID:"+userSid+"\r");
        sb.append("奖励红包：<FB>"+"￥"+scoreaSend+"</FB>\r");
        sb.append("奖励积分：<FB>"+scorebSend+"积分"+"</FB>\r");
        sb.append("----------------------\r");
        sb.append("     扫码进入\"乐加生活公众号\"\r\n");
        sb.append("        立即购买<FB>超值商品</FB>\r\n");
        sb.append("          1积分=1块钱\n");
        //sb.append("<table><tr><td><center>扫码进入\"乐加生活公众号\"</center></td><td><center>立即购买<FB>超值商品</center></FB></td><td><center>1积分=1块钱</center></td></tr></table>");
        //sb.append("\r");
        sb.append("<QR>http://weixin.qq.com/r/gD_2rnnEBiR5rT3N92qS</QR>");

        //System.out.println(sb.toString());

        try{
            sendContent(sb.toString());//打印消息
            //sendRequest(sb.toString());//打印消息
        }catch(Exception e){
            e.printStackTrace();
        }
        return MvUtil.go("/index");
    }

    public boolean sendContent(String content){
        try{
            Map<String,String> params=new HashMap<String,String>();
            params.put("partner", partner);
            params.put("machine_code", machine_code);
            String time = String.valueOf(System.currentTimeMillis());
            params.put("time", time);
            String sign=signRequest(params);

            byte[] data = ("partner="+partner+"&machine_code="+machine_code+"&content="+content+"&sign="+sign+"&time="+time).getBytes();
            URL url = new URL("http://open.10ss.net:8888");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","text/html; charset=utf-8");
            conn.setRequestProperty("Content-Length",String.valueOf(data.length));

            //获取输出流
            OutputStream outStream = conn.getOutputStream();
            //传入参数
            outStream.write(data);
            outStream.flush();
            outStream.close();


            //获取输入流
            InputStream is = conn.getInputStream();

            //System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                int i = -1;
                byte[] b = new byte[1024];
                StringBuffer result = new StringBuffer();
                while ((i = is.read(b)) != -1) {
                    result.append(new String(b, 0, i));
                }






                String sub = result.toString();
                if(sub.equals("1")){//数据已经发送到客户端
                    System.out.println("打印成功");
                    return true;
                }else{
                    System.out.println("打印失败,返回值："+result);
                    return false;
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static String signRequest(Map<String,String> params){
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
        System.out.println(encryptStr);
        return encryptStr;
    }
}
