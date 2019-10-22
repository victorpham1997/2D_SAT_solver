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
        if(clauses.isEmpty()){
            return env;
        }
        for(Clause cl : clauses){
            if(cl.isEmpty()){
                return null;
            }
        }
        int smallest = 100000;
        Clause smallest_cl;
        for(Clause cl : clauses){
            if(cl.size() < smallest){
                smallest = cl.size;
                smallest_cl = cl;
            }
        }
        Literal l = smallest_cl.chooseLiteral();
        if(smallest_cl.isUnit()){
            ImList<Clause> new_clauses = substitute(clauses , l );        
            //set enviroment
            Environment new_env = updateEnv(l, env);
            return solve(new_clauses, new_env);

        }else{
            ImList<Clause> new_clauses = substitute(clauses , l);          
            //set environemnt
            Environment new_env = updateEnv(l, env);
            return solve(new_clauses, new_env); 

            if(new_e == null){
                ImList<Clause> new_clauses_false = substitute(clauses , l.getNegation());
                //set environment
                Environment new_env = updateEnv(l.getNegation(), env);
                return solve(new_clauses_false, new_env);
            }
        }

    }
    private static Environemnt updateEnv( Literal l, Environemnt env){
        if(l instanceof PosLiteral ){
            Environemnt new_env = env.putTrue(l.getVariable());
        }else if(l instanceof NegLiteral){
            Environemnt new_env = env.putFalse(l.getVariable());
        }
        return new_env;
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
        ImList<Clause> out_clauses= new ImList<Clause>();

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
