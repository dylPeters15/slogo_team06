package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.ShapeList;
import backend.states.State;
import backend.states.StatesList;

public class SetShape extends Command {
	
	private final int NUM_PARAMS = 1;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant"}));

	public SetShape(StatesList<State> list) {
		super(list);
	}

	@Override
	public double runCommand(double a) throws SlogoException {
		State newState = getNewState();
		ShapeList newShapeList = ShapeList.fromInt((int)Math.round(a));
		if (newShapeList != null) {
			newState.setTurtleShapeChanged(true);
			newState.setTurtleShape(newShapeList);
			addNewState(newState);
			return a;
		}
		else {
			throw new SlogoException("ParamOutOfRange");
		}
	}

	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
