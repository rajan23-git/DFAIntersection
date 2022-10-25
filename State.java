import java.util.ArrayList;

//this class stores key information for a state
//including what the state is named
//and also which other states this certain state transtitions into
//on different input symbols from the alphabet.
public class State
{
    public String stateName;//state's Name
    public ArrayList<String> destStates; //arraylist of destination states from this state
    
    public State(){
        stateName=null; //constructor initializes stateName to null
        destStates= new ArrayList<String>(); //dest states arraylist initialized to length 10
        
    }
    

    
   
}
