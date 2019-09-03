package com.roncoo.eshop.cache.hystrix.command;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.spring.SpringContext;

public class GetProductInfoFromReidsCacheCommand extends HystrixCommand<ProductInfo> {

	private Long productId;
	
	public GetProductInfoFromReidsCacheCommand(Long productId) {
		super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
		this.productId = productId;
	}
	
	@Override
	protected ProductInfo run() throws Exception {
		JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
				.getBean("JedisClusterFactory"); 
		String key = "product_info_" + productId;
		String json = jedisCluster.get(key);
		if(json != null) {
			return JSONObject.parseObject(json, ProductInfo.class);
		}
		return null;
	}

}
