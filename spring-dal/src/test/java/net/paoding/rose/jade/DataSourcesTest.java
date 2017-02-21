package net.paoding.rose.jade;

import javax.sql.DataSource;

import model.MyModel;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.context.application.JadeFactory;

import org.junit.Before;
import org.junit.Test;

public class DataSourcesTest {

    @DAO
    interface UserDAO {

        @SQL("INSERT INTO t1(_createTime,_name,_content, _score,_status) VALUES(:1.time, :1.name,:1.content,:1.score,:1.status)")
        void save(MyModel m);
    }

    private UserDAO dao;

    @Before
    public void init() {
        DataSource dataSource = DataSources.createUniqueDataSource();
        JadeFactory factory = new JadeFactory(dataSource);
        dao = factory.create(UserDAO.class);
    }

    @Test
    public void test() {
    	
    	long sc = System.currentTimeMillis();
		
		for(int i=0;i<5;i++){
		
			MyModel jb=new MyModel();
			jb.setName("name"+i);
			jb.setContent("content"+i);
			jb.setScore(i);
			jb.setTime("2015-09-18");
			jb.setStatus(1);
			dao.save(jb);
		
		}
		
		System.out.println("耗时="+(System.currentTimeMillis()-sc));
		//357,015
		//357,880
		//352,926
		// 14次/秒
    }
}
