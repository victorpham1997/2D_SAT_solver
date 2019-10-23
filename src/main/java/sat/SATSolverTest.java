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
        long timeTaken = time - started;
        System.out.println("Time:" + timeTaken / 1000000.0 + "ms");

        if (env == null)
        {
            System.out.println("not satisfiable");
        } else {
            System.out.println("satisfiable");
            System.out.println(env.toString());
        }
    }

    private static List<String> readFile(String fileName)
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

    private static Formula linesToFormula(List<String> lines)
    {
        ArrayList<Literal> literal_ls = new ArrayList<>(); //to store literals to pass to clause making function makeCL()
        ArrayList<Clause> clause_ls = new ArrayList<>(); //to store clause to pass to Formula making function makeFm()

        for (String line : lines) {
            if (!(line.toCharArray().length == 0)) {
                if (line.toCharArray()[0] == 'c') {
                    System.out.println(line);
                } else if (line.toCharArray()[0] == 'p') {
                    System.out.println(line);
                } else {
                    String[] literals_arr = line.trim().split("\\s+"); // to store literal labels
                    for (String l_str : literals_arr) {
                        if (l_str.charAt(0) == '-') {
                            Literal l = NegLiteral.make(l_str.substring(1));
                            literal_ls.add(l);
                        } else if (l_str.charAt(0) != '0') {
                            Literal l = PosLiteral.make(l_str);
                            literal_ls.add(l);
                        } else {
                            clause_ls.add(makeCl(literal_ls));
                            literal_ls.clear();
                            //add literals to the list until 0 is encountered, create clause, clear list
                        }
                    }
                }
            }
        }
        return makeFm(clause_ls);
    }

    private static Formula makeFm(ArrayList<Clause> clauses_arr) {
        Formula f = new Formula();
        for (Clause c : clauses_arr)
        {
            f = f.addClause(c);
        }
        return f;
    }

    private static Clause makeCl(ArrayList<Literal> literals_arr) {
        Clause c = new Clause();
        for (Literal l : literals_arr)
        {
            c = c.add(l);
        }
        return c;
    }
}