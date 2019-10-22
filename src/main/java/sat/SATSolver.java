package sat;

import immutable.ImList;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import java.util.*;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        // TODO: implement this.



        throw new RuntimeException("not yet implemented.");
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        // TODO: implement this.
        //throw new RuntimeException("not yet implemented.");
        int smallest = 100000;
        Clause smallest_cl;
        for(Clause cl : clauses){
            if(cl.size() < smallest){
                cl.size = smallest;
                smallest_cl = cl;
            }
        }
        if(smallest_cl.isUnit()){
            substitute(clauses , smallest_cl.chooseLiteral())           
        }

        if(clauses)


        for(Clause cl : clauses){
            for(Literal li : cl){
                if(env.get(li.getVariable) == true){
                    substitute(clauses,li.getVariable);
                    break;
                }else{

                }
            }
        }

        


    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        // TODO: implement this.
        //throw new RuntimeException("not yet implemented.");
        ImList<Clause> out_clauses;

        for(Clause cl : clauses){
            // if(cl.contains(l)){
            //     Clause new_cl = cl.reduce(l);
            //     if(new_cl.isEmpty){
            //         ;
            //     }else{
            //         out_clauses.add();
            //     }
            // }else{
            //     out_clauses.add(cl);
            // }

            if(cl.reduce(l) != null){
                out_clauses.add(cl);
            }else{
                ;
            }
        }
        return out_clauses;
    }

}
