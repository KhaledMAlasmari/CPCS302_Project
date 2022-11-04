/*
 Khaled Alasmari - 2035189
 Omar abdulrahman abdulbagi - 2037070
 Salman alhodaly - 2044556
 */

package cpcs302_project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class LR0Parser {

    private Stack<String> stack;
    private HashMap<Integer, String[]> grammar;
    private HashMap<String, Integer> goToTable;
    private HashMap<String, Action> actionTable;
    private Scanner input;

    public LR0Parser(String path) throws FileNotFoundException {
        input = new Scanner(new File(path));
        initTables();
    }

    private void parseLine(String[] tokens) {
        int length = tokens.length;
        for (int i = 0; i < length;) {
            String currentInput = tokens[i];
            String stackValue = stack.peek();
            Action action = actionTable.get(stackValue + currentInput);
            if (action == null) {
                System.err.println("Syntax Error");
                System.exit(1);
            } else if (action.isShift()) {
                stack.push(currentInput);
                stack.push(Integer.toString(action.getOperand()));
                System.out.println(action);
                i++;
            } else if (action.isReduce()) {
                String[] rule = grammar.get(action.getOperand());
                for (int j = 0; j < (rule.length - 2) * 2; j++) {
                    stack.pop();
                }
                String lastValueAfterPop = stack.peek();
                int goToNumber = goToTable.get(lastValueAfterPop + rule[0]);
                stack.push(rule[0]);
                stack.push(Integer.toString(goToNumber));
                System.out.printf("%s by %s \n", action.getType(), Arrays.toString(rule).replace(",", "").replace("[", "").replace("]", ""));
            } else if (action.isAccept()) {
                System.out.println("Accept");
                return;
            } else {
                System.out.println("Syntax Error");
                System.exit(1);
            }
        }
    }

    public void startParsing() {
        while (input.hasNext()) {
            String currentInput = input.nextLine();
            String[] tokens = currentInput.split(" ");
            System.out.println("");
            System.out.printf("Right most derivation for the arithmetic expression %s:\n", currentInput.replace("$", ""));
            parseLine(tokens);
            System.out.println("");
            initTables();
        }
    }

    private void initTables() {
        grammar = new HashMap<>();
        stack = new Stack<>();
        stack.push("0");
        actionTable = new HashMap<>();
        goToTable = new HashMap<>();
        initActionTable();
        initGoToTable();
        initGrammar();
    }

    private void initGrammar() {
        grammar.put(0, new String[]{""});
        grammar.put(1, new String[]{"E", "--->", "E", "+", "T"});
        grammar.put(2, new String[]{"E", "--->", "T"});
        grammar.put(3, new String[]{"T", "--->", "T", "*", "F"});
        grammar.put(4, new String[]{"T", "--->", "F"});
        grammar.put(5, new String[]{"F", "--->", "(", "E", ")"});
        grammar.put(6, new String[]{"F", "--->", "id"});

    }

    private void initActionTable() {
        // 0
        actionTable.put("0id", new Action("Shift", 5));
        actionTable.put("0+", new Action("error", -1));
        actionTable.put("0*", new Action("error", -1));
        actionTable.put("0(", new Action("Shift", 4));
        actionTable.put("0)", new Action("error", -1));
        actionTable.put("0$", new Action("error", -1));
        // 1
        actionTable.put("1id", new Action("error", -1));
        actionTable.put("1+", new Action("Shift", 6));
        actionTable.put("1*", new Action("error", -1));
        actionTable.put("1(", new Action("error", -1));
        actionTable.put("1)", new Action("error", -1));
        actionTable.put("1$", new Action("Accept", -1));
        // 2
        actionTable.put("2id", new Action("error", -1));
        actionTable.put("2+", new Action("Reduce", 2));
        actionTable.put("2*", new Action("Shift", 7));
        actionTable.put("2(", new Action("error", -1));
        actionTable.put("2)", new Action("Reduce", 2));
        actionTable.put("2$", new Action("Reduce", 2));
        // 3
        actionTable.put("3id", new Action("error", -1));
        actionTable.put("3+", new Action("Reduce", 4));
        actionTable.put("3*", new Action("Reduce", 4));
        actionTable.put("3(", new Action("error", -1));
        actionTable.put("3)", new Action("Reduce", 4));
        actionTable.put("3$", new Action("Reduce", 4));
        // 4
        actionTable.put("4id", new Action("Shift", 5));
        actionTable.put("4+", new Action("error", -1));
        actionTable.put("4*", new Action("error", -1));
        actionTable.put("4(", new Action("Shift", 4));
        actionTable.put("4)", new Action("error", -1));
        actionTable.put("4$", new Action("error", -1));
        // khaled's stuff
        // 5
        actionTable.put("5id", new Action("error", -1));
        actionTable.put("5+", new Action("Reduce", 6));
        actionTable.put("5*", new Action("Reduce", 6));
        actionTable.put("5(", new Action("error", -1));
        actionTable.put("5)", new Action("Reduce", 6));
        actionTable.put("5$", new Action("Reduce", 6));
        // 6
        actionTable.put("6id", new Action("Shift", 5));
        actionTable.put("6+", new Action("error", -1));
        actionTable.put("6*", new Action("error", -1));
        actionTable.put("6(", new Action("Shift", 4));
        actionTable.put("6)", new Action("error", -1));
        actionTable.put("6$", new Action("error", -1));
        // 7
        actionTable.put("7id", new Action("Shift", 5));
        actionTable.put("7+", new Action("error", -1));
        actionTable.put("7*", new Action("error", -1));
        actionTable.put("7(", new Action("Shift", 4));
        actionTable.put("7)", new Action("error", -1));
        actionTable.put("7$", new Action("error", -1));
        // 8
        actionTable.put("8id", new Action("error", -1));
        actionTable.put("8+", new Action("Shift", 6));
        actionTable.put("8*", new Action("error", -1));
        actionTable.put("8(", new Action("error", -1));
        actionTable.put("8)", new Action("Shift", 11));
        actionTable.put("8$", new Action("error", -1));
        // 9
        actionTable.put("9id", new Action("error", -1));
        actionTable.put("9+", new Action("Reduce", 1));
        actionTable.put("9*", new Action("Shift", 7));
        actionTable.put("9(", new Action("error", -1));
        actionTable.put("9)", new Action("Reduce", 1));
        actionTable.put("9$", new Action("Reduce", 1));
        // 10
        actionTable.put("10id", new Action("error", -1));
        actionTable.put("10+", new Action("Reduce", 3));
        actionTable.put("10*", new Action("Reduce", 3));
        actionTable.put("10(", new Action("error", -1));
        actionTable.put("10)", new Action("Reduce", 3));
        actionTable.put("10$", new Action("Reduce", 3));
        // 11
        actionTable.put("11id", new Action("error", -1));
        actionTable.put("11*", new Action("Reduce", 5));
        actionTable.put("11(", new Action("error", -1));
        actionTable.put("11)", new Action("Reduce", 5));
        actionTable.put("11$", new Action("Reduce", 5));
    }

    private void initGoToTable() {
        // 0
        goToTable.put("0E", 1);
        goToTable.put("0T", 2);
        goToTable.put("0F", 3);
        // 1
        goToTable.put("1E", -1);
        goToTable.put("1T", -1);
        goToTable.put("1F", -1);

        // 2
        goToTable.put("2E", -1);
        goToTable.put("2T", -1);
        goToTable.put("2F", -1);
        // 3
        goToTable.put("3E", -1);
        goToTable.put("3T", -1);
        goToTable.put("3F", -1);
        // 4
        goToTable.put("4E", 8);
        goToTable.put("4T", 2);
        goToTable.put("4F", 3);
        // 5
        goToTable.put("5E", -1);
        goToTable.put("5T", -1);
        goToTable.put("5F", -1);

        // khaled's part
        // 6
        goToTable.put("6E", -1);
        goToTable.put("6T", 9);
        goToTable.put("6F", 3);
        // 7
        goToTable.put("7E", -1);
        goToTable.put("7T", -1);
        goToTable.put("7F", 10);
        // 8
        goToTable.put("8E", -1);
        goToTable.put("8T", -1);
        goToTable.put("8F", -1);
        // 9
        goToTable.put("9E", -1);
        goToTable.put("9T", -1);
        goToTable.put("9F", -1);
        // 10
        goToTable.put("10E", -1);
        goToTable.put("10T", -1);
        goToTable.put("10F", -1);
        // 11
        goToTable.put("11E", -1);
        goToTable.put("11T", -1);
        goToTable.put("11F", -1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        LR0Parser parser = new LR0Parser(
                "C://Users//sirkh//OneDrive//Documents//NetBeansProjects//CPCS302_Project//src//cpcs302_project/input.txt");
        parser.startParsing();
    }
}
