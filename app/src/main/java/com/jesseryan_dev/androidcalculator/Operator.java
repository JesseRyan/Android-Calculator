package com.jesseryan_dev.androidcalculator;

/**
 * Created by Dread on 5/20/2016.
 */
public class Operator {


    private String left = "";
    private String right = "";
    private Integer weight = null;
    private String symbol = "";
    private Double result = null;

    Operator(){ }
    Operator(Integer w, String s) {
        weight = w;
        symbol = s;
    }

    /* required to execute operation */
    public void addLeft( String s){ left += s; }
    public void addRight( String s ){
        right += s;
    }

    public void addAttributes( Operator o) {
        weight = o.getWeight();
        symbol = o.getSymbol();
    }

    public double compute(){


        switch ( symbol ) {
            case "+":
                    result = Double.parseDouble(left) + Double.parseDouble(right);
                    return result;
            case "-":
                    result = Double.parseDouble(left) - Double.parseDouble(right);
                    return result;
            case "/":
                    result = Double.parseDouble(left) / Double.parseDouble(right);
                    return result;
            case "*":
                    result = Double.parseDouble(left) * Double.parseDouble(right);
                    return result;
            case "^":
                    result = Math.pow(Double.parseDouble(left),  Double.parseDouble(right) );
                    return result;
            /* need to throw exception here */
            default: return 0.00;
        }
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public String getLeft(){
        return left;
    }
    public String getRight(){ return right; }
    public String getSymbol(){ return symbol;  }
    public Double getResult(){ return result; }
    public Integer getWeight(){ return weight; }






}

