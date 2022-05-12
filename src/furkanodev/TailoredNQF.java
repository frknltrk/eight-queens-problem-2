package furkanodev;

import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctions;
import aima.core.util.datastructure.XYLocation;

public class TailoredNQF extends NQueensFunctions {
	public static boolean testGoal(NQueensBoard state) {
		return state.getNumberOfQueensOnBoard() == state.getSize() &&
			   state.getNumberOfAttackingPairs() == 0 &&
			   state.queenExistsAt(new XYLocation(0,3)) &&
			   state.queenExistsAt(new XYLocation(1,1)) &&
			   state.queenExistsAt(new XYLocation(2,6)) &&
			   state.queenExistsAt(new XYLocation(3,2)) &&
			   state.queenExistsAt(new XYLocation(4,5)) &&
			   state.queenExistsAt(new XYLocation(5,7)) &&
			   state.queenExistsAt(new XYLocation(6,4)) &&
			   state.queenExistsAt(new XYLocation(7,0))
			   ;
	}
}