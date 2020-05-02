package gui;

import model.DrawableParticle;
import model.Model;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
    Model m;

    Canvas(Model m) {
        this.m = m;
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setBackground(Color.DARK_GRAY);
        g.clearRect(0, 0, getWidth(), getHeight());
        for (DrawableParticle p : m.pDraw) {
            p.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) Model.size, (int) Model.size);
    }
}
