/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alphabetapruning;

import java.util.LinkedList;

/**
 *
 * @author drew
 */
public class AlphaBetaPruning {

    
    private StateGenerator gen;
    private LinkedList<Node> graph;// this is the min-max tree stored as a stack
    
    public class Node {
        int alpha, beta, value; 
        boolean isMin;// what type of node true if min false if max
        Object state;
        public Node(boolean isMin) {
            this.isMin = isMin;
            alpha = Integer.MAX_VALUE;
            beta = Integer.MIN_VALUE;
            value = Integer.MIN_VALUE;
            state = null;
        }
        public Node(boolean isMin, Object state) {
            this.isMin = isMin;
            alpha = Integer.MAX_VALUE;
            beta = Integer.MIN_VALUE;
            value = Integer.MIN_VALUE;
            this.state = state;
        }
        public Node(boolean isMin, Object state, int alpha, int beta) {
            this.isMin = isMin;
            this.alpha = alpha;
            this.beta = beta;
            value = Integer.MIN_VALUE;
            this.state = state;
        }
        public Node(int alpha, int beta) {
            this.alpha = alpha;
            this.beta = beta;
            value = Integer.MIN_VALUE;
            state = null;
        }
        
    }
    
    
    
    //note that max-depth is taken care of by the state generator
    // it will return null when it is ok to get the value
    public int playMax(StateGenerator gen) {
        graph = new LinkedList();
        graph.push(new Node(false));
        this.gen = gen;
        return play();
    }
    
    public int playMin(StateGenerator gen) {
        graph = new LinkedList();
        graph.push(new Node(true));
        this.gen = gen;
        return play();
    }
    
    
    private int play() {
        
        Node curNode = new Node(true);// so no null pointer is thrown.
        
        while(!graph.isEmpty()) {
            curNode = graph.pop();
            Object nextState = gen.getNextState(curNode.state);
            if (nextState == null && gen.isLeaf(curNode)) {
                // then we can get the value for curNode
                // only if it is a leaf node
                curNode.value = gen.getValue(curNode.state);
            }
            
            else if (nextState != null) {
                graph.push(curNode);// push it back on
                // alpha,beta values are coppied down from parent
                graph.push(new Node(!curNode.isMin, nextState, curNode.alpha, curNode.beta));//and push on the next node
                continue;
            }
            
            // else I am done exploring so eval and move up a level
            if (graph.peek() != null) {
                Node parent = graph.pop();
                if(parent.isMin) {
                    evalMin(parent, curNode);
                }else {
                    // max
                    evalMax(parent, curNode);
                }
                    
            }
        }
        
        
        return curNode.value;
    }
    
    
    /**
     * This means the parent is a min node and that it is deciding on whether
     * to incorporate the 
     * @param parent
     * @param child 
     */
    public void evalMin(Node parent, Node child) {
        
        
        if (child.value < parent.value) {
            parent.value = child.value;
        }
        
        if (child.value <= parent.beta) {
            parent.beta = child.value;
        }
        
        if (parent.beta <= parent.alpha) {
            // then I know that I can prune the rest
            parent.value = parent.beta;
            // then I have to transfer stuff up to the grandparent
            if (graph.peek() != null) {
                evalMax(graph.pop(), parent);
            }
            
        }
        else {
            // I have to push the parent back on
            graph.push(parent);
        }
        
    }
    
    public void evalMax(Node parent, Node child) {
        // change the alpha value
        
        
        if (child.value > parent.value) {
            parent.value = child.value;
        }
        
        if (child.value >= parent.alpha) {
            parent.alpha = child.value;
        }
        
        if (parent.beta <= parent.alpha) {
            // then I know that I can prune the rest
            //parent.value = parent.beta;
            // then I have to transfer stuff up to the grandparent
            if (graph.peek() != null) {
                evalMin(graph.pop(), parent);
            }
            
        }
        else {
            // I have to push the parent back on
            graph.push(parent);
        }
        
    }
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
