package test;

import datasets.DataSetLoader;
import model.Model;
import model.ModelParallel;
import model.Particle;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EqualityTest {

    @Test
    public void TestEqualityElaborate1() {
        Model model = DataSetLoader.getElaborate(Model.class, 200, 300, 2, 0.99);
        Model modelParallel = DataSetLoader.getElaborate(ModelParallel.class, 200, 300, 2, 0.99);

        testWithModels(model, modelParallel);
    }

    @Test
    public void TestEqualityRandomSet1() {
        Model model = DataSetLoader.getRandomSet(Model.class, 100, 800, 200);
        Model modelParallel = DataSetLoader.getRandomSet(ModelParallel.class, 100, 800, 200);

        testWithModels(model, modelParallel);
    }

    @Test
    public void TestEqualityRandomSet2() {
        Model model = DataSetLoader.getRandomSet(Model.class, 100, 800, 100);
        Model modelParallel = DataSetLoader.getRandomSet(ModelParallel.class, 100, 800, 100);

        testWithModels(model, modelParallel);
    }

    @Test
    public void TestEqualityRandomGrid1() {
        Model model = DataSetLoader.getRandomGrid(Model.class, 100, 400, 30);
        Model modelParallel = DataSetLoader.getRandomGrid(ModelParallel.class, 100, 400, 30);

        testWithModels(model, modelParallel);
    }

    @Test
    public void TestEqualityRegularGrid1() {
        Model model = DataSetLoader.getRegularGrid(Model.class, 100, 800, 40);
        Model modelParallel = DataSetLoader.getRegularGrid(ModelParallel.class, 100, 800, 40);

        testWithModels(model, modelParallel);
    }

    @Test
    public void TestEqualityRotatingGrid1() {
        Model model = DataSetLoader.getRandomRotatingGrid(Model.class, 0.02d,100, 800, 40);
        Model modelParallel = DataSetLoader.getRandomRotatingGrid(ModelParallel.class, 0.02d,100, 800, 40);

        testWithModels(model, modelParallel);
    }

    @Test
    public void TestEqualityRotatingGrid2() {
        Model model = DataSetLoader.getRandomRotatingGrid(Model.class, 0.02d,100, 800, 30);
        Model modelParallel = DataSetLoader.getRandomRotatingGrid(ModelParallel.class, 0.02d,100, 800, 30);

        testWithModels(model, modelParallel);
    }


    public void testWithModels(Model m1, Model m2) {
        assertTrue(isSimilar(m1.p, m2.p));

        for(int i = 0; i < 10000; ++i) {
            m1.step();
            m2.step();
            assertTrue(isSimilar(m1.p, m2.p));
        }
    }

    /**
     * Used to find the difference in two lists of drawable particles.
     * @param p1
     * @param p2
     */
    public boolean isSimilar(List<Particle> p1, List<Particle> p2) {
        Iterator<Particle> p1Iterator = p1.iterator();
        Iterator<Particle> p2Iterator = p2.iterator();

        while(p1Iterator.hasNext()) {
            Particle particle1 = p1Iterator.next();
            Particle particle2 = p2Iterator.next();

            if(!particle1.similar(particle2)) {
                return  false;
            }
        }

        if(p2Iterator.hasNext()) {
            Particle p = p2Iterator.next(); // Put value here for debugger to read
            return  false;
        }

        return  true;
    }
}
