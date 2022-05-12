/*
THE GOAL STATE
-------Q			
-Q------
---Q----
Q-------
------Q-
----Q---
--Q-----
-----Q--
*/

package furkanodev;
import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.examples.NQueensCSP;

import java.util.List;
import java.util.Optional;

public class App {
	
	public static void testSolution() {
		NQueensCSP csp = new NQueensCSP(8);
		List<Variable> vars = csp.getVariables();
		int values[] = {3, 1, 6, 2, 5, 7, 4, 0}; // rows
//		csp.getVariables().forEach(v -> System.out.println(v));
//		csp.getConstraints().forEach(c -> System.out.println(c));
		Assignment<Variable, Integer> assignment = new Assignment<>();
		for(int i=0;i<8;i++) {
			assignment.add(vars.get(i), values[i]);
		}

		System.out.println(assignment.isSolution(csp)); // solution = complete AND consistent
	}

	public static void main(String[] args) {
		CSP<Variable, Integer> csp = new NQueensCSP(8);
		TailoredMCS<Variable, Integer> mcs = new TailoredMCS<Variable, Integer>(1000); // set maxSteps to any value you like
		System.out.println(mcs.createAssignment(csp) + "\n");
		
		Optional<Assignment<Variable, Integer>> results = mcs.solve(csp);
		
		System.out.println("\nResult -> " + results);
	}
}

/*
* create an "assignment" : https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/test/java/aima/test/core/unit/search/csp/AssignmentTest.java
* https://www.cs.toronto.edu/~fbacchus/Presentations/CSP-BasicIntro.pdf
* We must find a value for each of the variables that satisfies all of the constraints.
* https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/test/java/aima/test/core/unit/search/csp/MapCSPTest.java
*/