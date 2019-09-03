package com.roncoo.eshop.cache.hystrix.command;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.spring.SpringContext;

public class SaveProductInfo2ReidsCacheCommand extends HystrixCommand<Boolean> {
	
	private ProductInfo productInfo;
	
	public SaveProductInfo2ReidsCacheCommand(ProductInfo productInfo) {
		super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
		this.productInfo = productInfo;
	}
	
	@Override
	protected Boolean run() throws Exception {
		JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
				.getBean("JedisClusterFactory"); 
		String key = "product_info_" + productInfo.getId();
		jedisCluster.set(key, JSONObject.toJSONString(productInfo));  
		return true;
	}  

}
