package com.corinthgames.lastgalaxy.enums;

import com.nathanwestlake.dartgenerator.annotations.DartInclude;

/**
 * Created by CorayThan on 10/28/2014.
 */
@DartInclude
public enum Specialization {
	POLITICS(Stat.LEADERSHIP, Stat.CHARISMA),
	DEFENSE(Stat.LEADERSHIP, Stat.ATHLETICISM),
	ESPIONAGE(Stat.ATHLETICISM, Stat.INTELLIGENCE),
	RELIGION(Stat.WISDOM, Stat.CHARISMA),
	THE_ARTS(Stat.CHARISMA, Stat.ATHLETICISM);

	private final Stat primaryStat;
	private final Stat secondaryStat;

	private Specialization(Stat primaryStat, Stat secondaryStat) {
		this.primaryStat = primaryStat;
		this.secondaryStat = secondaryStat;
	}

	public Stat getPrimaryStat() {
		return primaryStat;
	}

	public Stat getSecondaryStat() {
		return secondaryStat;
	}
}
