package com.corinthgames.lastgalaxy.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

/**
 * Created by CorayThan on 10/24/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestRandomService {
	@InjectMocks
	RandomService randomService;

	@Mock
	Random random;

	@Before
	public void setUp() {

	}

	@Test
	public void testLowRandom() {
//		Mockito.when(random.nextInt(48)).thenReturn(0);
//		Mockito.when(random.nextBoolean()).thenReturn(false);
//		Assert.assertEquals(0, randomService.randomIntOnCurve(99, 3, -25));
	}

//	@Test
//	public void testRandom() {
//		Mockito.when(random.nextInt(48)).thenReturn(0);
//		Mockito.when(random.nextBoolean()).thenReturn(false);
//		Assert.assertEquals(0, randomService.randomIntOnCurve(99, 3, -25));
//	}
//
//	@Test
//	public void testHighRandom() {
//		Mockito.when(random.nextInt(156)).thenReturn(155);
//		Mockito.when(random.nextBoolean()).thenReturn(true);
//		Assert.assertEquals(99, randomService.randomIntOnCurve(99, 3, -25));
//	}
}
