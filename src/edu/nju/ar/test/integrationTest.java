package edu.nju.ar.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import edu.nju.ar.service.impl.DataIntegrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/mybatis-config.xml","classpath:/config/spring-common.xml","classpath:/config/spring-mvc.xml"})
public class integrationTest{

	@Autowired
	private DataIntegrator di;

	@Test
	public void test() {
		System.out.println(".......");

		di.generateMovieData();
		di.generateCinemaAndSessionData();

		
		System.out.println(".......");
	}
}
