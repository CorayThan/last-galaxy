package com.corinthgames.lastgalaxy.services;

import com.corinthgames.lastgalaxy.models.Person;

import java.time.*;

/**
 * In the game, dates are offset from real time by 2,000 years, starting with the year 2,000. Time increases at 12 fold
 * speed, so each real life month = 1 in game year.
 */
public class GameTime {

	private static final LocalDateTime BEGINNING_OF_TIME = LocalDateTime.of(LocalDate.ofYearDay(2000, 1), LocalTime.of(0, 0));
	private static final LocalDateTime BEGINNING_OF_GAME_TIME = LocalDateTime.of(LocalDate.ofYearDay(4000, 1), LocalTime.of(0, 0));
	private static final Clock PACIFIC_CLOCK = Clock.system(ZoneId.of("America/Los_Angeles"));
	private static final int SCALE_FACTOR = 12;

	/**
	 * Get the current game localDateTime
	 *
	 * @return
	 */
	public static LocalDateTime getGameTime() {
		return GameTime.getGameTime(0, 0);
	}

	/**
	 * Get a game localDateTime plus or minus the given years and days
	 *
	 * @param yearsMod
	 * @param daysMod
	 * @return
	 */
	public static LocalDateTime getGameTime(long yearsMod, long daysMod) {
		Duration timeBetween = Duration.between(BEGINNING_OF_TIME, LocalDateTime.now(PACIFIC_CLOCK));
		timeBetween = timeBetween.multipliedBy(SCALE_FACTOR);
		if (daysMod != 0) {
			timeBetween = timeBetween.plusDays(daysMod);
		}
		if (yearsMod != 0) {
			timeBetween = timeBetween.plusMinutes(525949l * yearsMod);
		}
		return BEGINNING_OF_GAME_TIME.plus(timeBetween);
	}

	/**
	 * This method is similar to {@link #getGameTime()}
	 */
	public static LocalDate getGameDate() {
		return getGameTime().toLocalDate();
	}

	/**
	 * This method is similar to {@link #getGameTime(long, long)}
	 */
	public static LocalDate getGameDate(int yearsMod, int daysMod) {
		return getGameTime(yearsMod, daysMod).toLocalDate();
	}

	public static Period getPeriod(LocalDate date) {
		return Period.between(getGameDate(), date);
	}

	public static boolean stillAlive(Person character) {
		return character.getDeathday().isAfter(GameTime.getGameDate());
	}
}