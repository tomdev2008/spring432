package org.spring.mongodb.lbs;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-mongodb.xml" })
public class TestGeo3 extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private MongoTemplate m;

	@Test //初始化
	public void init() {
		
		/**
		 * 经测试:要求为geoJson格式
		 * location is stored in GeoJSON format.
		 * {
		 *   "type" : "Point",
		 *   "coordinates" : [ x, y ]
		 * }
		 */
		m.save(new Location("中俊大厦", new Loc(new double[]{116.328152,40.109942})));
		m.save(new Location("小7路", new Loc(new double[]{116.327721,40.109155})));
		m.save(new Location("皖江公寓", new Loc(new double[]{116.329688,40.108455})));
		m.save(new Location("五合公寓", new Loc(new double[]{116.328952,40.108214})));
		m.save(new Location("金飞红木", new Loc(new double[]{116.33038,40.10749})));
		m.save(new Location("未知路", new Loc(new double[]{116.331162,40.109035})));
		m.save(new Location("朱辛庄地铁站", new Loc(new double[]{116.319771,40.10999})));
		
		//创建索引
		m.indexOps(Location.class).ensureIndex(new GeospatialIndex("loc.coordinates").typed(GeoSpatialIndexType.GEO_2DSPHERE));
	}

	//圆形球面Circle:适合查找出来进行标记的应用
	@Test
	public void test1() {

		//查找方圆半径内的目标:Circle
		Circle circle = new Circle(116.329688,40.108455, 0.01);
		List<Location> list = m.find(new Query(Criteria.where("location").within(circle)).limit(5), Location.class);
		for(Location l:list){
			System.out.println(l);
		}
	}
	
	@Test
	public void test3() {
		//球面坐标
		Circle circle = new Circle(-73.99171, 40.738868, 0.003712240453784);
		List<Location> list = m.find(new Query(Criteria.where("location").withinSphere(circle)), Location.class);
		for (Location l : list) {
			System.out.println(l);
		}
	}
	
	// 四方盒子:Box
	@Test
	public void test4() {
		//lower-left then upper-right
		Box box = new Box(new Point(-73.99756, 40.73083), new Point(-73.988135, 40.741404));
		List<Location> list = m.find(new Query(Criteria.where("location").within(box)), Location.class);
		for (Location l : list) {
			System.out.println(l);
		}
	}
	
	//点附近
	@Test
	public void test5(){

		//Point point = new Point(-73.99171, 40.738868);
		//List<Venue> venues =
		//    template.find(new Query(Criteria.where("location").near(point).maxDistance(0.01)), Venue.class);
		//
		//Point point = new Point(-73.99171, 40.738868);
		//List<Venue> venues =
		//    template.find(new Query(Criteria.where("location").near(point).minDistance(0.01).maxDistance(100)), Venue.class);
		//
		//To find venues near a Point using spherical coordinates the following query can be used
		//
		//Point point = new Point(-73.99171, 40.738868);
		//List<Venue> venues =
		//    template.find(new Query(
		//        Criteria.where("location").nearSphere(point).maxDistance(0.003712240453784)),
		//        Venue.class);
	}
	
	//附近搜索和距离计算，按照由远到近排序:是否附近应用
	@Test
	public void test6() {
		Point location = new Point(116.329688,40.108455);
		NearQuery query = NearQuery.near(location)
				.maxDistance(new Distance(Integer.MAX_VALUE, Metrics.KILOMETERS))
				.spherical(true)
				.num(10);

		GeoResults<Location> result= m.geoNear(query, Location.class,"location");
		
		System.out.println("====================================");
		//System.out.println(result.getAverageDistance());
		List<GeoResult<Location>> list = result.getContent();
		for(GeoResult<Location> t:list){
			System.out.println(t.getDistance()+"\t"+t.getContent());
		}
		System.out.println("====================================");
	}
}
