package sat;

import immutable.ImList;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;

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
//        System.out.println("solve started");
//        System.out.println(formula.getClauses());
        Environment result = solve(formula.getClauses(),new Environment());
        if(!(result== null)) return result;
        else return null;

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
        if(clauses.isEmpty()){
            return env;
        }
        int smallest = clauses.first().size();
        Clause smallest_cl = clauses.first();
        for(Clause cl : clauses){
            if(cl.isEmpty()){
                return null;
            }
            if(cl.size() < smallest){
                smallest = cl.size();
                smallest_cl = cl;
            }
        }
        //System.out.println(smallest_cl);

        Literal l = smallest_cl.chooseLiteral(); //choose the first literal of the smallest clause
        if(smallest_cl.isUnit()){
            ImList<Clause> new_clauses = substitute(clauses , l );
            //set environment
            Environment new_env = updateEnv(l, env);
            return solve(new_clauses, new_env);

        }else{
            ImList<Clause> new_clauses = substitute(clauses , l);
            //set environment
            Environment new_env = updateEnv(l, env);
            Environment temp_env = solve(new_clauses, new_env);

            if(temp_env == null){
                ImList<Clause> new_false_clauses = substitute(clauses , l.getNegation());
                //set environment
                Environment new_false_env = updateEnv(l.getNegation(), env);
                return solve(new_false_clauses, new_false_env);
            }
            return temp_env;
        }

    }
    private static Environment updateEnv( Literal l, Environment env){
        if(l instanceof PosLiteral ){
            return env.putTrue(l.getVariable());
        }else{
            return env.putFalse(l.getVariable());
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
        for(Clause cl : clauses){
            Clause reduced = cl.reduce(l);
            if(!(reduced == null)){
                clauses = clauses.remove(cl);
                clauses = clauses.add(reduced);
            }else{
                clauses = clauses.remove(cl);
            }
        }
        return clauses;
    }

}
