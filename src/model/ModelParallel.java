package model;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ModelParallel extends Model {

    public void step() {
        p.stream()
                .parallel()
                .forEach(p -> p.interact(this));

        p.stream()
                .parallel()
                .forEach(Particle::move);

        mergeParticles();
        updateGraphicalRepresentation();
    }

    private void updateGraphicalRepresentation() {
        Color c = Color.ORANGE;
        List<DrawableParticle> d = p.parallelStream()
                .parallel()
                .map(p -> new DrawableParticle((int) p.x, (int) p.y, (int) Math.sqrt(p.mass), c))
                .collect(Collectors.toList());
        this.pDraw = d;//atomic update
    }

    public void mergeParticles() {
        Stack<Particle> deadPs = new Stack<>();
        deadPs.addAll(p.stream().parallel().flatMap(x -> x.impacting.stream().parallel()).collect(Collectors.toList()));

        this.p.removeAll(deadPs);
        while (!deadPs.isEmpty()) {
            Particle current = deadPs.pop();
            Set<Particle> ps = getSingleChunck(current);
            deadPs.removeAll(ps);
            this.p.add(mergeParticles(ps));
        }
    }

    /**
     * Returns a set of particles that are impacting each other.
     * @param current
     * @return
     */
    private Set<Particle> getSingleChunck(Particle current) {
        Set<Particle> impacting = new HashSet<Particle>();
        impacting.add(current);

        while (true) {
            Set<Particle> tmp = impacting.stream().parallel().flatMap(x -> x.impacting.stream().parallel()).collect(Collectors.toSet());
            boolean changed = impacting.addAll(tmp);
            if (!changed) {
                break;
            }
        }
        //now impacting have all the chunk of collapsing particles
        return impacting;
    }

    public Particle mergeParticles(Set<Particle> ps) {
        Particle p = ps.stream().parallel().reduce((p1, p2) -> {
            double mass = p1.mass + p2.mass;
            double x = (p1.x * p1.mass) + (p2.x * p2.mass);
            double y = (p1.y * p1.mass) + (p2.y * p2.mass);
            double speedX = (p1.speedX * p1.mass) + (p2.speedX * p2.mass);
            double speedY = (p1.speedY * p1.mass) + (p2.speedY * p2.mass);

            return new Particle(mass, speedX / mass, speedY / mass, x / mass, y / mass);
        }).get();

        return new Particle(
                p.mass,
                p.speedX,
                p.speedY,
                p.x,
                p.y);
    }
}
