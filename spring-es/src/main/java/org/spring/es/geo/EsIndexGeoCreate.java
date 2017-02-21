package org.spring.es.geo;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.spring.es.EsBase;

/**
 * es创建geo索引
 * 
 * @author gaotingping
 *
 *         2017年2月4日 下午6:03:57
 */
public class EsIndexGeoCreate extends EsBase {

	//http://127.0.0.1:9200/index_geo_test/geo1/_mapping
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
	
	//更新mapping
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

	@Test // 添加点测试数据
	public void testAddData() {

		BulkRequestBuilder bulkRequestBuilder = c.prepareBulk();

		for (int i = 0; i < 5; i++) {

			// 111.876171,3.398951 122.686718,53.550502
			GeoVO geoVO = new GeoVO(i, 122.686718, 53.550502, "中国好啊");
			//id相同就类似与更新了

			bulkRequestBuilder.add(
						c.prepareIndex("index_geo_test", "geo1")
						.setId("id_" + i)
						.setSource(toGeoJson(geoVO))
					);
		}

		BulkResponse bulkResponse = bulkRequestBuilder.get();

		bulkRequestBuilder.request().requests().clear();

		if (bulkResponse.hasFailures()) {
			System.out.println("有失败的");
		}
		//System.out.println(JSON.toJSON(bulkResponse));
	}

	private String toGeoJson(GeoVO geoVO) {
		String jsonData = null;
		try {
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject()
					.field("id", geoVO.getId())
					//
					//Format in [lon, lat], note, the order of lon/lat here in order to conform with GeoJSON.
					//数组的时候，注意这里是经纬度 lng->lat,和搜索的point正好相反
					.startArray("location").value(geoVO.getLng()).value(geoVO.getLat()).endArray()
					.field("name", geoVO.getName())
					.endObject();
			jsonData = jsonBuild.string();
			System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}
}
