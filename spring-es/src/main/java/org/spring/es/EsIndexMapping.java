package org.spring.es;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

/**
 * es mapping管理:elasticsearch 通过定义的映射mapping来决定文档及其字段改如何被存储和索引
 * 
 * https://www.elastic.co/guide/en/elasticsearch/reference/5.0/mapping.html
 * @author gaotingping
 *
 * 2017年2月6日 下午2:28:52
 */
public class EsIndexMapping extends EsBase {

	@Test // 创建索引
	public void testIndexMapping() {

		IndicesAdminClient iac = c.admin().indices();

		XContentBuilder mapping = null;

		try {
			// 支持geo_point，geo_shape
			mapping = XContentFactory.jsonBuilder().startObject().startObject("properties").startObject("id")
					.field("type", "long").endObject().startObject("location").field("type", "geo_point")
					.field("index", "not_analyzed").endObject().startObject("name").field("type", "string")
					.field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject().endObject()
					.endObject();
			System.out.println(mapping.string());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 创建默认索引：已经存在的创建会抛异常
		iac.prepareCreate("index_geo_test").setSettings(Settings.builder().put("index.number_of_shards", 3)// 分片数，默认是5
				.put("index.number_of_replicas", 1)).addMapping("geo1", mapping).get();
	}
	
	/**
	 * 更新mapping
	 * 
		{
		    "properties": {
		        "id": {
		            "type": "long"
		        },
		        "location": {
		            "type": "geo_point",
		            "index": "not_analyzed"
		        },
		        "name": {
		            "type": "string",
		            "analyzer": "ik_smart",
		            "search_analyzer": "ik_smart"
		        }
		    }
		}
	 */
	@Test
	public void test1(){
		try {
			// 支持geo_point，geo_shape
			XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("properties").startObject("id")
					.field("type", "long").endObject().startObject("location").field("type", "geo_point")
					.field("index", "not_analyzed").endObject().startObject("name").field("type", "string")
					.field("analyzer", "ik_smart").field("search_analyzer", "ik_smart").endObject().endObject()
					.endObject();
			System.out.println(mapping.string());
		
		
			PutMappingRequest str = Requests.putMappingRequest("index_geo_test").type("geo1").source(mapping);
			System.out.println(str.source());
			c.admin().indices().putMapping(str).actionGet();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
