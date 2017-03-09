package backend.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.SlogoException;
import backend.states.ColorList;
import backend.states.State;
import backend.states.StatesList;

public class SetPalette extends Command {
	
	private final int NUM_PARAMS = 4;
	private List<String> paramsNeeded = new ArrayList<String>(Arrays.asList(new String []{"Constant", "Constant", "Constant", "Constant"}));

	/**
	 * @param list
	 */
	public SetPalette(StatesList<State> list) {
		super(list);
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#runCommand(double, double, double, double)
	 */
	@Override
	public double runCommand(double a, double b, double c, double d) throws SlogoException {
		if ((int)b >= 0 && (int)b <= 255 && (int)c >= 0 && (int)c <= 255 && (int)d >= 0 && (int)b <= 255){
			int index = ColorList.setColor((int)a, (int)b, (int)c, (int)d);
			if (index != -1) {return index;
			}
			else {
				throw new SlogoException("ParamOutOfRange");
			}
		}
		else {
			throw new SlogoException("ParamOutOfRange");
		}
	}
	
	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#numParamsNeeded()
	 */
	@Override
	public Integer numParamsNeeded() {
		return NUM_PARAMS;
	}

	/* (non-Javadoc)
	 * @see backend.interpreter.commands.Command#paramsNeeded()
	 */
	@Override
	public List<String> paramsNeeded() {
		return paramsNeeded;
	}

}
