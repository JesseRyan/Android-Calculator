package com.jesseryan_dev.androidcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int[] numerics = {R.id.i0, R.id.i1, R.id.i2, R.id.i3, R.id.i4, R.id.i5, R.id.i6, R.id.i7, R.id.i8, R.id.i9, R.id.iDecimal };
    final int[] operators = {R.id.oPlus, R.id.oMinus, R.id.oTimes, R.id.oDivide, R.id.execute3, R.id.fClear, R.id.fDelete};
    final String DEFAULT_VALUE = "";

    TextView statementView;
    String statementViewString = "";

    ArrayList<String> statementList;
    ArrayAdapter<String> statementListAdapter;
    ListView statementListView;

    List<Object>currentStatement;

    protected String executeStatement(){
        Operator currentOperator = new Operator();

        for( int i = 0, len = currentStatement.size(); i < len; i++ ){
            Object position = currentStatement.get(i);
            if ( position.getClass() == Operator.class && currentOperator.getSymbol() == "" ){
                Operator temp = (Operator)position;
                currentOperator.addAttributes(temp);
            }else if ( currentOperator.getSymbol() == "" ){
               currentOperator.addLeft( position.toString() );
            }else{
                currentOperator.addRight( position.toString() );
            }
        }

        Double computed = (double)currentOperator.compute();
        String computedString = String.format("%.2f", computed);
        currentStatement.clear();
        for ( int i = 0, len = computedString.length(); i < len; i++ ){
            currentStatement.add(computedString.charAt(i));
        }

        statementList.add(statementViewString + " = " + computedString);
        statementListView.setSelection(statementListAdapter.getCount() - 1);
       // statementListAdapater.add(statementViewString + " = " + computedString );

        return computed.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize numeric buttons */
        for (int i = 0, len = numerics.length; i < len; i++){
            Button buttonNum = (Button)findViewById(numerics[i]);
            buttonNum.setOnClickListener( this );
        }

          /* initialize operator buttons */
        for (int i = 0, len = operators.length; i < len; i++){
            Button buttonNum = (Button)findViewById(operators[i]);
            buttonNum.setOnClickListener( this );
        }


        /* initialize the previous statement list view */
        statementListView = (ListView)findViewById(R.id.listView);
        statementView = (TextView) findViewById(R.id.primaryState);
        statementList = new ArrayList<String>();

        statementListAdapter = new ArrayAdapter<String>(this,
                R.layout.custom_list_item,
                statementList);

        statementListView.setAdapter(statementListAdapter);

        currentStatement = new ArrayList<Object>();
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        String clickedView = Integer.toString(viewId);

        switch (view.getId()) {
            case R.id.i0:
                currentStatement.add("0");
                statementViewString += "0";
                break;
            case R.id.i1:
                currentStatement.add("1");
                statementViewString += "1";
                break;
            case R.id.i2:
                currentStatement.add("2");
                statementViewString += "2";
                break;
            case R.id.i3:
                currentStatement.add("3");
                statementViewString += "3";
                break;
            case R.id.i4:
                currentStatement.add("4");
                statementViewString += "4";
                break;
            case R.id.i5:
                currentStatement.add("5");
                statementViewString += "5";
                break;
            case R.id.i6:
                currentStatement.add("6");
                statementViewString += "6";
                break;
            case R.id.i7:
                currentStatement.add("7");
                statementViewString += "7";
                break;
            case R.id.i8:
                currentStatement.add("8");
                statementViewString += "8";
                break;
            case R.id.i9:
                currentStatement.add("9");
                statementViewString += "9";
                break;

            case R.id.iDecimal:
                if( ! statementViewString.contains(".") ) {
                    currentStatement.add(".");
                    statementViewString += ".";
                }
                break;


            /* operators */

            case R.id.oPlus:
                currentStatement.add( new Operator(1, "+"));
                statementViewString += "+";
                break;

            case R.id.oMinus:
                currentStatement.add(new Operator(1, "-"));
                statementViewString += "-";
                break;

            case R.id.oDivide:
                currentStatement.add(new Operator(2, "/"));
                statementViewString += "/";
                break;

            case R.id.oTimes:
                currentStatement.add(new Operator(2, "*"));
                statementViewString += "*";
                break;

            case R.id.fClear:
                currentStatement.clear();
                statementViewString = DEFAULT_VALUE;
                break;

            case R.id.fDelete:
                if ( currentStatement.size() > 1 ) {
                    currentStatement.remove(currentStatement.size() - 1);
                    String ss = statementViewString.substring(0, statementViewString.length() - 1);
                    statementViewString = ss;
                }
                break;
            /* execute */
            case R.id.execute3:
                statementViewString = executeStatement();
                break;

        }


        statementView.setText(statementViewString);

    }

    public void parseStatementViewOperators() {

        currentStatement.clear();
        if ( statementViewString != "" && Double.parseDouble(statementViewString) != 0 ){
            for( int i = 0; i < statementViewString.length(); i++){
                switch (statementViewString.charAt(i)) {
                /* operators */

                    case '+':
                        currentStatement.add(new Operator(1, "+"));
                        break;

                    case '-':
                        currentStatement.add(new Operator(1, "-"));
                        break;

                    case '/':
                        currentStatement.add(new Operator(2, "/"));
                        break;

                    case '*':
                        currentStatement.add(new Operator(2, "*"));
                        break;

                    default:
                        currentStatement.add(statementViewString.charAt(i));
                        break;

                }
            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        statementViewString = sharedPref.getString("STATEMENT_VIEW_STRING", DEFAULT_VALUE);
        statementView.setText(statementViewString);

        Set<String> set = sharedPref.getStringSet("STATEMENT_VIEW_LIST", new HashSet<String>() );

        for (String s : set) {
            statementList.add( s );
        }

        /* add operators where needed */
        parseStatementViewOperators();
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("STATEMENT_VIEW_STRING", statementViewString);

        Set<String> set = new HashSet<String>();
        set.addAll(statementList);
        editor.putStringSet("STATEMENT_VIEW_LIST", set);

        editor.commit();

    }



}
