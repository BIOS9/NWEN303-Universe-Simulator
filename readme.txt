How to run this code:
Run the main method inside the GUI class.

To switch between ModelParallel and Model:
Change the class in the DataSetLoader arguments inside the main method. e.g

Parallel:
Model m = DataSetLoader.getElaborate(ModelParallel.class, 200, 700, 2, 0.99);

Sequential:
Model m = DataSetLoader.getElaborate(Model.class, 200, 700, 2, 0.99);


There are two test files inside the src/test folder:
EqualityTest: Checks that ModelParallel and Model behave the same.
PerformanceTest: Prints out and checks the time taken for both models to run the same simulation.


Bugs:
The parallel version runs slower on my computer (Not sure how it would behave on a computer with more/faster cores)