package org.celestial.client.settings.impl;

import org.celestial.client.settings.Setting;

import java.util.function.Supplier;

public class NumberSetting extends Setting {

    private final NumberType type;
    private float current, minimum, maximum, increment;
    private String desc;

    public NumberSetting(String name, float current, float minimum, float maximum, float increment, Supplier<Boolean> visible) {
        this.name = name;
        this.minimum = minimum;
        this.current = current;
        this.maximum = maximum;
        this.increment = increment;
        this.type = NumberType.DEFAULT;
        setVisible(visible);
    }

    public NumberSetting(String name, float current, float minimum, float maximum, float increment, Supplier<Boolean> visible, NumberType type) {
        this.name = name;
        this.minimum = minimum;
        this.current = current;
        this.maximum = maximum;
        this.increment = increment;
        this.type = type;
        setVisible(visible);
    }

    public NumberSetting(String name, String desc, float current, float minimum, float maximum, float increment, Supplier<Boolean> visible) {
        this.name = name;
        this.desc = desc;
        this.minimum = minimum;
        this.current = current;
        this.maximum = maximum;
        this.increment = increment;
        this.type = NumberType.DEFAULT;
        setVisible(visible);
    }

    public NumberSetting(String name, String desc, float current, float minimum, float maximum, float increment, Supplier<Boolean> visible, NumberType type) {
        this.name = name;
        this.desc = desc;
        this.minimum = minimum;
        this.current = current;
        this.maximum = maximum;
        this.increment = increment;
        this.type = type;
        setVisible(visible);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getMinimum() {
        return minimum;
    }

    public void setMinValue(float minimum) {
        this.minimum = minimum;
    }

    public float getMaximum() {
        return maximum;
    }

    public void setMaxValue(float maximum) {
        this.maximum = maximum;
    }

    public float getCurrentValue() {
        return current;
    }

    public void setCurrentValue(float current) {
        this.current = current;
    }

    public float getIncrement() {
        return increment;
    }

    public void setIncrement(float increment) {
        this.increment = increment;
    }

    public NumberType getType() {
        return type;
    }

    public enum NumberType {

        MS("Ms"), APS("Aps"), SIZE("Size"), PERCENTAGE("Percentage"), DISTANCE("Distance"), DEFAULT("");

        String name;

        NumberType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
