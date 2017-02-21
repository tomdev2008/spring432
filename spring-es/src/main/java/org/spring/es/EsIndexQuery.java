package org.spring.es;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * es索引查询
 * 
 * @author gaotingping
 *
 *         2017年2月4日 下午2:19:47
 */
public class EsIndexQuery extends EsBase {

	/**
	 * 按照ID查询
	 */
	@Test // 按照ID查更新或删除
	public void testGetById() {
		GetResponse r = c.prepareGet("index_test_1", "test1", "2").get();
		System.out.println(r);
	}

	@Test // 搜索
	public void testSearch() {

		/*
		 * SearchResponse response = client.prepareSearch("index1", "index2")
		 * .setTypes("type1", "type2")
		 * .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		 * .setQuery(QueryBuilders.termQuery("multi", "test")) // Query
		 * .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))
		 * //Filter 这2个有啥区别呢？？？ .setFrom(0).setSize(60) //分页 .setExplain(true)
		 * .get();
		 */
		SearchResponse response = c.prepareSearch("index_test_1").setTypes("test1").get();

		/*
		 * 另外一种是使用Scroll/size，如果结果超过size大小，会返回size条记录，
		 * 并返回一个scrollId，可以用这个scrollId继续往下查询 .setScroll(new TimeValue(60000))
		 * 
		 * SearchResponse actionGet = elasticClient.prepareSearchScroll(
		 * actionGet.getScrollId()) .setScroll(new TimeValue(600000))
		 * .execute().actionGet();
		 */

		System.out.println(JSON.toJSON(response));
	}

	@Test
	public void testSearch2() {
		SearchResponse response = c.prepareSearch("index_test_1").setTypes("test1")
				// .addSort(field, order);//排序
				// .setFrom(0).setSize(60).setExplain(true)//分页
				.get();

		System.out.println(response);
	}

	@Test
	public void testSearch3() {

		// 单个匹配
		QueryBuilders.matchQuery("name", "葫芦4032娃");

		// 多个
		QueryBuilders.multiMatchQuery("内容", "name1", "name2");

		// 多条件组合
		QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name", "葫芦3033娃")) // trem是单独项
				.must(QueryBuilders.termQuery("home", "山西省太原市7967街道")) // 必须
				.mustNot(QueryBuilders.termQuery("isRealMen", false))// 必须不
				.should(QueryBuilders.termQuery("now_home", "山西省太原市"));// or

		// ids in
		QueryBuilders.idsQuery().addIds("1", "2", "3");

		// 模糊匹配 不建议用，任何可能是整个集群有问题的服务
		// QueryBuilders.fuzzyQuery("name", "葫芦3582");
		QueryBuilders.wildcardQuery("name", "query");// 通配符
	}

	@Test // 一次调用多个查询
	public void testMultiSearch() {
		SearchRequestBuilder srb1 = c.prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch"))
				.setSize(1);
		SearchRequestBuilder srb2 = c.prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

		MultiSearchResponse sr = c.prepareMultiSearch().add(srb1).add(srb2).get();

		// You will get all individual responses from
		// MultiSearchResponse#getResponses()
		long nbHits = 0;
		for (MultiSearchResponse.Item item : sr.getResponses()) {
			SearchResponse response = item.getResponse();
			nbHits += response.getHits().getTotalHits();
		}
		System.out.println(nbHits);
	}

	@Test // 聚合查询
	public void testAggregation() {
		SearchResponse sr = c.prepareSearch().setQuery(QueryBuilders.matchAllQuery())
				.addAggregation(AggregationBuilders.terms("age").field("field"))
				.addAggregation(AggregationBuilders.dateHistogram("agg2").field("birth").dateHistogramInterval(DateHistogramInterval.YEAR))
				.get();

		// Get your facet results
		System.out.println(sr);
		//Terms agg1 = sr.getAggregations().get("agg1");
		//Aggregation agg2 = sr.getAggregations().get("agg2");
	}
	
