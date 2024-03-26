package org.zombiesplugin.zombies;

public enum TeamColors {
	None, Red, Blue, Yellow, Green;
	
	public TeamColors GetNext() {
        int nextIndex = this.ordinal() + 1;

        if(nextIndex == TeamColors.values().length -1) {
            return this;
        }

        return TeamColors.values()[nextIndex];
    }
}
