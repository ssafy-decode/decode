package com.decode.web.domain.user.enums;

public enum Tier {
    BRONZE("bronze", 100), SILVER("silver", 250), GOLD("gold", 450), PLATINUM("platinum",
            700), DIAMOND("diamond", 1000), RUBY("ruby", 1500);

    private final String tier;
    private final int exp;

    Tier(String tier, int exp) {
        this.tier = tier;
        this.exp = exp;
    }

    public static String expToTier(int exp) {
        if (exp < BRONZE.exp) {
            return BRONZE.tier;
        }
        if (exp < SILVER.exp) {
            return SILVER.tier;
        }
        if (exp < GOLD.exp) {
            return GOLD.tier;
        }
        if (exp < PLATINUM.exp) {
            return PLATINUM.tier;
        }
        if (exp < DIAMOND.exp) {
            return DIAMOND.tier;
        }
        return RUBY.tier;
    }

    public String getTier() {
        return tier;
    }
}
