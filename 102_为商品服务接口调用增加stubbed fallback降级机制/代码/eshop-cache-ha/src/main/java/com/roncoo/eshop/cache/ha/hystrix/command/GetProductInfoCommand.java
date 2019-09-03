package com.roncoo.eshop.cache.ha.hystrix.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.roncoo.eshop.cache.ha.cache.local.BrandCache;
import com.roncoo.eshop.cache.ha.cache.local.LocationCache;
import com.roncoo.eshop.cache.ha.http.HttpClientUtils;
import com.roncoo.eshop.cache.ha.model.ProductInfo;

/**
 * 获取商品信息
 * @author Administrator
 *
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

	public static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");
	
	private Long productId;
	
	public GetProductInfoCommand(Long productId) {
//		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
//				.andCommandKey(KEY)
//				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
//				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
//						.withCoreSize(10)
//						.withMaxQueueSize(12)
//						.withQueueSizeRejectionThreshold(15)) 
//				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//						.withCircuitBreakerRequestVolumeThreshold(30)
//						.withCircuitBreakerErrorThresholdPercentage(40)
//						.withCircuitBreakerSleepWindowInMilliseconds(3000)
//						.withExecutionTimeoutInMilliseconds(500)
//						.withFallbackIsolationSemaphoreMaxConcurrentRequests(30))  
//				);  
		super(HystrixCommandGroupKey.Factory.asKey("ProductInfoServiceGroup"));
		this.productId = productId;
	}
	
	@Override
	protected ProductInfo run() throws Exception {
//		System.out.println("调用接口，查询商品数据，productId=" + productId); 
//		
		if(productId.equals(-1L)) {
			throw new Exception();
		}
//		
//		if(productId.equals(-2L)) {
//			Thread.sleep(3000);  
//		}
//		
//		if(productId.equals(-3L)) {
////			Thread.sleep(250); 
//		}
		
		String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
		String response = HttpClientUtils.sendGetRequest(url);
		return JSONObject.parseObject(response, ProductInfo.class);  
	}
	
//	@Override
//	protected String getCacheKey() {
//		return "product_info_" + productId;
//	}
	
	@Override
	protected ProductInfo getFallback() {
		ProductInfo productInfo = new ProductInfo();
		// 从请求参数中获取到的唯一条数据
		productInfo.setId(productId); 
		// 从本地缓存中获取一些数据
		productInfo.setBrandId(BrandCache.getBrandId(productId));
		productInfo.setBrandName(BrandCache.getBrandName(productInfo.getBrandId()));  
		productInfo.setCityId(LocationCache.getCityId(productId)); 
		productInfo.setCityName(LocationCache.getCityName(productInfo.getCityId()));  
		// 手动填充一些默认的数据
		productInfo.setColor("默认颜色");  
		productInfo.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		productInfo.setName("默认商品");  
		productInfo.setPictureList("default.jpg");  
		productInfo.setPrice(0.0);  
		productInfo.setService("默认售后服务");  
		productInfo.setShopId(-1L);  
		productInfo.setSize("默认大小");  
		productInfo.setSpecification("默认规格");   
		return productInfo;
	}
	
//	public static void flushCache(Long productId) {
//		HystrixRequestCache.getInstance(KEY,
//                HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
//	}
	
}