	@Test //达到指定数量是，提前终止，防止数据爆棚
	//The maximum number of documents to collect for each shard
	public void test6(){
		SearchResponse sr = c.prepareSearch(INDEX_NAME)
			    .setTerminateAfter(3)    
			    .get();

			if (sr.isTerminatedEarly()) {
			    // We finished early
				System.out.println("提前终止");
			}
	}
	
	
	@Test
	public void testQuery21(){
		
		
		//查询所有
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		
		/*
		 *  全文查询:分词查询,一般适用与文本字段,类似与like %xxx%
			match query
			    The standard query for performing full text queries, including fuzzy matching and phrase or proximity queries. 
			multi_match query
			    The multi-field version of the match query. 
			common_terms query
			    A more specialized query which gives more preference to uncommon words. 
			query_string query
			    Supports the compact Lucene query string syntax, allowing you to specify AND|OR|NOT conditions and multi-field search within a single query string. For expert users only. 
			simple_query_string
			    A simpler, more robust version of the query_string syntax suitable for exposing directly to users. 
		 */
		
		qb = QueryBuilders.matchQuery(
			    "name",  //字段                
			    "kimchy elasticsearch");//值
		
		qb=QueryBuilders.multiMatchQuery(
			    "kimchy elasticsearch", //text
			    "user", "message"//fields       
			);
		
		qb=QueryBuilders.commonTermsQuery("name",//字段
                "kimchy"); //值
		
		qb=QueryBuilders.queryStringQuery("+kimchy -elasticsearch");//Lucene语法查询，专家级用法
		
		qb=QueryBuilders.simpleQueryStringQuery("+kimchy -elasticsearch");//简化版
		
	}
	
	@Test
	public void testQuery22(){
		
		/*
		 * //term-level queries 适用与结构化的查询，精确匹配 > =
			term query
			    Find documents which contain the exact term specified in the field specified. #指定字段包含指定的值
			terms query
			    Find documents which contain any of the exact terms specified in the field specified. #复数
			range query
			    Find documents where the field specified contains values (dates, numbers, or strings) in the range specified. #返回查询
			exists query
			    Find documents where the field specified contains any non-null value. #存在(非空)
			prefix query
			    Find documents where the field specified contains terms which being with the exact prefix specified. #前缀
			wildcard query
			    Find documents where the field specified contains terms which match the pattern specified, where the pattern 
			    supports single character wildcards (?) and multi-character wildcards (*) #通配符
			regexp query
			    Find documents where the field specified contains terms which match the regular expression specified. #正则表达式查询
			fuzzy query
			    Find documents where the field specified contains terms which are fuzzily similar to the specified term. 
			    Fuzziness is measured as a Levenshtein edit distance of 1 or 2.  #模糊
			type query
			    Find documents of the specified type. #类型
			ids query
			    Find documents with the specified type and IDs.  #id in
		 */
		//查询所有
		QueryBuilder qb = QueryBuilders.termQuery(
			    "name",   //字段 
			    "kimchy" //值
			);
		
		qb = QueryBuilders.termsQuery("tags", //字段 
			    "blue", "pill"); //值
		
		qb = QueryBuilders.rangeQuery("price")   
			    .from(5)                            
			    .to(10)                             
			    .includeLower(true)    //是否包含边界             
			    .includeUpper(false);  
		
		qb = QueryBuilders.rangeQuery("age") //简单写法 
	    .gte("10")                        
	    .lt("20");
		
		
		qb = QueryBuilders.existsQuery("name");//存在这个字段，非null
		
		
		qb = QueryBuilders.prefixQuery(
			    "brand",    //字段
			    "heine"    //值 
			);
		
		qb = QueryBuilders.wildcardQuery("user", "k?mc*");//通配符

		
		qb = QueryBuilders.regexpQuery(//正则表达式
		    "name.first",        
		    "s.*y");             

			
		//过期，不建议使用
//		qb = QueryBuilders.fuzzyQuery(
//			    "name",     
//			    "kimzhy"    
//			);
		
		QueryBuilders.typeQuery("my_type");//类型查询是什么玩意？应该是查询类型下的所有数据吧
		
		QueryBuilders.idsQuery("my_type", "type2")//类型可选
	    .addIds("1", "4", "100");//id
	}
	
