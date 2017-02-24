package com.zhaiyz.activemq;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-jms.xml")
public class ConverterTest {
	
	@Resource
	Producer producer;

	@Test
	public void testSendMessage() throws Throwable {
		TestRunnable[] trs = new TestRunnable[10];
		for (int i = 0; i < trs.length; i++) {
			trs[i] = new SendMessageThread();
		}
		
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		mttr.runTestRunnables();
	}
	
	private class SendMessageThread extends TestRunnable {

		@Override
		public void runTest() throws Throwable {
			String request = "第" + this.hashCode() + "条信息";
			Assert.assertTrue(producer.convertAndSendMessage(request));
		}
		
	}

}
