package com.mazhe.config;


import com.mazhe.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;



/**
 * 
* @ClassName: PayOrderSeq  
* @Description: TODO(生成全局唯一序列号工具类)  
* @author mazhe 
* @date 2017年12月15日  
*
 */

@Component
public class PayOrderSeq {

	private static AtomicLong order_seq = new AtomicLong(0L);
	private static String order_seq_prefix = "P";

	private static AtomicLong shop_seq = new AtomicLong(0L);
	private static String shop_seq_prefix = "S";

	private static String node = "01";

	public static String getNode() {
		return node;
	}

	@Value("${node}")
	public  void setNode(String node) {
		PayOrderSeq.node = node;
	}


	/**
	 * 唯一订单序列号
	 * @param mchid  动态可扩展参数
	 * @return
	 */
	public static String getOrderSeq(String mchid) {
		return getSeq(order_seq_prefix, order_seq,mchid);
	}

	public static String getShopSeq(String mchid) {
		return getSeq(shop_seq_prefix, shop_seq,mchid);
	}

	private static String getSeq(String prefix, AtomicLong seq,String mchid) {
		prefix += getNode();
		return String.format("%s%s%04d",prefix, DateUtil.format(new Date(),"yyyyMMdd")+getRandomNumberInRange(100000,999999), (int) seq.getAndIncrement() % 10000);
	}


	private static int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).findFirst().getAsInt();

	}

}