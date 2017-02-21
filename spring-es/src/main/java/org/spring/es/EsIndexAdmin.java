package org.spring.es;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * es 索引管理
 * 
 * @author gaotingping
 *
 * 2017年2月4日 下午2:18:37
 */
public class EsIndexAdmin extends EsBase{

	@Test //看索引的setting
	public void getMapping() {

		IndicesAdminClient iac = c.admin().indices();

		GetMappingsResponse r = iac.prepareGetMappings("index_geo_test").get();
		System.out.println(JSON.toJSON(r.getMappings()));
	}

	/**
	 * es的索引，类似于关系数据库的数据库对象
	 */
	@Test
	public void adminIndexs() {

		IndicesAdminClient iac = c.admin().indices();

		// 创建默认索引：已经存在的创建会抛异常
		iac.prepareCreate("index_test_1")
				.setSettings(Settings.builder()
						.put("index.number_of_shards", 3)//分片数，默认是5
						.put("index.number_of_replicas", 1))
				.get();

		// 刷新索引
		iac.prepareRefresh().get();
		iac.prepareRefresh("index_test_1").get();//刷新单个
		
		//删除索引
		//iac.prepareDelete(indices);
	}
}
