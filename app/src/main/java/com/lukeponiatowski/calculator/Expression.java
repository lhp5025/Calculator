package com.lukeponiatowski.calculator;

import java.util.ArrayList;

/*
    Expression -  class for parsing and evaluating expression strings
*/
public class Expression {
    // Constants for operators, with higher values being higher order
    public final int END_OF_EXPRESSION = 0;
    public final static int ADD = 10;
    public final static int SUBTRACT = 11;
    public final static int MODULUS = 12;
    public final static int MULTIPLY = 100;
    public final static int DIVIDE = 101;
    public final static int SIN = 102;
    public final static int COS = 103;
    public final static int TAN = 104;
    public final static int CSC = 105;
    public final static int SEC = 106;
    public final static int COT = 107;
    public final static int ASIN = 108;
    public final static int ACOS = 109;
    public final static int ATAN = 110;
    public final static int LN = 111;
    public final static int LOG = 112;
    public final static int SQRT = 113;
    public final static int EXPONENTIAL = 1000;
    public final static int FACTORIAL = 1100;

    // Class Data Members
    protected ArrayList<Expression> ExpressionList;
    protected int Operator; //Operator following this expression
    protected Number Value = null; //Value of this expression, null since expression may have sub-expressions

    //Public defalut constructor
    public Expression() {
        ExpressionList = new ArrayList();
        Operator = END_OF_EXPRESSION; //Default operatore is END_OF_EXPRESSION, because the expression has no parents
    }

    //Constructor for expression that is used as a sub-expression (hence private)
    private Expression(String exp, int operator) {
        ExpressionList = new ArrayList();
        parseString(exp);
        Operator = operator;
    }

    // Parses a formatted input string, into computable sub expressions
    public boolean parseString(String input) {
        boolean toReturn = true;
        try {
            input = input.replace("e", Math.E + "");
            input = input.replace("\u03C0", Math.PI + "");
            input = input.replace("E", "*10^");
            // Read in till on operator is found
            String numberBuffer = "";
            String functionBuffer = "";
            for (int i = 0; i < input.length(); i++) {
                // If the start us of nested sub-expressions
                if (input.charAt(i) == '('){
                    // Start new sub-expression, build string
                    int parenthCounter = 1;
                    int end = i + 1;
                    String subExpressionBuffer = "";
                    for (int j = end; j < input.length() && parenthCounter > 0; j++) {
                        if (input.charAt(j) == '('){
                            parenthCounter++;
                        } else if (input.charAt(j) == ')'){
                            parenthCounter--;
                        }
                        if (parenthCounter > 0) {
                            subExpressionBuffer += input.charAt(j);
                        }
                        end = j;
                    }
                    i = end + 1;

                    int operator = 0;
                    if (end+1 < input.length()){
                        operator = determineOperator(input.charAt(end+1));
                    } else if (operator == 0){
                        operator = MULTIPLY;
                    }

                    if (functionBuffer.length() != 0) {
                        ExpressionList.add(new FunctionValue(determineFunction(functionBuffer) ,subExpressionBuffer, operator ) );
                    } else {
                        ExpressionList.add(new Expression(subExpressionBuffer, operator ) );
                    }

                } else if ( determineOperator(input.charAt(i)) > 0 ) {
                    // If there is an operator
                    if (numberBuffer.length() == 0 && determineOperator(input.charAt(i)) == SUBTRACT ){
                        // Number is deemed negative, add negative sign to number
                        numberBuffer += input.charAt(i);
                    } else if (numberBuffer.length() == 0 && determineOperator(input.charAt(i)) == ADD ){
                        // Number is deemed positive, ignore this operator
                    } else {
                        ExpressionList.add(new ValueOperand( Double.parseDouble(numberBuffer), determineOperator(input.charAt(i))) );
                        numberBuffer = "";
                    }
                } else if (isCharNumber(input.charAt(i))) {
                    // Add rest to buffer
                    numberBuffer += input.charAt(i);
                } else {
                    functionBuffer += input.charAt(i);
                }
            }
            if (numberBuffer.length() != 0){
                ExpressionList.add(new ValueOperand( Double.parseDouble(numberBuffer), END_OF_EXPRESSION) );
            }

        } catch (Exception e) {
            //Log.e("Calculator",e.getStackTrace(). );
            e.printStackTrace();
            toReturn = false;
        }

        return toReturn;
    }

    // Returns the values of this expression
    public Number getValue() {
        Number toReturn = 0;
        if (ExpressionList.size() > 0){
            toReturn = ExpressionList.get(0).getValue();
            //If there are sub-expressions
            if (Value == null){
                // While the expression has sub expression to be computed
                while (ExpressionList.size() > 1) {
                    // Find operator with the highest value
                    int comuteIndex = 0;
                    int comuteIndexOpValue = 0;
                    for (int i = 0; i < ExpressionList.size(); i++) {
                        if (ExpressionList.get(i).getOperator() > comuteIndexOpValue){
                            comuteIndexOpValue= ExpressionList.get(i).getOperator();
                            comuteIndex = i;
                        }
                    }
                    //Amend the sub-expressions
                    ExpressionList.get(comuteIndex + 1).setValue(computeValues(ExpressionList.get(comuteIndex).getValue(), ExpressionList.get(comuteIndex + 1).getValue(), ExpressionList.get(comuteIndex).getOperator() ));
                    ExpressionList.remove(comuteIndex); //Remove current expression

                }
                toReturn = ExpressionList.get(0).getValue(); //Return the leading, and only sub-expression value
            } else {
                //If ther is a value, it overrides sub-exprssion
                toReturn = Value;
            }
        }
        return toReturn;
    }

