package com.coins.tradecoin.service;

import com.coins.tradecoin.consts.CoinConsts;
import com.coins.tradecoin.dao.UserMapper;
import com.coins.tradecoin.entity.UserPO;
import com.coins.tradecoin.enums.RequestType;
import com.coins.tradecoin.utils.EncryptUtil;
import com.coins.tradecoin.utils.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
@Data
public class BaseService {


	/** 火币账号在db中的主键id **/
	@Value("${trade.user.id}")
	private Integer userId;

	@Autowired
	private UserMapper userMapper;

	private String accountId;
	private String huobiAccessKey;
	private String huobiSecretKey;

	@PostConstruct
	private void struct() throws Exception {
		/** 根据账号信息，获取到账号有关的所有 AK SK apiurl信息 **/
		UserPO userPO= userMapper.selectByPrimaryKey(userId);
		huobiAccessKey=userPO.getHuobiAccessKey();
		huobiSecretKey=userPO.getHuobiSecretKey();
		accountId=userPO.getAccountId();
		/** 调用api查看account的详细信息 **/
		log.info(userId.toString());
		log.info("BaseService initiation finished");

	}

	public String post(Map<String,Object> map,String url) throws IOException {
		return HttpUtil.post(url, map, new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				int code = response.getStatusLine().getStatusCode();
				if(CoinConsts.SUCCESS_CODE == code) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				log.info("response code {}", code);
				return null;
			}
		});
	}


	/** 不设置时区，获取时间戳，其实获取的也是UTC下的时间戳 **/
	public long getUTCTimestamp(){
		return System.currentTimeMillis();
	}

	/** 获取utc下的时间字符串 **/
	public String getTimestamp(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("utc"));
        String dateStr= simpleDateFormat.format(date);
        String result= dateStr.replaceFirst(" ","T");
        log.info("return timestamp before format {}",result);
        return result;
    }

	/** 获取md5加密后的值 **/
	public String sign(LinkedHashMap<String,Object> map,String secretKey){

		StringBuilder stringBuffer=new StringBuilder();
		for (Map.Entry<String, Object> me : map.entrySet()) {
			stringBuffer.append(me.getKey()).append("=").append(me.getValue()).append("&");
		}
        return EncryptUtil.hmacSha256(stringBuffer.substring(0, stringBuffer.length() - 1),secretKey);
	}

	/** 获取signature **/
	public String getSignature(RequestType requestType,String urlPrefix,String urlSuffix, TreeMap<String,Object> map){
		map.put("AccessKeyId",huobiAccessKey);
		map.put("SignatureMethod","HmacSHA256");
		map.put("SignatureVersion","2");
		map.put("Timestamp",getTimestamp());

		/** 拼接整个加密之前的串 **/
        StringBuilder stringBuffer=new StringBuilder();
        /** 请求类型 **/
        stringBuffer.append(requestType.getName()).append("\n");
        /** url前缀 **/
        stringBuffer.append(urlPrefix.replaceFirst("https://","")).append("\n");
        /** url后缀 **/
        stringBuffer.append(urlSuffix).append("\n");

        /** 拼接请求参数 **/
        StringBuilder sb=new StringBuilder();
        for (Map.Entry<String, Object> me : map.entrySet()) {
        	/** 需要对所有的k-v对进行URI编码 **/
            sb.append(me.getKey()).append("=").append(URLEncoder.encode(me.getValue().toString())).append("&");
        }
        String message=stringBuffer.append(sb.substring(0, sb.length() - 1)).toString();
        return EncryptUtil.hmacSha256(message,huobiSecretKey);
    }


}
