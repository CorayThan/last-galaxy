package com.corinthgames.lastgalaxy.enums;

import com.nathanwestlake.dartgenerator.annotations.DartInclude;

/**
 * Primary Stat increases profits, secondary stat reduces costs
 *
 * Created by CorayThan on 10/28/2014.
 */
@DartInclude
public enum Industry {
	MINING(Stat.INTELLIGENCE, Stat.ATHLETICISM),
	MANUFACTURING(Stat.INTELLIGENCE, Stat.LEADERSHIP),
	CONSTRUCTION(Stat.ATHLETICISM, Stat.LEADERSHIP),
	TRADE(Stat.CHARISMA, Stat.LEADERSHIP),
	TRANS(Stat.LEADERSHIP, Stat.WISDOM),
	MEDICINE(Stat.WISDOM, Stat.INTELLIGENCE),
	TECH(Stat.INTELLIGENCE, Stat.WISDOM),
	AGRICULTURE(Stat.ATHLETICISM, Stat.WISDOM),
	MEDIA(Stat.CHARISMA, Stat.WISDOM);
	
	private final Stat primaryStat;
	private final Stat secondaryStat;

	private Industry(Stat primaryStat, Stat secondaryStat) {
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
