package org.spring.mongodb.lbs;

import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
public class TestExecutor extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private MongoTemplate m;
	
	private double lat=40;
	private double lng=116;

	@Test
	public void doTest(){
		long s = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			//areaSearch();//34s 33s 33s
			nearSearch();//15.8s 15.3s 15.2s
		}
		System.out.println(System.currentTimeMillis()-s);
	}

	//附近搜索
	public void nearSearch() {
		Point location = new Point(40.109942, 116.328152);
		NearQuery query = NearQuery.near(location)
				.maxDistance(new Distance(0.5, Metrics.KILOMETERS))
				.spherical(true);
		m.geoNear(query, Location.class,"location");
	}

	//区域内搜索
	public void areaSearch() {
		Box box = new Box(new Point(40,116), new Point(40.1,116.1));
		m.find(new Query(Criteria.where("location").within(box)), Location.class);
	}
	
	@Test
	public void test3(){
		//插入
		Random r=new Random();
		long s = System.currentTimeMillis();
		for(int i=0;i<500;i++){
			double t = (double)r.nextInt(10000)/10000;
			m.save(new Location("中俊大厦", new Loc(new double[]{lng+t,lat+t})));
		}
		System.out.println(System.currentTimeMillis()-s);
	}
}
