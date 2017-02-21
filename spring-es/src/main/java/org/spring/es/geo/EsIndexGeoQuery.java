package org.spring.es.geo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.spring.es.EsBase;

import com.alibaba.fastjson.JSON;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * es 基于地理位置的查询GEO
 * 
 * Lng,Lat (经度-维度:111.876171,3.398951  122.686718,53.550502)
 * 
 * @author gaotingping
 *
 *         2017年2月4日 下午2:19:47
 */
public class EsIndexGeoQuery extends EsBase {
	/*
		https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-geo-queries.html
		https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.0/java-geo-queries.html
	*/
	@Test
	public void testQuery25() throws IOException{
		/*
		 * Geo queries  #地理位置查询
			Elasticsearch supports two types of geo data: geo_point fields which support lat/lon pairs, 
			and geo_shape fields, which support points, lines, circles, polygons, multi-polygons etc.
			
			geo_shape query #形状:相交，包含，不相交
			    Find document with geo-shapes which either intersect, are contained by, or do 
			    not intersect with the specified geo-shape. 
			
			geo_bounding_box query  #是否在指定的矩形
			    Finds documents with geo-points that fall into the specified rectangle. 
			
			geo_distance query #是否在指定点中心位置内
			    Finds document with geo-points within the specified distance of a central point. 
			
			geo_polygon query #在指定的多边形
			    Find documents with geo-points within the specified polygon. 
		 */
		List<Coordinate> points = new ArrayList<>();
		points.add(new Coordinate(0, 0));//原点顺时针回到原点
		points.add(new Coordinate(0, 10));
		points.add(new Coordinate(10, 10));
		points.add(new Coordinate(10, 0));
		points.add(new Coordinate(0, 0));

		QueryBuilders.geoShapeQuery(
		        "pin.location",                         
		        ShapeBuilders.newMultiPoint(points))
				.relation(ShapeRelation.WITHIN);
		/*
		 *      INTERSECTS("intersects"),//相交
			    DISJOINT("disjoint"),//互斥
			    WITHIN("within"),//之内
			    CONTAINS("contains");//包含 FIXME 这个该怎么理解呢
		 */
		
		// Using pre-indexed shapes FIXME 什么东东
		QueryBuilders.geoShapeQuery(
		        "pin.location",                  
		        "DEU",                           
		        "countries")                     
		        .relation(ShapeRelation.WITHIN)
		        .indexedShapeIndex("shapes")     
		        .indexedShapePath("location"); 
		
		//矩形内的点
		QueryBuilders.geoBoundingBoxQuery("pin.location") 
	    .setCorners(40.73, -74.1,   //左上角                      
	                40.717, -73.99);//右下角
		
		//圆形内的点:附近搜索
		QueryBuilders.geoDistanceQuery("pin.location")  
	    .point(40, -70)                                 
	    .distance(200, DistanceUnit.KILOMETERS);
		
		//指定的多边形
		List<GeoPoint> points2 = new ArrayList<>();             
		points2.add(new GeoPoint(40, -70));
		points2.add(new GeoPoint(30, -80));
		points2.add(new GeoPoint(20, -90));

		QueryBuilders.geoPolygonQuery("pin.location", points2); 
	}
	
	@Test //geoDistanceQuery 只查询
	public void test1(){
		
		GeoDistanceQueryBuilder qb = QueryBuilders.geoDistanceQuery("location")  
	    .point(53.550502,122.686718)//注意 lat,lng的顺序，和一般地图地图的经纬度相反                        
	    .distance(200, DistanceUnit.KILOMETERS);
		
		 SearchResponse response = c.prepareSearch("index_geo_test")
		 .setTypes("geo1")
		 .setQuery(qb)
		 .get();
		 
		 System.out.println(JSON.toJSON(response));
	}
}
