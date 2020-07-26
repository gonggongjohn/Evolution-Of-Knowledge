package com.gonggongjohn.eok.api.utils.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EquationSolver {
    public HashMap<ParameterList,Double> unknownParameterList;
    public ArrayList<ILawBase> lawList;
    public HashMap<ParameterList, Double> result;

    private double step;     //the step that the parameter change per turn
    private double epsilon;  //error between the equation that can be allowed

    public EquationSolver(double step, double epsilon)
    {
        this.step=step;
        this.epsilon=epsilon;
        initialList();
    }
    public EquationSolver()
    {
        this.step = 1e-3;
        this.epsilon = 1e-7;
        initialList();
    }
    private void initialList()
    {
        unknownParameterList = new HashMap<>();
        lawList = new ArrayList<>();
        result = new HashMap<>();
    }

    public void addEquation(ILawBase law)
    {
        ParameterList[] unknownList = law.getUnknownParameter();
        this.lawList.add(law);
        HashMap<ParameterList,Double> valueList = law.getValue();
        // extract the unknown parameter list
        for (ParameterList tmpParameter : unknownList) {
            if (this.unknownParameterList.get(tmpParameter) != null)
                this.unknownParameterList.put(tmpParameter, valueList.get(tmpParameter));
        }
    }

    public HashMap<ParameterList, Double> solveAll()
    {
        int solvable = checkSolvable();
        if(solvable>0)
        {
            System.out.println("The number of equations are more than the number of unknown parameters. That might cause conflict!");
        }
        else if(solvable<0)
        {
            System.out.println("The number of equations are less than the number of unknown parameters. That might cause an incorrect answer!");
        }
        //todo

        return result;
    }
    private int checkSolvable()
    {
        int num1 = lawList.size();
        int num2 = unknownParameterList.size();
        return num1-num2;
    }
}
