package com.roncoo.eshop.cache.prewarm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.service.CacheService;
import com.roncoo.eshop.cache.spring.SpringContext;
import com.roncoo.eshop.cache.zk.ZooKeeperSession;

/**
 * 缓存预热线程
 * @author Administrator
 *
 */
public class CachePrewarmThread extends Thread {
	
	@Override
	public void run() {
		CacheService cacheService = (CacheService) SpringContext.
				getApplicationContext().getBean("cacheService"); 
		ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
		
		// 获取storm taskid列表
		String taskidList = zkSession.getNodeData("/taskid-list"); 
		
		if(taskidList != null && !"".equals(taskidList)) {
			String[] taskidListSplited = taskidList.split(",");  
			for(String taskid : taskidListSplited) {
				String taskidLockPath = "/taskid-lock-" + taskid;
				
				boolean result = zkSession.acquireFastFailedDistributedLock(taskidLockPath);
				if(!result) {
					continue;
				}
				
				String taskidStatusLockPath = "/taskid-status-lock-" + taskid;
				zkSession.acquireDistributedLock(taskidStatusLockPath);  
				
				String taskidStatus = zkSession.getNodeData("/taskid-status-" + taskid);
				
				if("".equals(taskidStatus)) {
					String productidList = zkSession.getNodeData("/task-hot-product-list-" + taskid);
					JSONArray productidJSONArray = JSONArray.parseArray(productidList);
					
					for(int i = 0; i < productidJSONArray.size(); i++) {
						Long productId = productidJSONArray.getLong(i);
						String productInfoJSON = "{\"id\": " + productId + ", \"name\": \"iphone7手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, \"modifiedTime\": \"2017-01-01 12:00:00\"}";
						ProductInfo productInfo = JSONObject.parseObject(productInfoJSON, ProductInfo.class);
						cacheService.saveProductInfo2LocalCache(productInfo);
						cacheService.saveProductInfo2ReidsCache(productInfo);  
					}
					
					zkSession.setNodeData(taskidStatusLockPath, "success");   
				}
				
				zkSession.releaseDistributedLock(taskidStatusLockPath);
				
				zkSession.releaseDistributedLock(taskidLockPath);
			}
		}
	}
	
}
