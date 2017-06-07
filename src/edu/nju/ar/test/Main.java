package edu.nju.ar.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.nju.ar.service.impl.DataInjection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/mybatis-config.xml","classpath:/config/spring-common.xml","classpath:/config/spring-mvc.xml"})
public class Main {

	@Autowired
	private DataInjection di;

	@Test
	public void test() {
//		di.insertMovieForYupiaoer();
//		di.insertCinemaForYupiaoer();
		
		di.insertSessionForNuoMi();
//		di.insertSessionForYupiaoer();
//		di.insertMovieForNuoMi();
//		di.insertCinemaForNuoMi();
		
		System.out.println(".......");
	}
}
