package model;

import java.awt.*;
import java.util.Objects;

public class DrawableParticle {
    public DrawableParticle(int x, int y, int r, Color c) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.c = c;
    }

    final int x;
    final int y;
    final int r;
    final Color c;

    public void draw(Graphics2D g) {
        g.setColor(c);
        g.drawOval(x - r, y - r, r * 2, r * 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawableParticle that = (DrawableParticle) o;
        return x == that.x &&
                y == that.y &&
                r == that.r &&
                Objects.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, c);
    }
}
