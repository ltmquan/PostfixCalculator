/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 1
 * EXTRA CREDIT: I added ^, %, sin, cos, tan (how you enter sin, cos, tan would be, for
 * 				example, sin(30) or sin 30.
 * EXTRA CREDIT: I also added try..catches and ifs (on lines 219, 247, 255, 291, 303, 325, 354, 422)
 * 				to handle invalid expressions 
 */
import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Calculator {
	
	//create a stack and queue containing strings
	static URStack<String> stack = new URStack<String>();
	static URQueue<String> queue = new URQueue<String>();
	
	//Decimal format to take 2 decimal numbers
	static DecimalFormat df = new DecimalFormat("#0.00");
	
	//method to turn infix to postfix
	public static void In2Post(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			
			//test if contain "!", as the split with " " does not apply on "!"
			while (arr[i].contains("!")) {
				stack.push("!");
				
				//take substring to remove the ! 
				arr[i] = arr[i].substring(1);
			}
			
			//EXTRA CREDIT:
			//test if contain "sin", as the split with " " does not apply on "sin"
			while (arr[i].contains("sin")) {
				stack.push("sin");
				
				//take substring to remove the sin
				arr[i] = arr[i].substring(3);
			}
			
			//EXTRA CREDIT:
			//test if contain "cos", as the split with " " does not apply on "cos"
			while (arr[i].contains("cos")) {
				stack.push("cos");
				
				//take substring to remove the cos
				arr[i] = arr[i].substring(3);
			}
			
			//EXTRA CREDIT:
			//test if contain "tan", as the split with " " does not apply on "tan"
			while (arr[i].contains("tan")) {
				stack.push("tan");
				
				//take substring to remove the tan
				arr[i] = arr[i].substring(3);
			}
			
			//EXTRA CREDIT:
			//test if contain "(", as the split with " " does not apply on "("
			while (arr[i].contains("(")) {
				stack.push("(");
				
				//take substring to remove the (
				arr[i] = arr[i].substring(1);
			}
			
			//EXTRA CREDIT:
			//test if contain ")", as the split with " " does not apply on ")"
			if (arr[i].contains(")")) {
				
				//enqueue the number before the ")"
				queue.enqueue(arr[i].substring(0, arr[i].indexOf(")")));
				while (arr[i].contains(")")) {
					
					//get rid of )
					arr[i] = arr[i].substring(0, arr[i].length()-1);
					
					//enqueue until reach (
					while(!stack.isEmpty() && !stack.peek().equals("(")) {
						queue.enqueue(stack.pop());
					}
					
					//pop the remaining (
					stack.pop();
				}
				
				//no need to test anything else so..
				continue;
			}
			
			switch (arr[i]) {
			
			case "+":
			case "-":
				
				//all higher and equal precedence will be enqueue
				while (!stack.isEmpty() && (stack.peek().equals("*") 
				|| stack.peek().equals("/")
				|| stack.peek().equals("%")
				|| stack.peek().equals("^")
				|| stack.peek().equals("+")
				|| stack.peek().equals("-")
				|| stack.peek().equals("sin")
				|| stack.peek().equals("cos")
				|| stack.peek().equals("tan"))) {
					queue.enqueue(stack.pop());
				}
				
				//push operator
				stack.push(arr[i]);
				break;
				
			case "*":
			case "/":
			case "%":
				
				//all higher and equal precedence will be enqueue
				while (!stack.isEmpty() && (stack.peek().equals("*") 
				|| stack.peek().equals("/")
				|| stack.peek().equals("%")
				|| stack.peek().equals("^")
				|| stack.peek().equals("sin")
				|| stack.peek().equals("cos")
				|| stack.peek().equals("tan"))) {
					queue.enqueue(stack.pop());
				}
				
				//push operator
				stack.push(arr[i]);
				break;
				
			case "^":
				
				//all higher and equal precedence will be enqueue
				while (!stack.isEmpty() && (stack.peek().equals("^")
				|| stack.peek().equals("sin")
				|| stack.peek().equals("cos")
				|| stack.peek().equals("tan"))) {
					queue.enqueue(stack.pop());
				}
				
				//push operator
				stack.push(arr[i]);
				break;
				
			case "&":
			case "|":
				
				//all higher and equal precedence will be enqueue
				while (!stack.isEmpty() && (stack.peek().equals("!")
				|| stack.peek().equals("&") 
				|| stack.peek().equals("|"))) {
					queue.enqueue(stack.pop());
				}
				
				//push operator
				stack.push(arr[i]);
				break;
				
			case ">":
			case "<":
			case "=":
				
				//all higher and equal precedence will be enqueue
				while (!stack.isEmpty() && (stack.peek().equals("*") 
				|| stack.peek().equals("/")
				|| stack.peek().equals("%")
				|| stack.peek().equals("^")
				|| stack.peek().equals("+")
				|| stack.peek().equals("-")
				|| stack.peek().equals("sin")
				|| stack.peek().equals("cos")
				|| stack.peek().equals("tan"))) {
					queue.enqueue(stack.pop());
				}
				
				//push operator
				stack.push(arr[i]);
				break;
			
			default:
				
				//enqueue numbers
				queue.enqueue(arr[i]);
				break;
			}
		}
		
		//afterwards, enqueue elements left in the stack
		while(!stack.isEmpty()) {
			queue.enqueue(stack.pop());
			
		}
	}
	
	//method to calculate postfix
	public static String calculate() {
		
		//loop to test all elements in queue
		while (queue.peek() != null) {
			switch (queue.peek()) {
			
			
			//CASE 1: operators which require two numbers
			case "+":
			case "-":
			case "*":
			case "/":
			case "%":
			case "^":
			case ">":
			case "<":
			case "=":
			case "&":
			case "|":
				
				//pop two numbers to operate
				double left, right;
				
				//EXTRA CREDIT: invalid when stack.pop() cannot convert to double (NumberFormatException)
				try {
					
					//EXTRA CREDIT: invalid if no element to pop
					if (stack.peek() == null) {
						return "Invalid expression: syntax error";
					}
					right = Double.valueOf(stack.pop());
					
					//EXTRA CREDIT: invalid if no element to pop
					if (stack.peek() == null) {
						return "Invalid expression: syntax error";
					}
					left = Double.valueOf(stack.pop());
				} catch (NumberFormatException e){
					return "Invalid expression: syntax error";
				}
				double result = 0;
				switch (queue.peek()) {
				 
				case "+":
					result = left + right;
					break;
					
				case "-":
					result = left - right;
					break;
					
				case "*":
					result = left * right;
					break;
					
				case "/":
					
					//EXTRA CREDIT: invalid if divide by 0
					if (right == 0) {
						return "Invalid expression: can't divide by zero";
					}
					result = left / right;
					break;
					
				//EXTRA CREDIT: modulo
				case "%":
					
					//EXTRA CREDIT: invalid if divide by 0 (modulo the same)
					if (right == 0) {
						return "Invalid expression: can't mod by zero";
					}
					result = left % right;
					break;
					
				//EXTRA CREDIT: exponentiation
				case "^":
					result = Math.pow(left, right);
					break;
					
				case ">":
					if (left > right) {
						result = 1; 
					} else {
						result = 0;
					}
					break;
					
				case "<":
					if (left < right) {
						result = 1; 
					} else {
						result = 0;
					}
					break;
					
				case "=":
					if (left == right) {
						result = 1; 
					} else {
						result = 0;
					}
					break;
					
				case "&":
					
					//EXTRA CREDIT: invalid if not used for logical expressions
					if ((left != 0 && left != 1) 
						|| (right != 0 && right != 1)){
							return "Invalid expression: | can only be used for logical expressions";
						}
					if (left == 1 && right == 1) {
						result = 1;
					} else {
						result = 0;
					}
					break;
					
				case "|":
					
					//EXTRA CREDIT: invalid if not used for logical expressions
					if ((left != 0 && left != 1) 
						|| (right != 0 && right != 1)){
						return "Invalid expression: | can only be used for logical expressions";
					}
					if (left == 1 || right == 1) {
						result = 1;
					} else {
						result = 0;
					}
					break;
				}
				
				//push result back to stack
				stack.push(String.valueOf(df.format(result)));
				
				//dequeue the operator
				queue.dequeue();
				break;
				
			//CASE 2: !
			case "!":
				double element;
				//EXTRA CREDIT: invalid when stack.pop() cannot convert to double (NumberFormatException)
				try {
					
					//EXTRA CREDIT: invalid if no element to pop
					if (stack.peek() == null) {
						return "Invalid expression: syntax error";
					}
					element = Double.valueOf(stack.pop());
				} catch (NumberFormatException e) {
					return "Invalid expression: syntax error";
				}
				
				//EXTRA CREDIT: invalid if not used for logical expressions
				if (element != 0 && element != 1) {
					return "Invalid expression: ! can only be used for logical expressions";
				}
				double neg = 0;
				if (element == 0) {
					neg = 1;
				}
				
				//push result back to stack
				stack.push(String.valueOf(df.format(neg)));
				
				//dequeue the operator
				queue.dequeue();
				break;
				
			//EXTRA CREDIT
			//CASE 3: trigonometry
			case "sin":
			case "cos":
			case "tan":
				double e;
				//EXTRA CREDIT: invalid when stack.pop() cannot convert to double (NumberFormatException)
				try {
					
					//EXTRA CREDIT: invalid if no element to pop
					if (stack.peek() == null) {
						return "Invalid expression: syntax error";
					}
					e = Double.valueOf(stack.pop());
				} catch (NumberFormatException ee) {
					return "Invalid expression: syntax error";
				}
				double trig = 0;
				switch (queue.peek()) {
				
				case "sin":
					trig = Math.sin(Math.toRadians(e));
					break;
					
				case "cos":
					trig = Math.cos(Math.toRadians(e));
					break;
					
				case "tan":
					trig = Math.tan(Math.toRadians(e));
					break;
					
				}
				
				//push the result back to stack
				stack.push(String.valueOf(df.format(trig)));
				
				//dequeue the operator
				queue.dequeue();
				break;
				
			default:
				
				//push numbers onto stack
				stack.push(queue.dequeue());
			}
		}
		
		String answer = stack.pop();
		
		//EXTRA CREDIT: if after pop, stack still have element, then must be syntax error
		if (!stack.isEmpty()) {
			return "Invalid expression: syntax error";
		}
		
		//pop the final result
		return answer;
	}
	
	public static void main(String[] args) {
		try {
			
			//Scan and read file
			Scanner s = new Scanner(new File(args[0]));
			
			//Create file writer
			FileWriter fw = new FileWriter(new File(args[1]));
			
			//while file has next line, read line, split into array of strings, 
			//then write to next file
			while (s.hasNextLine()) {
				String[] data = s.nextLine().split(" ");
				In2Post(data);
				fw.write(calculate() + "\n");
				while(!stack.isEmpty()) {
					stack.pop();
				}
				while(!queue.isEmpty()) {
					queue.dequeue();
				}
			}
			fw.close();
			
		//necessary exceptions catching
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}
}
