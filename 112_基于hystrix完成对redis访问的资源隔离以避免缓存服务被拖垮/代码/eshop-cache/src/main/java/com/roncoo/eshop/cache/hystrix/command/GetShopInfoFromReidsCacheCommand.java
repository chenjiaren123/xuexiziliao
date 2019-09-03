package com.roncoo.eshop.cache.hystrix.command;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.spring.SpringContext;

public class GetShopInfoFromReidsCacheCommand extends HystrixCommand<ShopInfo> {
	
	private Long shopId;
	
	public GetShopInfoFromReidsCacheCommand(Long shopId) {
		super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
		this.shopId = shopId;
	}
	
	@Override
	protected ShopInfo run() throws Exception {
		JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
				.getBean("JedisClusterFactory"); 
		String key = "shop_info_" + shopId;
		String json = jedisCluster.get(key);
		if(json != null) {
			return JSONObject.parseObject(json, ShopInfo.class);
		}
		return null;
	} 

}
