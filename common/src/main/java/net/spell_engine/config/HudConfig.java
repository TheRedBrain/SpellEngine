package net.spell_engine.config;

import net.spell_engine.client.gui.HudElement;
import net.minecraft.util.math.Vec2f;

import java.util.List;

public class HudConfig { HudConfig() { }
    public HudElement base;
    public Part target;
    public Part icon;
    public int bar_width;

    public HudConfig(HudElement base, Part target, Part icon, int bar_width) {
        this.base = base;
        this.target = target;
        this.icon = icon;
        this.bar_width = bar_width;
    }

    public static class Part { public Part() { }
        public boolean visible = true;
        public Vec2f offset = Vec2f.ZERO;
        public Part(boolean visible, Vec2f offset) {
            this.visible = visible;
            this.offset = offset;
        }
    }

    // MARK: Default and Presets

    public static HudConfig createDefault() {
        return presets.get(0);
    }

    private static HudConfig overXPBar() {
        return new HudConfig(
                new HudElement(
                        HudElement.Origin.BOTTOM,
                        new Vec2f(0, -28)),
                new Part(
                        false,
                        new Vec2f(0, -12)),
                new Part(
                        true,
                        new Vec2f(-8, -25)),
                172);
    }

    public static final List<HudConfig> presets = List.of(
            overXPBar(),
            preset(HudElement.Origin.TOP_LEFT),
            preset(HudElement.Origin.TOP),
            preset(HudElement.Origin.TOP_RIGHT),
            preset(HudElement.Origin.BOTTOM_RIGHT),
            preset(HudElement.Origin.BOTTOM),
            preset(HudElement.Origin.BOTTOM_LEFT)
    );

    private static HudConfig preset(HudElement.Origin origin) {
        int offsetW = 70;
        int offsetH = 16;
        var barWidth = 90;
        var offset = new Vec2f(0, 0);
        var target = new Part();
        var icon = new Part();
        switch (origin) {
            case TOP -> {
                offset = new Vec2f(0, offsetH);
                target.offset = targetOffsetDown();
                icon.offset = iconRight(barWidth);
            }
            case TOP_LEFT -> {
                offset = new Vec2f(offsetW - 8, offsetH);
                target.offset = targetOffsetDown();
                icon.offset = iconRight(barWidth);
            }
            case TOP_RIGHT -> {
                offset = new Vec2f((-1) * offsetW + 8, offsetH);
                target.offset = targetOffsetDown();
                icon.offset = iconLeft(barWidth);
            }
            case BOTTOM -> {
                offset = new Vec2f(0, (-1) * offsetH);
                target.offset = targetOffsetUp();
                icon.offset = iconRight(barWidth);
            }
            case BOTTOM_LEFT -> {
                offset = new Vec2f(offsetW - 8, (-1) * offsetH);
                target.offset = targetOffsetUp();
                icon.offset = iconRight(barWidth);
            }
            case BOTTOM_RIGHT -> {
                offset = new Vec2f((-1) * offsetW + 8, (-1) * offsetH);
                target.offset = targetOffsetUp();
                icon.offset = iconLeft(barWidth);
            }
        }

        var config = new HudConfig();
        config.base = new HudElement(origin, offset);
        config.target = target;
        config.icon = icon;
        config.bar_width = barWidth;
        return config;
    }

    private static Vec2f targetOffsetUp() {
        return new Vec2f(0, -12);
    }

    private static Vec2f targetOffsetDown() {
        return new Vec2f(0, 12);
    }

    private static Vec2f iconLeft(int barWidth) {
        return new Vec2f(- (barWidth / 2) - 10 - 16, -6);
    }

    private static Vec2f iconRight(int barWidth) {
        return new Vec2f((barWidth / 2) + 10, -6);
    }
}