package com.mazhe.config;


import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.mazhe.util.DateUtil;
import org.springframework.stereotype.Component;



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

	private static AtomicLong pay_seq = new AtomicLong(0L);
	private static String pay_seq_prefix = "P";

	private static String node = "01";
	public static String getNode() {
		return node;
	}

	public static void setNode(String node) {
		PayOrderSeq.node = node;
	}


	public static String getPay(String mchid) {
		return getSeq(pay_seq_prefix, pay_seq,mchid);
	}

	private static String getSeq(String prefix, AtomicLong seq,String mchid) {
		prefix += getNode();
		return String.format("%s%04d", DateUtil.format(new Date(),"yyyyMMddHHmmss"), (int) seq.getAndIncrement() % 10000);
	}
}