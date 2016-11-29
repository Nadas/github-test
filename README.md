Prompt Overview
--------
An analysis of the test coverage of a given GitHub user's public projects source code based on the number of test classes and methods.

"Given a GitHub user, automatically analyze all the public projects source code and check the number of test classes, methods being tested, or test coverage, and report whether the user is a good tester or not."
	
Project Process
--------
1. Clone User's Repository
2. Compile Repository
3. List Classes
4. List Methods
5. Generate Report

Assumptions and Limitations
--------
1. Java Projects.
2. Maven Projects.
3. Class coverage is calculated as a ratio of the number test classes vs java classes.
4. A class is only counted as a test class if it ends with “ClassNameTest.java”
5. i.e. a small project with 6 *.java classes and 3 *Test.java classes, test class coverage = 50%. (not ideal)
6. Method coverage is calculated as the ratio of the number of test  methods in the test class vs the number of methods in  its corresponding class.
7. A method is only counted if it starts with testMethodName


Future Improvements
--------
1. Automate the process. 
2. Find an alternative to cloning projects locally.
3. Calculate line coverage during compilation.
4. Using better test measurements than a ration of #ofTestClasss:#ofMainClasses
5. Again, better test measurements for when calculating methods being tested, as this program does not consider the case of multiple test methods for one main method.
6. (Hence the detailed report listing names of classes/methods)
