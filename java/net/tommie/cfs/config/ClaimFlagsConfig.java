package net.tommie.cfs.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClaimFlagsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> pay_rent_period;
    public static final ForgeConfigSpec.ConfigValue<Integer> rent_cost;
    public static final ForgeConfigSpec.ConfigValue<Integer> kingdom_upkeep;
    public static final ForgeConfigSpec.ConfigValue<Integer> claim_health;
    public static final ForgeConfigSpec.ConfigValue<Integer> pay_rent_distance;
    public static final ForgeConfigSpec.ConfigValue<Integer> claim_radius;

    static{
        BUILDER.push("Config for Tommie's Claim Flags Mod");

        pay_rent_period = BUILDER.comment("How often a kingdom should pay rent. Minimum value is 10 seconds maximum is 3600. Default value is 60 seconds.").defineInRange("Pay Rent Period", 60, 10, 3600);
        rent_cost = BUILDER.comment("How much kingdom rent should cost for each claim per *pay rent distance* from the claim to the kingdom. Default value is 2 gold nuggets. Minimum value is 1 maximum is 576").defineInRange("Rent Cost", 2,1,576);
        kingdom_upkeep = BUILDER.comment("How much the rent will be for just the kingdom claim itself. Default value is 5 gold nuggets. Minimum value is 1 Maximum is 576").defineInRange("Kingdom Upkeep",5,1,576);
        claim_health = BUILDER.comment("How long it should take a player to overclaim a territory. Minimum value is 3 Maximum is 1200 seconds. Default is 5 seconds.").defineInRange("Claim Health",5,3,2400);
        claim_radius = BUILDER.comment("How big a square a claim flag should claim,these should only be uneven numbers, 1x1, 3x3, 5x5 etc.. be aware that if claims overlap on the corner it wont be a claim but an overclaim!").defineInRange("Claim radius", 1,1,15);
        pay_rent_distance = BUILDER.comment("The distance per which rent should be applied, calculated from each flag to the corresponding kingdom block, the kingdom pays 1 rent cost for each flag, for each distance the flag is away from the kingdom; For example if this distance is 100 and the flag is placed 250 blocks away the kingdom will pay 2 rent costs for that flag and its claimed territories per pay-rent cycle").defineInRange("Pay rent distance", 100, 16, 10000);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
