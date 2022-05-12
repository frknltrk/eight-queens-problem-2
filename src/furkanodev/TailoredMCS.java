// https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/MinConflictsSolver.java

package furkanodev;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import aima.core.util.Tasks;
import aima.core.util.Util;

import java.util.*;

public class TailoredMCS<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {
	private int maxSteps;
	
	public TailoredMCS(int maxSteps) {
		this.maxSteps = maxSteps;
	}
	
	public Assignment<VAR, VAL> createAssignment(CSP<VAR, VAL> csp) {
		Assignment<VAR, VAL> assignment = new Assignment<>();
		VAR firstQueen = csp.getVariables().get(0);
		VAR secondQueen = csp.getVariables().get(1);
		assignment.add(firstQueen, csp.getDomain(firstQueen).get(3));
		assignment.add(secondQueen, csp.getDomain(secondQueen).get(1));
		for(int i=2;i<8;i++) {
			VAL randomValue = Util.selectRandomlyFromList(csp.getDomain(csp.getVariables().get(i)).asList());
			assignment.add(csp.getVariables().get(i), randomValue);
		}
		return assignment;
	}

	@Override
	public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
		Assignment<VAR, VAL> current = createAssignment(csp);
		fireStateChanged(csp, current, null);
		for (int i = 0; i < maxSteps && !Tasks.currIsCancelled(); i++) {
			System.out.println((i+1)+". STEP");
			if (current.isSolution(csp)) {
				return Optional.of(current);
			} else {
				Set<VAR> vars = getConflictedVariables(current, csp);
				System.out.println(vars);
				VAR var = Util.selectRandomlyFromSet(vars);
				VAL value = getMinConflictValueFor(var, current, csp);
				current.add(var, value);
				fireStateChanged(csp, current, var);
			}
		}
		return Optional.empty();
	}
	
	private Set<VAR> getConflictedVariables(Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
		Set<VAR> result = new LinkedHashSet<>();
		csp.getConstraints().stream().filter(constraint ->
											!constraint.isSatisfiedWith(assignment)
											).map(Constraint::getScope).forEach(scope -> {
												for (VAR var: scope) {
													if (var != csp.getVariables().get(0) &&
														var != csp.getVariables().get(1)) {
														result.add(var);
													}
												}
											});
		return result;
	}
	
	private VAL getMinConflictValueFor(VAR var, Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
		List<Constraint<VAR, VAL>> constraints = csp.getConstraints(var);
		Assignment<VAR, VAL> testAssignment = assignment.clone();
		int minConflict = Integer.MAX_VALUE;
		List<VAL> resultCandidates = new ArrayList<>();
		for (VAL value : csp.getDomain(var)) {
			testAssignment.add(var, value);
			int currConflict = countConflicts(testAssignment, constraints);
			if (currConflict <= minConflict) {
				if (currConflict < minConflict) {
					resultCandidates.clear();
					minConflict = currConflict;
				}
				resultCandidates.add(value);
			}
		}
		 return (!resultCandidates.isEmpty()) ? Util.selectRandomlyFromList(resultCandidates) : null;
	}
	
	private int countConflicts(Assignment<VAR, VAL> assignment, List<Constraint<VAR, VAL>> constraints) {
		return (int) constraints.stream().filter(constraint -> !constraint.isSatisfiedWith(assignment)).count();
	}
	

}
