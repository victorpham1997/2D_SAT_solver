package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();



    
    // TODO: add the main method that reads the .cnf file and calls SATSolver.solve to determine the satisfiability

    
    public Environment testSATSolver1(){
        // (a v b)
        Environment e = SATSolver.solve(makeFm(makeCl(a,b)) );
        return e;
/*
        assertTrue( "one of the literals should be set to true",
                Bool.TRUE == e.get(a.getVariable())  
                || Bool.TRUE == e.get(b.getVariable())  );
        
*/      
    }
    
    
    public Environment testSATSolver2(){
        // (~a)
        Environment e = SATSolver.solve(makeFm(makeCl(na)));
        return e;
        /*
        assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/      
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }

    public static void main(String [] args){
        SATSolverTest TEST =  new SATSolverTest();
        System.out.println(TEST.testSATSolver1());
        System.out.println(TEST.testSATSolver2());
//        System.out.println("hello world");
    }
    
    
}

