package sat;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;

import immutable.ImList;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;
import java.lang.Math;
import java.util.Iterator;

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
    public static int i = 0;
    public static int inner = 100;
    public static int outer = 100;
    public static int restart = 1;
    public static int limit = 32*Luby_determination(restart);
    public static int conflict_no = 0;
    public static ImList<Clause> original_clauses;
    public static Clause learning_cl =  new Clause();

    public static Environment solve(Formula formula) {
//        System.out.println("solve started");
//        System.out.println(formula.getClauses());
        original_clauses = checkAllLiteral(formula.getClauses());
        Environment result = solve(original_clauses,new Environment());
//        Environment result = solve(checkAllLiteral(formula.getClauses()),new Environment());
        if(!(result== null)) return result;
        else return null;

    }
    private static int Luby_determination(int i){
        for(int k = 1; k < 32; k++){
            if (i == ((int)Math.pow(2, k) - 1)){
                return (int)Math.pow(2, k-1);
            }
        }
        for(int k =1;;k++){
            if(((int)Math.pow(2, k-1))<= i && i< ((int)Math.pow(2, k) - 1)){
                return Luby_determination(i - (int)Math.pow(2, k-1) + 1);
            }
        }
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
     * @returwann an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */

//    public static int k;
    private static Environment solve(ImList<Clause> clauses, Environment env) {

        if(clauses.isEmpty()){
            return env;
        }
//        else if(clauses.size() == 2){
////            System.out.println("yeeeha");
////            for(Clause cl : clauses){
////                System.out.println(cl);
////            }
//            if(clauses.first().merge(clauses.rest().first()) == null){
////                System.out.println("yeeeha");
//                return null;}
//        }

        if (conflict_no == limit){
            restart++;
//            System.out.println(restart);
            limit = 32*Luby_determination(restart);
//            System.out.println("restart");
//            System.out.println(limit);
            conflict_no = 0;
            //restart
            return solve(clauses, new Environment());
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
//        System.out.println( smallest_cl );
        if(smallest_cl.isUnit()){
            return solve(substitute(clauses , l ), updateEnv(l, env));

        }else{
            Environment temp_env = solve(substitute(clauses , l), updateEnv(l, env));
            learning_cl = learning_cl.add(l.getNegation());
//            System.out.println("add l");

            if(temp_env == null){
                if(!learning_cl.isEmpty()){
                    clauses = clauses.add(learning_cl);
                    System.out.println(learning_cl);
                    learning_cl = new Clause();
                }
                learning_cl = learning_cl.add(l);
                conflict_no++;
                return solve(substitute(clauses , l.getNegation()), updateEnv(l.getNegation(), env));
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
//            Clause cl_watched =  cl;
            if(cl.contains(l)){
                clauses = clauses.remove(cl);
            }else if(cl.contains(l.getNegation())){
                clauses = clauses.remove(cl).add(cl.reduce(l));
            }else{
                continue;
            }

        }
        return clauses;
    }

    private static Clause watchClause(Clause tis_clauses){
        if (tis_clauses.size() <3){ return tis_clauses;}
        Iterator<Literal> cl_iter = tis_clauses.iterator();
        Clause out_cl = new Clause().add(cl_iter.next()).add(cl_iter.next());
        System.out.println(out_cl);
        return out_cl;


    }
    private static ImList<Clause> checkAllLiteral(ImList<Clause> clauses){
        ArrayList<Literal> literals_arr = new ArrayList<>();
        ArrayList<Literal> black_ls = new ArrayList<>();
        for(Clause cl : clauses){
            for(Literal l : cl){
                if(!black_ls.contains(l)){
                    black_ls.add(l);
                    if(literals_arr.contains(l.getNegation())){
                        literals_arr.remove(l.getNegation());
                    }else if(!literals_arr.contains(l)){
                        literals_arr.add(l);
                    }
                }
            }
        }
        System.out.println(literals_arr);
        if(literals_arr.size() == 0){
            return clauses;
        }
        for(Clause cl : clauses){
            for(Literal pure_l : literals_arr){
                if (cl.contains(pure_l)){
                    clauses.remove(cl);
                }
            }
        }
        return clauses;
    }

}
