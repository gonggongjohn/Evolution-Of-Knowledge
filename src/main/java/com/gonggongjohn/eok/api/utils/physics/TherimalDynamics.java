package com.gonggongjohn.eok.api.utils.physics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TherimalDynamics {
    public static final double NA = 6.023e23; //
    public static final double kB = 1.38e-23;
    public static final double R = 8.314;


    public static class IdealGasLaw implements ILawBase
    {
        public static final String LAW_STRING = "PV=nRT";
        private final ParameterList[] unknownParameter;
        private double P,V,n,T;
        private final HashMap<ParameterList,Double> parameterValue;
        private final ParameterList[] PARAMETER_INCLUDE ={ParameterList.Pressure,ParameterList.Volume,ParameterList.AmountOfMatter,ParameterList.Temperature};
        public IdealGasLaw(double P, double V, double n, double T, ParameterList[] unknownParameter)
        {
            this.P=P;
            this.V=V;
            this.n=n;
            this.T=T;
            parameterValue = new HashMap<>();
            parameterValue.put(ParameterList.Pressure,this.P);
            parameterValue.put(ParameterList.Volume,this.V);
            parameterValue.put(ParameterList.AmountOfMatter,this.n);
            parameterValue.put(ParameterList.Temperature,this.T);
            this.unknownParameter = unknownParameter;
        }
        @Override
        public String getLaw()
        {
            return LAW_STRING;
        }

        @Override
        public double solve(ParameterList unknownParameter) {
            double result = 0;
            switch(unknownParameter)
            {
                case Volume:
                    result = n*R*T/P;
                    break;
                case Pressure:
                    result = n*R*T/V;
                    break;
                case AmountOfMatter:
                    result = (P*V)/(R*T);
                    break;
                case Temperature:
                    result = (P*V)/(n*R);
                    break;
                default:
                    break;
            }
            return result;
        }

        @Override
        public void addToEquationSolver(EquationSolver solver, ILawBase counter) {
            solver.addEquation(counter);
        }

        @Override
        public ParameterList[] getUnknownParameter() {
            return this.unknownParameter;
        }

        @Override
        public HashMap<ParameterList, Double> getValue() {
            return this.parameterValue;
        }
    }

}
