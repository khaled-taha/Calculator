
package application;

import java.util.Stack;

public class Calculation {
    
    public Calculation(){
        
    }
   
    public static double evaluate(String exString){
        exString = infix_To_Postfix(exString);
        Stack <Double> stc = new Stack <>();
        double result;
        double number = 0;
        int numDecimal = -1;
        int i = 0;
        
        while(i < exString.length()){
            
            if((exString.charAt(i) >= 48 && exString.charAt(i) <= 57)){
                
                while((exString.charAt(i) >= 48 && exString.charAt(i) <= 57) || exString.charAt(i) == '.'){
                    
                    if(exString.charAt(i) == '.') numDecimal = 0;
                    else {
                    number = (number * 10) + (double)(exString.charAt(i) - '0');
                    if(numDecimal > -1) numDecimal++;
                    }
                    i++;
                }
              
                if(numDecimal == -1) numDecimal = 0;
                int ten = 1;
                for(int j = 1; j <= numDecimal; j++) ten *= 10;
                number /= ten;
                
                stc.push(number);
                numDecimal = -1;
                number = 0;
            }
            
            
            else if (exString.charAt(i) != 32){

                double operand1 = stc.peek();

                stc.pop();
                double operand2 = stc.peek();

                stc.pop();
                
                result = mathOperator(operand2, operand1, exString.charAt(i));

                stc.push(result);
            }
            i++;
        }
        
        return stc.peek();
    }
    
    private static double mathOperator(double operand1, double operand2, char operator){
        switch(operator){
            case '+' -> {
                return operand1 + operand2;
            }
            case '-' -> {
                return operand1 - operand2;
            }
            case '*' -> {
                return operand1 * operand2;
            }
            case '/' -> {
                return operand1 / operand2;
            }
        }
        return 0;
    }
    
    
    private static String infix_To_Postfix(String expression){
        Stack <Character> stc = new Stack <>();
        String output = "";
        int i = 0;
        
        while(i < expression.length()){
            
            if((expression.charAt(i) >= 48 && expression.charAt(i) <= 57) || expression.charAt(i) == 32
                    || expression.charAt(i) == '.'){
                      output += expression.charAt(i);
            }
            
            else if(expression.charAt(i) == '(') stc.push('(');
            else if(expression.charAt(i) == ')'){
                while(stc.peek() != '('){
                    output += " " + stc.peek();
                    stc.pop();
                }
                stc.pop();
            }
            
            else{
                
            while(!stc.empty() && precedent_process(expression.charAt(i)) <= precedent_process(stc.peek())){
                output += " " + stc.peek();
                stc.pop();
            }
            
            stc.push(expression.charAt(i));
            }
            i++;
        }
        
        
        while(!stc.empty()) {
            
            output += " " + stc.peek();
            stc.pop();
        }
        System.out.println(output);
        return output;
    }
    
    private static int precedent_process(char c){
        if(c == '+' || c == '-') return 1;
        else if(c == '/' || c == '*') return 2;
        return 0;
    }
    
    public static void main(String[] args){
        System.out.println(evaluate("2 + 9 - 4 * 8"));
    }
}
