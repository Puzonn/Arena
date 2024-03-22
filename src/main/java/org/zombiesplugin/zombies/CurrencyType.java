package org.zombiesplugin.zombies;

public enum CurrencyType {
    Coal,
    Iron,
    Diamond;

    public CurrencyType GetNext() {
        int nextIndex = this.ordinal() + 1;

        if(nextIndex == CurrencyType.values().length -1) {
            return this;
        }

        return CurrencyType.values()[nextIndex];
    }
}