	@Test//https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-compound-queries.html
	public void testQuery23(){
		/*
		 //Compound queries 符合查询:可以组合对个查询条件的
		constant_score query
		    A query which wraps another query, but executes it in filter context. 
		    All matching documents are given the same “constant” _score. 
		bool query 
		    The default query for combining multiple leaf or compound query clauses, as must, should, must_not, 
		    or filter clauses. The must and should clauses have their scores combined — the more matching clauses, 
		    the better — while the must_not and filter clauses are executed in filter context. 
		dis_max query
		    A query which accepts multiple queries, and returns any documents which match any of the query clauses. 
		    While the bool query combines the scores from all matching queries, the dis_max query uses the score 
		    of the single best- matching query clause. 
		function_score query
		    Modify the scores returned by the main query with functions to take into account factors like popularity, 
		    recency, distance, or custom algorithms implemented with scripting. 
		boosting query
		    Return documents which match a positive query, but reduce the score of documents 
		    which also match a negative query. 
		indices query
		    Execute one query for the specified indices, and another for other indices. 
		 */
		
		//查询指定打分的结果？ filter过滤？
		QueryBuilders.constantScoreQuery(
				QueryBuilders.termQuery("name","kimchy")      
		    )
		    .boost(2.0f); 
		
		//多条件  and  or  not_and组合
		QueryBuilders.boolQuery()
			    .must(QueryBuilders.termQuery("content", "test1"))   //必须->and
			    .must(QueryBuilders.termQuery("content", "test4"))    
			    .mustNot(QueryBuilders.termQuery("content", "test2")) //必须取反
			    .should(QueryBuilders.termQuery("content", "test3"))  //或:or
			    .filter(QueryBuilders.termQuery("content", "test5"));//过滤,不打分的
	
		//有点不大清楚，不懂啊
		QueryBuilders.disMaxQuery()
	    .add(QueryBuilders.termQuery("name", "kimchy"))        
	    .add(QueryBuilders.termQuery("name", "elasticsearch")) 
	    .boost(1.2f)                             
	    .tieBreaker(0.7f);  
		
		// 晕
		FilterFunctionBuilder[] functions = {
		        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
		        		QueryBuilders.matchQuery("name", "kimchy"),                 
		        		ScoreFunctionBuilders.randomFunction("ABCDEF")),                    
		        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
		        		ScoreFunctionBuilders.exponentialDecayFunction("age", 0L, 1L))      
		};
		QueryBuilders.functionScoreQuery(functions);
		
		
		//和disMaxQuery区别是啥？
		QueryBuilders.boostingQuery(
				QueryBuilders.termQuery("name","kimchy"),    
				QueryBuilders.termQuery("name","dadoonet"))  
		    .negativeBoost(0.2f);
		
//		//在5.0.0废弃
//		QueryBuilders.indicesQuery(
//		        termQuery("tag", "wow"),             
//		        "index1", "index2"                   
//		    ).noMatchQuery(termQuery("tag", "kow"));
	}
	
	
	@Test
	public void testQuery24(){
		/*
		 * joining 查询:sql的join在分布式系统中，是非常重的操作，日常操作，最好避免join，尤其是数据量大的情况下，能冗余的多冗余下
		 * 
			nested query  #嵌套查询(子查询)
			    Documents may contains fields of type nested. These fields are used to index arrays of objects, 
			    where each object can be queried (with the nested query) as an independent document. 
			has_child and has_parent queries
			    A parent-child relationship can exist between two document types within a single index. 
			    The has_child query returns parent documents whose child documents match the specified query, 
			    while the has_parent query returns child documents whose parent document matches the specified query. 
		 */
		QueryBuilders.nestedQuery(
		        "obj1",                       
		        QueryBuilders.boolQuery()                   
		                .must(QueryBuilders.matchQuery("obj1.name", "blue"))
		                .must(QueryBuilders.rangeQuery("obj1.count").gt(5)),
		        ScoreMode.Avg);
		
		QueryBuilders.hasChildQuery(
			    "blog_tag",                     
			    QueryBuilders.termQuery("tag","something"),   
			    ScoreMode.Avg                   
			);
		
		QueryBuilders.hasParentQuery(
			    "blog",                         
			    QueryBuilders.termQuery("tag","something"),   
			    false                           
			);
	}
	
	@Test
	public void testQuery26(){
		/**
		  	专业(专门)查询，不能和其它组混用？
			more_like_this query
			    This query finds documents which are similar to the specified text, document, or collection of documents. 
			script query
			    This query allows a script to act as a filter. Also see the function_score query. 
			percolate query
			    This query finds percolator queries based on documents. 
		 */
	}
	
	@Test
	public void testQuery27(){
		/**
		 * Span queries:跨度查询（SpanTermQuery），一般用于专利或法律条文
		 */
		
	}
}