    // Returns the operator following this expression
    protected int getOperator() {
        return Operator;
    }

    // Sets the value of the expression
    protected void setValue(Number n) {
        Value = n;
    }

    // Determines if a char is a number, or part of
    private boolean isCharNumber(char c){
        boolean toReturn = false;

        // 0 to 9 or .
        if (( (int)c >= 48  && (int)c <=57) || c == '.') {
            toReturn = true;
        }

        return toReturn;
    }

    // Computes the value of a sub expression
    private int determineOperator(char c) {
        int toReturn = 0;

        switch (c) {
            case '+':
                toReturn = ADD;
                break;
            case '-':
                toReturn = SUBTRACT;
                break;
            case '*':
                toReturn = MULTIPLY;
                break;
            case '/':
                toReturn = DIVIDE;
                break;
            case '^':
                toReturn = EXPONENTIAL;
                break;

        }

        return  toReturn;
    }

    // Computes the value of a sub-function
    private int determineFunction(String s) {
        int toReturn = 0;

        switch (s) {
            case "sin":
                toReturn = SIN;
                break;
            case "cos":
                toReturn = COS;
                break;
            case "tan":
                toReturn = TAN;
                break;
            case "asin":
                toReturn = ASIN;
                break;
            case "acos":
                toReturn = ACOS;
                break;
            case "atan":
                toReturn = ATAN;
                break;
            case "csc":
                toReturn = CSC;
                break;
            case "sec":
                toReturn = SEC;
                break;
            case "cot":
                toReturn = COT;
                break;
            case "ln":
                toReturn = LN;
                break;
            case "log":
                toReturn = LOG;
                break;
            case "\u221A":
                toReturn = SQRT;
                break;
        }

        return  toReturn;
    }

    // Computes the value of a sub expression
    private Number computeValues(Number value1, Number value2, int operator) {
        Number toReturn = 0;
        switch (operator) {
            case END_OF_EXPRESSION:
                toReturn = value1;
                break;
            case ADD:
                toReturn = value1.doubleValue() + value2.doubleValue() ;
                break;
            case SUBTRACT:
                toReturn =  value1.doubleValue() -  value2.doubleValue();
                break;
            case MULTIPLY:
                toReturn =  value1.doubleValue() * value2.doubleValue();
                break;
            case DIVIDE:
                toReturn =  value1.doubleValue() / value2.doubleValue();
                break;
            case EXPONENTIAL:
                toReturn = Math.pow( value1.doubleValue(),  value2.doubleValue());
                break;

        }

        return  toReturn;
    }

    // Inner class for value-operator pairs
    private class ValueOperand extends Expression {
        public ValueOperand(Number value, int operator) {
            this.ExpressionList = null;
            this.Value = value;
            this.Operator = operator;
        }

        @Override
        public Number getValue() {
            return Value;
        }

        @Override
        public void setValue(Number n) {
            this.Value = n;
        }
    }

    // Inner class for function sub-expressions
    private class FunctionValue extends  Expression {
        private final int Funct;
        public FunctionValue(int funct, String innerExpression, int operator) {
            this.Operator = operator;
            this.Funct = funct;
            this.ExpressionList = new ArrayList<>();
            parseString(innerExpression);
        }

        @Override
        public Number getValue() {
            Number toReturn = super.getValue();
            switch (Funct){
                case SIN:
                    toReturn = Math.sin(toReturn.doubleValue());
                    break;
                case COS:
                    toReturn = Math.cos( toReturn.doubleValue());
                    break;
                case TAN:
                    toReturn = Math.tan(toReturn.doubleValue());
                    break;
                case CSC:
                    toReturn = 1 / Math.sin(toReturn.doubleValue());
                    break;
                case SEC:
                    toReturn = 1 / Math.cos(toReturn.doubleValue());
                    break;
                case COT:
                    toReturn = 1 / Math.tan(toReturn.doubleValue());
                    break;
                case ASIN:
                    toReturn = Math.asin(toReturn.doubleValue());
                    break;
                case ACOS:
                    toReturn = Math.acos(toReturn.doubleValue());
                    break;
                case ATAN:
                    toReturn = Math.atan(toReturn.doubleValue());
                    break;
                case LN:
                    toReturn = Math.atan(toReturn.doubleValue());
                    break;
                case LOG:
                    toReturn = Math.log(toReturn.doubleValue());
                    break;
                case SQRT:
                    toReturn = Math.sqrt(toReturn.doubleValue());
                    break;
            }
            return toReturn;
        }

    }

}
