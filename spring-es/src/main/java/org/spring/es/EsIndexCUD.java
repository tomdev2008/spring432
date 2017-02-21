package org.spring.es;
//package org.spring.es;
//
//import org.elasticsearch.action.ActionListener;
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
//import org.elasticsearch.index.reindex.DeleteByQueryAction;
//import org.junit.Test;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * es 索引增删改
// * 
// * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-update.html
// * 
// * @author gaotingping
// *
// *         2017年2月4日 下午2:18:37
// */
//public class EsIndexCUD2 extends EsBase {
//
//	/**
//	 * 批量添加增加
//	 */
//	@Test
//	public void batchAddDoc() {
//
//		BulkRequestBuilder bulkRequestBuilder = c.prepareBulk();
//
//		for (int i = 0; i < 5; i++) {
//			JSONObject data = new JSONObject();
//			data.put("name", "name_" + i);
//			data.put("age", i);
//			data.put("pid", 5 + i);
//			bulkRequestBuilder
//					.add(c.prepareIndex("index_test_1", "test1").setId("id_" + i).setSource(data.toJSONString()));
//		}
//
//		BulkResponse bulkResponse = bulkRequestBuilder.get();
//
//		bulkRequestBuilder.request().requests().clear();
//
//		if (bulkResponse.hasFailures()) {
//			System.out.println("有失败的");
//		}
//	}
//
//	/**
//	 * 单个添加
//	 */
//	@Test
//	public void indexTest() {
//
//		JSONObject data = new JSONObject();
//		data.put("name", "name2");
//		data.put("age", 18);
//		data.put("pid", "123456");
//
//		// add 参数一次是:索引 类型 ID data
//		IndexResponse response1 = c
//				.prepareIndex("index_test_1", // 索引
//						"test1", // 类型:相当于数据表，添加doc时可以自动添加，类型:文档结构类似的doc集合。逻辑概念上的
//						"3")// id最好用自己的，别用es默认的
//				.setSource(data.toJSONString()).get();
//
//		System.out.println(response1);
//	}
//
//	@Test // 按照ID查更新或删除
//	public void testGetById() {
//		GetResponse r = c.prepareGet("index_test_1", "test1", "2").get();
//		System.out.println(r);
//
//		// 更新
//		JSONObject data = new JSONObject();
//		data.put("name", "我是修改后的");
//		data.put("age", 18);
//
//		c.prepareUpdate("index_test_1", "test1", "2").setDoc(data.toJSONString()).get();
//
//		c.prepareGet("index_test_1", "test1", "2").get();
//		System.out.println(r);
//
//		// 删除按照id删除
//		c.prepareDelete("index_test_1", "test1", "2");
//
//		// 查询删除
//	}
//
//	@Test // 基于查询结果删除
//	public void testDelByQuery() {
//		BulkIndexByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(c)
//				.filter(QueryBuilders.matchQuery("gender", "male")).source("persons").get();
//
//		long deleted = response.getDeleted();
//		System.out.println("deleted=" + deleted);
//		
//		//删除可能是长时间的，你可以异步删除
//		DeleteByQueryAction.INSTANCE.newRequestBuilder(c)
//	    .filter(QueryBuilders.matchQuery("gender", "male"))                  
//	    .source("persons")                                                   
//	    .execute(new ActionListener<BulkIndexByScrollResponse>() {           
//	        @Override
//	        public void onResponse(BulkIndexByScrollResponse response) {
//	            long deleted = response.getDeleted();      
//	            System.out.println("deleted=" + deleted);
//	        }
//	        @Override
//	        public void onFailure(Exception e) {
//	            // Handle the exception
//	        }
//	    });
//	}
//}
