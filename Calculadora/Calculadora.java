import java.util.Scanner;
import java.util.Stack;

public class Calculadora {
    private static int precedence(char operator) {
        if (operator == '^') {
            return 3;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '+' || operator == '-') {
            return 1;
        } else {
            return 0;
        }
    }

    private static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            if (Character.isDigit(token)) {
                postfix.append(token);
            } else if (token == '(') {
                stack.push(token);
            } else if (token == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Descarta o '('
            } else {
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private static String infixToPrefix(String expression) {
        StringBuilder prefix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = expression.length() - 1; i >= 0; i--) {
            char token = expression.charAt(i);

            if (Character.isDigit(token)) {
                prefix.insert(0, token);
            } else if (token == ')') {
                stack.push(token);
            } else if (token == '(') {
                while (!stack.isEmpty() && stack.peek() != ')') {
                    prefix.insert(0, stack.pop());
                }
                stack.pop(); // Descarta o ')'
            } else {
                while (!stack.isEmpty() && precedence(token) < precedence(stack.peek())) {
                    prefix.insert(0, stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            prefix.insert(0, stack.pop());
        }

        return prefix.toString();
    }

    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char token = postfix.charAt(i);

            if (Character.isDigit(token)) {
                stack.push(Double.parseDouble(Character.toString(token)));
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();

                switch (token) {
                    case '+':
                        stack.push(operand1 + operand2);
                        break;
                    case '-':
                        stack.push(operand1 - operand2);
                        break;
                    case '*':
                        stack.push(operand1 * operand2);
                        break;
                    case '/':
                        stack.push(operand1 / operand2);
                        break;
                    case '^':
                        stack.push(Math.pow(operand1, operand2));
                        break;
                    default:
                        break;
                }
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a equação em notação infixada: ");
        String infix = scanner.nextLine();

        String postfix = infixToPostfix(infix);
        String prefix = infixToPrefix(infix);
        double result = evaluatePostfix(postfix);

        System.out.println("Equação em notação infixada: " + infix);
        System.out.println("Equação em notação pós-fixa: " + postfix);
        System.out.println("Equação em notação pré-fixa: " + prefix);
        System.out.println("Resultado da equação: " + result);
    }
}
