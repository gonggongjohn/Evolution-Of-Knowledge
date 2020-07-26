package com.gonggongjohn.eok.api.utils.physics;

import java.util.ArrayList;
import java.util.HashMap;

public interface ILawBase {

    public String getLaw();

    public double solve(ParameterList unknownParameter);

    public void addToEquationSolver(EquationSolver solver, ILawBase counter);
    public ParameterList[] getUnknownParameter();
    public HashMap<ParameterList,Double> getValue();
}
