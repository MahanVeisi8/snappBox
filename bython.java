//import java.util.*;
//
//public class bython {
//    static int numberOfInputs = 0;
//    static int counterForInputs = 0;
//    static Map<String, Integer> variables = new HashMap<>();
//    static List<Integer> inputs = new ArrayList<>();
//    static String validRegexForVariables = "^[A-z][A-z0-9]{0,9}$";
//    static String validRegexForNumbers = "([0-9]+)|(-[0-9]+)";
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        // getting the first commands
//        StringBuilder totalText = new StringBuilder();
//        int totalInputs = 0;
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//            if (line.equals("-----")) {
//                break;
//            }
//            if (line.contains("voroodi()")){
//                totalInputs++;
//            }
//            totalText.append(line).append("\n");
//        }
//        // removing the last "\n" to avoid any possible errors
//        totalText.deleteCharAt(totalText.length() - 1);
//
//        // getting the inputs
//        numberOfInputs = totalInputs;
//        for (int i = 0; i < totalInputs; i++) {
//            inputs.add(sc.nextInt());
//        }
//        // analyzing the commands
//        analyzeTheCommands(totalText.toString());
//        System.out.println(variables.size());
//    }
//
//    static void analyzeTheCommands(String fullCommands){
//        // in this question we assume that the commands, variables and inputs are valid
//        String[] commands = fullCommands.split("\n");
//        for (String command : commands) {
//            String[] eachLine = command.split(" ");
//
//            if (eachLine[0].contains("khoorooji")){
//                khoorooji(command);
//            }
//
//            else if (eachLine[0].equals("agar")){
//                agar(command);
//            }
//
//            else if (eachLine[1].equals("=")){
//                assignment(command);
//            }
//        }
//    }
//
//    static void khoorooji(String command){
//        String parameters = command.substring(
//                command.indexOf("(") + 1,
//                command.indexOf(")")
//        ) + ", ";
//        String[] parArray = parameters.split(", ");
//        // we have each of them in parArray in a clean format
//        // now we have to check if they are valid or not and then print them
//        for (String par : parArray) {
//            if (par.matches(validRegexForVariables)) {
//                if (variables.containsKey(par)) {
//                    System.out.print(variables.get(par) + " ");
//                } else {
//                    System.out.println("Error: variable " + par + " is not defined"); // additional; have not been declared in the question
//                }
//            } else if (par.matches(validRegexForNumbers)) {
//                System.out.print(par + " ");
//            } else {
//                System.out.println("Error: " + par + " is not a valid parameter"); // additional;
//            }
//        }
//        System.out.println();
//    }
//
//    static void agar(String command){
//        String[] eachLine = command.split(" ");
//        int indexOfComparable = 1;
//        int[] comparable = new int[2];
//        int numberOfParameters = 2;
//
//        for (int i=0; i<numberOfParameters; i++){
//            if (eachLine[indexOfComparable].matches(validRegexForNumbers)){
//                comparable[i] = Integer.parseInt(eachLine[indexOfComparable]);
//            }
//            else if (eachLine[indexOfComparable].equals("voroodi()")){
//                comparable[i] = inputs.get(counterForInputs);
//                counterForInputs++;
//            }
//            else {
//                // checking if the right side is a variable
//                if (variables.containsKey(eachLine[indexOfComparable])){
//                    comparable[i] = variables.get(eachLine[indexOfComparable]);
//                }
//                else {
//                    System.out.println("Error: variable " + eachLine[indexOfComparable] + " is not defined"); // additional; have not been declared in the question
//                }
//            }
//            indexOfComparable += 2;
//        }
//
//        if (comparable[0] == comparable[1]){
//            String newAssignmentCommand = command.substring(command.indexOf(":") + 2);
//            assignment(newAssignmentCommand);
//        }
//    }
//
//    static void assignment(String command){
//        // first checking the right side of the assignment
//        String[] eachPart = command.split(" ");
//        int rightResult = 0;
//
//        if (eachPart.length == 3) { //having a simple assignment : a = 3 or a = b or a = input()
//            // checking if the right side is value or input
//            if (eachPart[2].equals("voroodi()")){
//                rightResult = inputs.get(counterForInputs);
//                counterForInputs++;
//            }
//            else if (eachPart[2].matches(validRegexForNumbers)){
//                rightResult = Integer.parseInt(eachPart[2]);
//            }
//            else {
//                // checking if the right side is a variable
//                if (variables.containsKey(eachPart[2])){
//                    rightResult = variables.get(eachPart[2]);
//                }
//                else {
//                    System.out.println("Error: variable " + eachPart[2] + " is not defined"); // additional; have not been declared in the question
//                }
//            }
//        }
//
//        else if (eachPart.length == 5){ // having an expression : a = b + 3 or a = b + c
//            // checking if the right side is a valid expression
//            int[] rightValues = new int[2];
//            int numberOfParameters = 2;
//            int indexOfParameter = 2;
//            for (int j=0; j<numberOfParameters; j++ ){
//                if (eachPart[indexOfParameter].matches(validRegexForNumbers)){
//                    rightValues[j] = Integer.parseInt(eachPart[indexOfParameter]);
//                }
//                else if (eachPart[indexOfParameter].equals("voroodi()")){
//                    rightValues[j] = inputs.get(counterForInputs);
//                    counterForInputs++;
//                }
//                else {
//                    // checking if the right side is a variable
//                    if (variables.containsKey(eachPart[indexOfParameter])){
//                        rightValues[j] = variables.get(eachPart[indexOfParameter]);
//                    }
//                    else {
//                        System.out.println("Error: variable " + eachPart[indexOfParameter] + " is not defined"); // additional; have not been declared in the question
//                    }
//                }
//                indexOfParameter += 2;
//            }
//            // if eachPart[3] is + then we add the two values, if it is - then we subtract them
//            rightResult = eachPart[3].equals("+") ? rightValues[0] + rightValues[1] : rightValues[0] - rightValues[1];
//        }
//
//        if (eachPart[0].matches(validRegexForVariables)){
//            variables.put(eachPart[0], rightResult);
//        }
//        else {
//            System.out.println("Error: " + eachPart[0] + " is not a valid variable name"); // additional; have not been declared in the question
//        }
//    }
//}
//
//
