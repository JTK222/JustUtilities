package net.brazier_modding.justutilities.events.event_types;

import net.minecraft.world.level.Level;

public class LevelChangeEvent {

	private Level previousLevel;
	private Level level;

	public Level getPreviousLevel() {
		return previousLevel;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void setPreviousLevel(Level previousLevel) {
		this.previousLevel = previousLevel;
	}
}
