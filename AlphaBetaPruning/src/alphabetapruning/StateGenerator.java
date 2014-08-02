/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alphabetapruning;

/**
 * The purpose of this interface is so that the alpha-beta prunning
 * algorithm can get the value of the state of the system.
 * @author drew
 */
public interface StateGenerator {
    
    /**
     * I don't really care how you are determining the value so just use an object for state
     * @param state the state that I want the value for
     * @return the integer representation of the worth of being in that state.
     */
    int getValue(Object state);
    /**
    /* the state of the system given the current state
    /*  null state indicates beginning
    * Note that you must also keep track of the states that you have already returned
    * so that you don't return the same state.  Because the algorithm may ask you multiple
    * times for the next state given the same argument and each time it should be different
    * or else null to indicate that no more next states and that you should be able to get
    * the value for that state object now.
    * returning null on non-leaf nodes means done for that level not that their is a value available
    */
    Object getNextState(Object state);
    
    
    
    /**
     * Used to determine if a state is a leaf node
     * @param state the node that you want to check
     * @return 
     */
    boolean isLeaf(Object state);
}
