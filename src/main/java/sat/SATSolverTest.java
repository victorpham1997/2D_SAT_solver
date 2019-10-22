package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();


    // TODO: add the main method that reads the .cnf file and calls SATSolver.solve to determine the satisfiability
    public static void main(String[] args)
    {
        String fileName = "/home/victorpham1997/Workplace/SUTD/2D/2D_SAT_solver/SAT/Project-2D-starting/sampleCNF/largeSat.cnf";
        List<String> lines = readFile(fileName);
        Formula formula = linesToFormula(lines);
        System.out.println(formula);

        System.out.println("SAT solver starts!!!");
        long started = System.nanoTime();
        Environment env = SATSolver.solve(formula);
        long time = System.nanoTime();
        long timeTaken= time - started;
        System.out.println("Time:" + timeTaken/1000000.0 + "ms");

//        System.out.println(formula);
        if (env == null) {
            System.out.println("not satisfiable");
        }
        else {
            System.out.println("satisfiable");
            System.out.println(env.toString());
//            try {
//                String nameOfOutputFile = "BoolAssignment.txt";
//                FileWriter fw = new FileWriter(yourFilePath + nameOfOutputFile);
//                PrintWriter output = new PrintWriter(fw);
//                for (int varNum = 1; varNum <= numOfVar; varNum++) {
//                    Bool bool = env.get(new Variable(Integer.toString(varNum))); // get the boolean values of the variables from the env
//                    if (bool == Bool.TRUE) {
//                        output.println(Integer.toString(varNum) + ":TRUE");
//                    }
//                    else { // if (bool == Bool.FALSE)
//                        output.println(Integer.toString(varNum) + ":FALSE");
//                    }
//                }
//                output.close();
//            } catch (IOException e) { // to catch error, if there is.
//                e.printStackTrace();
//            }
        }
    }

    public static List<String> readFile(String fileName)
    {
        List<String> lines = Collections.emptyList();
        try
        {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return lines;
    }

    public static Formula linesToFormula(List<String> lines)
    {
//        Formula formula = new Formula();
        ArrayList<Literal> literal_ls = new ArrayList<>(); //to store literals to pass to clause making function
        ArrayList<Clause> clause_ls = new ArrayList<>(); //to store clause to pass to Formula making function

        for (String line : lines)
        {
            if (line.toCharArray().length == 0)
            {
                continue;
            }
            else if (line.toCharArray()[0] == 'c')
            {
                System.out.println(line);
                continue;
            }
            else if (line.toCharArray()[0] == 'p')
            {
                System.out.println(line);
            }
            else
            {
//                System.out.println(line);
                String[] literals_arr = line.trim().split("\\s+"); // to store literal labels
//                for (String aa : literals_arr)
//                    System.out.println(aa);

                for (String l_str : literals_arr){
//                    System.out.println(l_str);
                    if(l_str.charAt(0) == '-'){
                        Literal l = NegLiteral.make(l_str.substring(1));
//                        System.out.println(l);
                        literal_ls.add(l);
                    }else if(l_str.charAt(0) != '0'){
                        Literal l = PosLiteral.make(l_str);
                        literal_ls.add(l);
                    }else {
                        clause_ls.add(makeCl(literal_ls));
//                        System.out.println(literal_ls);
                        literal_ls.clear();
                    }
                }
            }
        }

        return makeFm(clause_ls);
    }

    private static Formula makeFm(ArrayList<Clause> clauses_arr) {
        Formula f = new Formula();
        for (Clause c : clauses_arr) {
            f = f.addClause(c);
        }
        return f;
    }

//    private static Clause makeCl(Literal... e) {
//        Clause c = new Clause();
//        for (Literal l : e) {
//            c = c.add(l);
//        }
//        return c;
//    }

    private static Clause makeCl(ArrayList<Literal> literals_arr) {
        Clause c = new Clause();
        for (Literal l : literals_arr) {
            c = c.add(l);
        }
        return c;
    }

//    public static void main(String [] args){
//        SATSolverTest TEST =  new SATSolverTest();
//        System.out.println(TEST.testSATSolver1());
//        System.out.println(TEST.testSATSolver2());
////        System.out.println("hello world");
//    }
}

