//package org.spring.mongodb.lbs;
//
//import java.util.List;
//import java.util.Random;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.geo.Circle;
//import org.springframework.data.geo.GeoResults;
//import org.springframework.data.geo.Point;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
//import org.springframework.data.mongodb.core.index.GeospatialIndex;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.NearQuery;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
//public class TestGeo extends AbstractJUnit4SpringContextTests {
//	
//	@Autowired
//	private MongoTemplate m;
//
//	@Test //初始化
//	public void init() {
//		
//		double [] node={116.319771,40.10999};
//		
//		Random r=new Random();
//		
//		for(int i=0;i<500;i++){
//			int t = r.nextInt(300);
//			// 初始化数据
//			m.save(new Location("A"+i, node[0]+(double)t/100000, node[1]+(double)t/100000));
//		}
//		
//		//创建索引
//		m.indexOps(Location.class).ensureIndex(new GeospatialIndex("position").typed(GeoSpatialIndexType.GEO_2DSPHERE));
//		
//	}
//
//	@Test
//	public void test1() {
//
//		Circle circle = new Circle(116.319771,40.10999, 0.01);
//		List<Location> list = m.find(new Query(Criteria.where("location").within(circle)).limit(5), Location.class);
//		for(Location l:list){
//			System.out.println(l);
//		}
//	}
//
//	@Test
//	public void test2() {
//		Point location = new Point(116.319771,40.10999);//当前位置
//		
//		NearQuery query = NearQuery.near(location)
//				//.maxDistance(new Distance(Integer.MAX_VALUE, Metrics.MILES))
//				//.spherical(true)
//				;
//
//		GeoResults<Location> list= m.geoNear(query, Location.class);
//		System.out.println(list.getContent());
//	}
//}
