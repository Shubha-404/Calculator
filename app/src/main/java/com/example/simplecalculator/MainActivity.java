//      ॐ       //

//SHUBHAJIT RANA//
package com.example.simplecalculator;
import android.annotation.SuppressLint;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView results, Exp,solutionFromResults,TempResults;
    Button button_c, button_percent,button_backspace,button_division;
    Button button_7,button_8,button_9,button_multiply;
    Button button_4,button_5,button_6,button_minus;
    Button button_1,button_2,button_3,button_plus;
    Button button_00,button_0,button_dot,button_equal;
    Vibrator myVibrator;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FOR VIBRATOR SERVICE
        myVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //ASSIGNING ID
        results=findViewById(R.id.result);
        Exp =findViewById(R.id.exp);
        solutionFromResults=findViewById(R.id.solutionFromResult);

        //SETTING ON CLICK Listeners
        //SHUBHAJIT RANA
        assignId(button_c,R.id.button_c);
        assignId(button_percent,R.id.button_percent);
        assignId(button_dot,R.id.button_dot);
        assignId(button_division,R.id.button_divide);
        assignId(button_multiply,R.id.button_multiply);
        assignId(button_plus,R.id.button_plus);
        assignId(button_minus,R.id.button_minus);
        assignId(button_equal,R.id.button_equal);
        assignId(button_backspace,R.id.button_backspace);
        assignId(button_0,R.id.button_0);
        assignId(button_00,R.id.button_00);
        assignId(button_1,R.id.button_1);
        assignId(button_2,R.id.button_2);
        assignId(button_3,R.id.button_3);
        assignId(button_4,R.id.button_4);
        assignId(button_5,R.id.button_5);
        assignId(button_6,R.id.button_6);
        assignId(button_7,R.id.button_7);
        assignId(button_8,R.id.button_8);
        assignId(button_9,R.id.button_9);

    }
    void assignId(Button btn,int id) {
        btn = findViewById(id);
        btn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v){
        myVibrator.vibrate(100);
        Button clicked_btn=(Button) v;
        String btnText=clicked_btn.getText().toString();
        //exp.setText(btnText);
        String ans= "";
        String eqn= Exp.getText().toString();
        if(btnText.equals("C")){
            Exp.setText("");
            results.setText("");
            //solutionFromResults.setText("");
            return;
        }
        else if(btnText.equals("⌫")){
            if(!eqn.isEmpty()){
                eqn=eqn.substring(0,eqn.length()-1);
                Exp.setText(eqn);
            }
            return;
        }

        else if(btnText.equals("=")){
            ans=calculate(eqn);
            results.setText(ans);
            //exp.setText("");
            //solutionFromResults.setText(Double_To_String(ans));
            Exp.setText(ans);
            return;
        }

        eqn += btnText;
        Exp.setText(eqn);
        ans=calculate(eqn);
        results.setText(ans);

    }


//    public String calculate(String s) {
//        double ans = 0;
//        //proceed only if the input is valid
//        if(s != null && s.length() > 0){
//            Stack<Double> nums = new Stack<>();
//            char lastOperator = '+'; //start with +
//            double num = 0.0; //start with 0
//            String tempStr="";
//            for(int i = 0; i < s.length(); i++){
//                char c = s.charAt(i);
//                //find the number, if it is a multiple digit number
//                if(Character.isDigit(c) || c == '.') {
//                    tempStr+=c;
//                }
//
//                num = Double.parseDouble(tempStr);
//
//                //if the character is operator OR last character
//                if(isOperator(c) || i == s.length() - 1){
//                    if(lastOperator == '+'){
//                        nums.push(num);
//                    }
//                    else if(lastOperator == '-'){
//                        nums.push(-1 * num);
//                    }
//                    else if(lastOperator == '×'){
//                        nums.push(nums.pop() * num);
//                    }
//                    else if(lastOperator == '÷'){
//                        nums.push(nums.pop() / num);
//                    }
//                    //set the number back to zero
//                    num = 0.0;
//                    tempStr="";
//                    //set the current operation as the last operation
//                    lastOperator = c;
//                }
//            }
//            while(!nums.isEmpty()){
//                ans += nums.pop();
//            }
//        }
//        return Double_To_String(ans);
//    }
public String calculate(String s) {
    double ans = 0;

    try {
        //proceed only if the input is valid
        if(s != null && s.length() > 0){
            Stack<Double> nums = new Stack<>();
            char lastOperator = '+'; //start with +
            double num = 0.0; //start with 0
            String tempStr="";

            for(int i = 0; i < s.length(); i++){
                char c = s.charAt(i);

                //find the number, if it is a multiple digit number
                if(Character.isDigit(c) || c == '.') {
                    tempStr += c;
                }
                //num = Double.parseDouble(tempStr);
                try {
                    num = Double.parseDouble(tempStr);
                } catch (NumberFormatException e) {
                    // Handle invalid number format
                    // You can choose to throw an exception or handle it as appropriate
                    e.printStackTrace(); // Print the error for debugging
                    // Handle the error gracefully
                    return "Invalid Expression !";
                }

                //if the character is operator OR last character
                if(isOperator(c) || i == s.length() - 1){
                    if(c=='%'){
                        num=num/100;
                        lastOperator='+';
                    }
                    if(lastOperator == '+'){
                        nums.push(num);
                    }
                    else if(lastOperator == '-'){
                        nums.push(-1 * num);
                    }
                    else if(lastOperator == '×') {
                        nums.push(nums.pop() * num);
                    }

                    else if(lastOperator == '÷'){
                        if(num!=0){
                            nums.push(nums.pop() / num);
                        }
                        else{
                            return "Cannot be divided by 0";
                        }

                    }
                    //set the number back to zero
                    num = 0.0;
                    tempStr = "";
                    //set the current operation as the last operation
                    lastOperator = c;
                }
            }

            while(!nums.isEmpty()){
                ans += nums.pop();
            }
        }
    } catch (Exception e) {
        //e.printStackTrace(); // Print the error for debugging
        // Handle other exceptions as needed
        //return "Error: Unexpected error occurred";

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return stackTrace;
    }

    return Double.toString(ans);
}


    public boolean isOperator(char c){
        return (c == '+' || c == '-' || c == '×' || c == '÷');
    }
    public String Double_To_String(Double d){
        String doubleAsString = String.format("%.3f", d);
        if(doubleAsString.endsWith(".000")){
            doubleAsString=doubleAsString.replace(".000","");
        }
        return doubleAsString;
    }
}