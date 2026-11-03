package optifine;

public class CustomColors extends net.optifine.CustomColors {
    public static int getPotionId(String name) {
        if (name == null) return -1;
        name = name.toLowerCase().replace("potion.", "").replace("effect.", "");
        return switch (name) {
            case "speed", "movespeed" -> 1;
            case "slowness", "moveslowdown" -> 2;
            case "haste", "digspeed" -> 3;
            case "mining_fatigue", "digslowdown" -> 4;
            case "strength", "damageboost" -> 5;
            case "instant_health", "heal" -> 6;
            case "instant_damage", "harm" -> 7;
            case "jump_boost", "jump" -> 8;
            case "nausea", "confusion" -> 9;
            case "regeneration" -> 10;
            case "resistance" -> 11;
            case "fire_resistance", "fireresistance" -> 12;
            case "water_breathing", "waterbreathing" -> 13;
            case "invisibility" -> 14;
            case "blindness" -> 15;
            case "night_vision", "nightvision" -> 16;
            case "hunger" -> 17;
            case "weakness" -> 18;
            case "poison" -> 19;
            case "wither" -> 20;
            case "health_boost", "healthboost" -> 21;
            case "absorption" -> 22;
            default -> 1;
        };
    }
}
