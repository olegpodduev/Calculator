import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Введите выражение, 'quit' чтобы закончить:");
            String type = s.nextLine();
            if (checkExpression(type)||checkSigns(type)) {
                System.out.println("Ошибка. Некорректное выражение");
            } else if (type.trim().equals("quit")) {
                System.exit(0);
            } else {
                System.out.println(calculate(type));
            }
        }
    }
    public static boolean checkExpression(String expression) {
        char[] arr = expression.toCharArray();
        int open = 0;
        int close = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '(') {
                open++;
            }
            if (arr[i] == ')') {
                close++;
            }
        }
        if (open == close) {
            return false;
        }
        return true;
    }
    public static boolean checkSigns(String signs) {
        char[] array = signs.toCharArray();
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == '-' && array[i + 1] == '-') {
                return true;
            } else if (array[i] == '+' && array[i + 1] == '+') {
                return true;
            } else if (array[i] == '*' && array[i + 1] == '*') {
                return true;
            } else if (array[i] == ':' && array[i + 1] == ':') {
                return true;
            }
        }
        return false;
    }
        public String eval0 (String el1S, String op, String el2S){ // Дробь, оператор и дробь
            Frac f1 = null;
            Frac f2 = null;
            boolean isF1 = Frac.isFrac(el1S);
            boolean isF2 = Frac.isFrac(el2S);
            if (isF1 || isF2) {
                f1 = new Frac(el1S); // получаем объект дроби
                f2 = new Frac(el2S);
            }

            if (f1 == null && f2 == null) {
                int el1 = Integer.valueOf(el1S);
                int el2 = Integer.valueOf(el2S);
                if (op.equals("+")) {
                    return String.valueOf(el1 + el2);
                } else if (op.equals("-")) {
                    return String.valueOf(el1 - el2);
                } else if (op.equals("*")) {
                    return String.valueOf(el1 * el2);
                } else {
                    return (new Frac((int) el1, (int) el2)).toString();// преобразовываем назад в дробь
                }
            } else {
                if (op.equals("+")) {
                    return f1.add(f2).toString();
                } else if (op.equals("-")) {
                    return f1.sub(f2).toString();
                } else if (op.equals("*")) {
                    return f1.mult(f2).toString();
                } else {
                    return f1.div(f2).toString();
                }
            }
        }
        public String eval1 (String exp){

            List<String> res = new ArrayList<>();

            String num1 = "-?[0-9]+\\.?[0-9]*"; // Регулярные выражения
            String frac1 = "-?[0-9]+[|]-?[0-9]+";
            String num = "(" + frac1 + "|" + num1 + ")";
            String[] op = {"([*/])", "([+-])"};
            String space = "[ ]*";
            String open = "\\(";
            String close = "\\)";

            exp = exp.replaceAll("--", "");

            exp = exp.replaceAll("\\((" + num + ")\\)", "$1"); // Берет внутренние скобки

            for (int o = 0; o < op.length; o++) {

                String comp2 = num + space + op[o] + space + num;

                Pattern p = Pattern.compile(comp2);
                Matcher m = p.matcher(exp);
                if (m.find()) {
                    for (int i = 1; i <= m.groupCount(); i++) {
                        res.add(m.group(i));
                    }
                    String evalExp = String.valueOf(eval0(res.get(0), res.get(1), res.get(2))); // вызов подсчета дроби если дробь корректна
                    return exp.replaceFirst(comp2, evalExp);
                }
            }
            if (Frac.isFrac(exp)) exp = new Frac(exp).toString();
            return exp;

        }
        public String eval (String exp){
            String newExp = eval1(exp);
            while (!newExp.equals(exp)) {
                exp = newExp;
                newExp = eval1(exp);
            }
            return newExp;
        }
        public static String calculate (String exp) throws ArithmeticException {
            return new Fraction().eval(exp);
        }
    }
