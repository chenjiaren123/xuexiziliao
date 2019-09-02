package com.roncoo.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.roncoo.eshop.cache.ha.cache.local.LocationCache;

/**
 * 获取城市名称的command
 * @author Administrator
 *
 */
public class GetCityNameCommand extends HystrixCommand<String> {

	private Long cityId;
	
	public GetCityNameCommand(Long cityId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
		        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
		               .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
		this.cityId = cityId;
	}
	
	@Override
	protected String run() throws Exception {
		return LocationCache.getCityName(cityId);
	}
	
}
