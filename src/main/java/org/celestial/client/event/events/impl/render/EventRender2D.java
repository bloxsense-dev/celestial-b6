package org.celestial.client.event.events.impl.render;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.ScaledResolution;
import org.celestial.client.event.events.Event;

public class EventRender2D implements Event {

    private final ScaledResolution resolution;
    private final GuiGraphicsExtractor graphics;

    public EventRender2D(ScaledResolution resolution) {
        this(resolution, null);
    }

    public EventRender2D(ScaledResolution resolution, GuiGraphicsExtractor graphics) {
        this.resolution = resolution;
        this.graphics = graphics;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

    public GuiGraphicsExtractor getGraphics() {
        return graphics;
    }
}
