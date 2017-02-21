package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.mysql.jdbc.Statement;

/**
 * 单元测试
 */
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class JDBCTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	protected JdbcTemplate jdbcTemplate = null;

	@Before
	public void init() {
		System.out.println("init");
	}

	@After
	public void close() {
		System.out.println("close");
	}
	
	@Test
	public void test11(){
		List<Map<String, Object>> s = jdbcTemplate.queryForList("select * from t1");
		System.out.println(s.size());
	}
	
	@Test//获得主键
	public void test0() {
		final String sql="insert into t1(_name,_content, _score) values(?,?,?)";
		final Object [] args={"n123","c123","123"};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		System.out.println(jdbcTemplate.update(new PreparedStatementCreator(){
			@Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                setPatams(ps);
                return ps;
            }
            private void setPatams(PreparedStatement ps) throws SQLException {
            	for(int i=0;i<args.length;i++){
            		  ps.setObject(i+1, args[i]);
            	}
            }
		}, keyHolder));
		System.out.println(keyHolder.getKey().intValue());
	}

	@Test
	public void test1() {
		System.out.println(jdbcTemplate);
	}
	
	@Test
	public void test3(){
		try {
	        //System.out.println(bookDao.getById("SELECT * FROM t1 WHERE _id<?",4));
        } catch (Exception e) {
	        e.printStackTrace();
        }
	}

	@Test
	// 存储过程
	public void test4() {
		// this.jdbcTemplate.execute("call testpro('p1','p2')");

		/*
		 * String param2Value = (String) jdbcTemplate.execute( new
		 * CallableStatementCreator() { public CallableStatement
		 * createCallableStatement(Connection con) throws SQLException { String
		 * storedProc = "{call testpro(?,?)}";// 调用的sql CallableStatement cs =
		 * con.prepareCall(storedProc); cs.setString(1, "p1");// 设置输入参数的值
		 * cs.registerOutParameter(2, OracleTypes.VARCHAR);// 注册输出参数的类型 return
		 * cs; } }, new CallableStatementCallback() { public Object
		 * doInCallableStatement(CallableStatement cs) throws SQLException,
		 * DataAccessException { cs.execute(); return cs.getString(2);//
		 * 获取输出参数的值 } });
		 */

		/*
		 * List<Object[]> rtnObjs = new ArrayList<Object[]>(); rtnObjs =
		 * this.getJdbcTemplate().execute("{call getuser()}", new
		 * CallableStatementCallback<List<Object[]>>() {
		 * @Override public List<Object[]> doInCallableStatement(
		 * CallableStatement cs) throws SQLException, DataAccessException {
		 * List<Object[]> objects = new ArrayList<Object[]>(); ResultSet rs =
		 * cs.executeQuery(); while (rs.next()) { Object[] objArr = new
		 * Object[4]; objArr[0] = rs.getLong("ID"); objArr[1] =
		 * rs.getTimestamp("createtime"); objArr[2] = rs.getString("password");
		 * objArr[3] = rs.getString("username"); objects.add(objArr); } return
		 * objects; } }); return rtnObjs;
		 */

		/*
		 * final int uid = id; Object[] objArr = new Object[4]; objArr =
		 * this.getJdbcTemplate().execute("{call finduser(?)}", new
		 * CallableStatementCallback<Object[]>() {
		 * @Override public Object[] doInCallableStatement(CallableStatement cs)
		 * throws SQLException, DataAccessException { Object[] arr = new
		 * Object[4]; cs.setInt("uid", uid); //为存储过程中参数赋值 ResultSet rs =
		 * (ResultSet) cs.executeQuery(); while (rs.next()) { arr[0] =
		 * rs.getLong("ID"); arr[1] = rs.getTimestamp("createtime"); arr[2] =
		 * rs.getString("password"); arr[3] = rs.getString("username"); } return
		 * arr; } }); return objArr;
		 */
	}
	
	@Test
	public void update(){
		int i = jdbcTemplate.update("",1);
		System.out.println(i);
	}

	// 灵活 少冗余
//	@Test
//	public void query() {
//		
//		// 这中想不到在什么场合使用
//		final List<Person> list = new ArrayList<Person>();
//		//
//		RowCallbackHandler rch = new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				Person p = new Person();
//				p.setId(rs.getString("id"));
//				p.setName(rs.getString("name"));
//				p.setAge(rs.getInt("age"));
//				list.add(p);
//			}
//		};
//		jdbcTemplate.query("select * from t1", rch);
//		System.out.println(list);
//
//		// rowmapper
//		RowMapper<Person> rowMapper = new RowMapper<Person>() {
//
//			@Override
//			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Person p = new Person();
//				p.setId(rs.getString("id"));
//				p.setName(rs.getString("name"));
//				p.setAge(rs.getInt("age"));
//				return p;
//			}
//		};
//		List<Person> l = jdbcTemplate.query("select * from t1", rowMapper);
//		System.out.println(l);
//	}

	@Test
	public void queryForMap() {

		/*
		 * 可能的需求 1)一行: 列名称=值 2)多行: List<1)> 3)多行： map以第一列的值key,以第二列的值为value
		 * 4)多行: map以第一列的值key,整个行为value
		 */

//		// 1)
//		Map<String, Object> map = jdbcTemplate.queryForMap("select id,name from t1 where id<?", 8);
//		System.out.println(map);

//		// 2)
//		List<Map<String, Object>> m = jdbcTemplate.queryForList("select id,name from t1 where id<?", 100);
//		System.out.println(m);

		// 3) ResultSetExtractor 方便我们做一些自己的特殊封装
		ResultSetExtractor<Map<String, String>> rse = new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
//				//元数据分析
//				ResultSetMetaData rsmd = rs.getMetaData();
//				int columnCount = rsmd.getColumnCount();
//				
//				System.out.println("本次查询列数="+columnCount);
//				
//				for(int i=1;i<=columnCount;i++){
//					System.out.println("第"+i+"个列名是"+rsmd.getColumnName(i));
//				}
//				
//				if (columnCount > 2) {
//					throw new RuntimeException("The query must be 2 columns");
//				}
				
				Map<String, String> map = null;
				if (rs != null) {
					map = new HashMap<String, String>();
					while (rs.next()) {
						map.put(rs.getString(1), rs.getString(2));// 下标从1开始
					}
				}
				return map;
			}
		};
		Map<String, String> mp = jdbcTemplate.query("select id,name from t1 where id < ?", new String[] { "100" }, rse);
		System.out.println(mp);

//		// 4)
//		ResultSetExtractor<Map<String, Person>> rse2 = new ResultSetExtractor<Map<String, Person>>() {
//
//			@Override
//			public Map<String, Person> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				Map<String, Person> map = null;
//				if (rs != null) {
//					map = new HashMap<String, Person>();
//					while (rs.next()) {
//						Person p = new Person();
//						p.setId(rs.getString("id"));
//						p.setName(rs.getString("name"));
//						p.setAge(rs.getInt("age"));
//						map.put(rs.getString(1), p);// 下标从1开始
//					}
//				}
//				return map;
//			}
//		};
//		Map<String, Person> mp2 = jdbcTemplate.query("select id,name,age from t1 where id < ?", new String[] { "100" }, rse2);
//		System.out.println(mp2);
	}
}
