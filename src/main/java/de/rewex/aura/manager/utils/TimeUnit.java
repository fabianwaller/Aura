package de.rewex.aura.manager.utils;

import java.util.ArrayList;
import java.util.List;

public enum TimeUnit {

    SECOND("Sekunde(n)", 1, "sec"),  MINUTE("Minute(n)", 60, "min"),  HOUR("Stunde(n)", 3600, "hour"),  DAY("Tag(e)", 86400, "day"),  WEEK("Woche(n)", 604800, "week");

    private String name;
    private int toSecond;
    private String shortcut;

    private TimeUnit(String name, int toSecond, String shortcut) {
        this.name = name;
        this.toSecond = toSecond;
        this.shortcut = shortcut;
    }

    public int getToSecond() {
        return this.toSecond;
    }

    public String getName() {
        return this.name;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<String> getUnitsAsString() {
        List<String> units = new ArrayList();
        TimeUnit[] arrayofPassUnit;
        int j = (arrayofPassUnit = TimeUnit.values()).length;
        for (int i = 0; i < j; i++) {
            TimeUnit unit = arrayofPassUnit[i];
            units.add(unit.getShortcut().toLowerCase());
        }
        return units;
    }

    public static TimeUnit getUnit(String unit) {
        TimeUnit[] arrayOfMuteUnit;
        int j = (arrayOfMuteUnit = values()).length;
        for (int i = 0; i < j; i++) {
            TimeUnit units = arrayOfMuteUnit[i];
            if (units.getShortcut().toLowerCase().equals(unit.toLowerCase())) {
                return units;
            }
        }
        return null;
    }

}
