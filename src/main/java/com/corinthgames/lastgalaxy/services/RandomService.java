package com.corinthgames.lastgalaxy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by CorayThan on 10/24/2014.
 */
@Service
public class RandomService {
	@Autowired
	private Random random;

	//this can be used to print out results to put in a bar graph using open office calc
	public static void main(String...args) {
		RandomService ran = new RandomService();
		ran.random = new Random();
		Map<Integer, Integer> result = new HashMap<>();
		for (int x = 0; x < 1000000; x++) {
			int number = 0;
			if (ran.random.nextInt(150) == 0) {
				number = 0;
			} else if (ran.random.nextInt(60) == 0) {
				number = ran.randomIntOnCurve(8, 3, -4);
			} else if (ran.random.nextInt(65) == 0) {
				number = ran.randomIntOnCurve(16, 3, 2) + 6;
			} else {
				number = ran.randomIntOnCurve(131, 5, 17);
			}

			Integer count = result.get(number);
			if (count==null) {
				count = 0;
			}
			count++;
			result.put(number, count);
		}
		for (int x = 0; x < 131; x++) {
			Integer num = result.get(x);
			if (num == null) {
				num = 0;
			}
			System.out.println(num);
		}
	}

	public Random getRandom() {
		return random;
	}

	public int generateCharacterStat(int moveCurve) {
		return randomIntOnCurve(100, 3, moveCurve);
	}

	/**
	 * Uses random int on curve to generate a range between a min and a max with a target center. You may pass in a curve
	 * center that is less than or greater than the min or max, and it will be set to the min or max
	 *
	 * @param min
	 * @param max
	 * @param curveFactor
	 * @param curveCenter
	 * @return
	 */
	public int randomIntBetween(int min, int max, int curveFactor, int curveCenter) {
		if (min >= max) {
			throw new IllegalArgumentException("min must be less than max");
		} else if (curveCenter > max) {
			curveCenter = max;
		} else if (curveCenter < min) {
			curveCenter = min;
		}
		int range = max - min;
		int targetCenter = curveCenter - min;
		int normalCenter = range / 2;
		targetCenter = targetCenter - normalCenter;
		if (targetCenter > range / 2) {
			targetCenter--;
		} else if (targetCenter < - (range / 2)) {
			targetCenter++;
		}
		return min + randomIntOnCurve(range, curveFactor, targetCenter);
	}

	/**
	 * Generates an int between 0 and the range (exclusive). The int will be randomly distributed
	 * using the curveFactor (1 for flat line, 3 for bell curve, etc.). The curve will be offset
	 * from center using the moveCurve. E.g. range 100 curveFactor 3 moveCurve -10 will create a
	 * bell curve ranging from 0 to 99 centered on 39
	 *
	 * @return
	 */
	public int randomIntOnCurve(int range, int curveFactor, int moveCurve) {
		if (Math.abs(moveCurve) > range / 2) {
			throw new IllegalArgumentException("moveCurve cannot move the curve beyond the range. Range: " + range +
					" curveFactor: " + curveFactor + " moveCurve: " + moveCurve);
		}
		boolean oddRange = range % 2 == 1;
		int side = random.nextInt(range);
		boolean aboveAverage = side > range / 2 + moveCurve;
		int modifiedRange;
		if (aboveAverage) {
			modifiedRange = range / 2 - moveCurve;
		} else {
			modifiedRange = range / 2 + moveCurve;
		}

		int result = 0;
		if (modifiedRange > 0) {
			for (int x = 0; x < curveFactor; x++) {
					result+=random.nextInt(modifiedRange * 2);
			}
		}
		result /= curveFactor;
		result = Math.abs(result - modifiedRange);
		if (aboveAverage) {
			result += range - modifiedRange;
			if (oddRange && random.nextBoolean()) {
				result--;
			}
		} else {
			result = modifiedRange - result;
			if (oddRange && random.nextBoolean()) {
				result++;
			}
		}
		return result;
	}
}
