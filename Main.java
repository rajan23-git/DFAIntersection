import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;

/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{

    public static void main(String [] args){
        File DFA_1= new File("DFA_1.txt");//first DFA file
        File DFA_2= new File("DFA_2.txt");//second DFA file
        String startStateFirst;//start state of first DFA
        String startStateSecond;//start state of second DFA
        String startStateIntersected;//start state of intersected DFA
        ArrayList<String> stateNames= new  ArrayList<String> ();//names of states
        ArrayList<String> inputSymbols= new ArrayList<String>();//input alphabet
        ArrayList<State> firstStates= new ArrayList<State>();
        //arraylist contain state objects of first dfa
        ArrayList<String> firstAcceptStateNames= new  ArrayList<String> ();
        //accept states of first DFA
        ArrayList<String> secondAcceptStateNames=new ArrayList<String>();
        //accept states of second DFA
        
        Scanner scan= new Scanner(System.in);
        System.out.println("Keep typing in accept states for first DFA.To stop adding accept states type in X.");
        String input="abcdefg";
        while(true){
            //while loop keeps prompting user for accept states in first DFA
            System.out.println("State: ");
            input=scan.nextLine();
            if(input.equals("X")){
                break;
            }
            firstAcceptStateNames.add(input);

        
        
        }
        System.out.println("Now type in the start state of the first DFA");
        startStateFirst=scan.nextLine();
        
        System.out.println("Keep typing in accept states for second DFA. To stop adding accept states type in X.");

        input="xyz";
        while(true){
            //while loop keeps prompting user for accept states in second DFA
            System.out.println("State: ");
            input=scan.nextLine();
            if(input.equals("X")){

                break;
            }
            secondAcceptStateNames.add(input);

        }
        
       System.out.println("Now type in start state of second DFA");
       startStateSecond= scan.nextLine();
       startStateIntersected=startStateFirst+","+startStateSecond;
       
       
       

        try
        {
            Scanner readFileOne=new Scanner( DFA_1);
            String alphabetString=readFileOne.nextLine();
            String [] alphabetStringArray=alphabetString.split(" ");
            for(String s : alphabetStringArray){
                //adds alphabet symbols to arraylist

                inputSymbols.add(s);
            }
            //reads entirely DFA file 1
            while(readFileOne.hasNextLine()){
                String stateInfo=readFileOne.nextLine();
                String [] stateInfoArray=stateInfo.split(" ");

                stateNames.add(stateInfoArray[0]);

                State tempState= new State();
                tempState.stateName= stateInfoArray[0];
                //creates new state
                //sets the name and destinations of the state
                for(int i=1;i<stateInfoArray.length;i++){
                    (tempState.destStates).add(stateInfoArray[i]);

                }
                //adds state to arraylist
                firstStates.add(tempState);

            }
        }

        catch (java.io.FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }

        ArrayList<State> secondStates= new ArrayList<State>();
        try
        {
            Scanner readFileTwo=new Scanner( DFA_2);
            readFileTwo.nextLine();

            while(readFileTwo.hasNextLine()){
                String stateInfo=readFileTwo.nextLine();
                String [] stateInfoArray=stateInfo.split(" ");

                State tempState= new State();
                tempState.stateName= stateInfoArray[0];
                //creates temporary state and sets the name
                //and distinations of the state
                for(int i=1;i<stateInfoArray.length;i++){
                    (tempState.destStates).add(stateInfoArray[i]);

                }
                //adds temprary state to arraylist
                secondStates.add(tempState);

            }
        }

        catch (java.io.FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        //arraylist that contains intersected states
        ArrayList<State> intersectedStates= new ArrayList<State>();

        for(int i=0;i<firstStates.size();i++){
            for(int j=0;j<secondStates.size();j++){
                String combinedStateName=(firstStates.get(i)).stateName+
                    ","+(secondStates.get(j)).stateName;

                State tempState= new State();
                tempState.stateName=combinedStateName;
                //combines state names to create name of intersected state
                ArrayList<String> destStatesOfFirst=new ArrayList<String>();
                ArrayList<String> destStatesOfSecond= new ArrayList<String>();
                destStatesOfFirst=(firstStates.get(i)).destStates;
                destStatesOfSecond=(secondStates.get(j)).destStates;
                for(int k=0;k<destStatesOfFirst.size();k++){
                    //for loop walks through the destination states of
                    //both states of interest
                    //combines together two states that are reached via
                    //a transition on the same input symbol
                    //adds this to the destination states of 
                    //the intersected state
                    String combinedDestString=destStatesOfFirst.get(k)
                        +","+destStatesOfSecond.get(k);
                    (tempState.destStates).add(combinedDestString);

                }
                intersectedStates.add(tempState);

            }
        }

        ArrayList<String> intersectedAcceptStateNames= new ArrayList<String>();
        //finds all resulting accept states by finding cross product
        //between accept states of the two individual DFAs
        for(int i=0;i<firstAcceptStateNames.size();i++){
            for(int j=0;j<secondAcceptStateNames.size();j++){
                String intersectedAcceptStateName=firstAcceptStateNames.get(i)
                    +","+secondAcceptStateNames.get(j);
                intersectedAcceptStateNames.add(intersectedAcceptStateName);
            }

        }

        try
        {
            File DFA_Intersected= new File("DFA_Intersected.txt");
            if(!DFA_Intersected.exists()){
                DFA_Intersected.createNewFile();
            }
            PrintWriter pw= new PrintWriter(DFA_Intersected);
            //prints input symbols to the first line of intersected DFA file
            for(int i=0;i<inputSymbols.size();i++){
                pw.print(inputSymbols.get(i)+" ");
            }
            pw.print("\n");
            //these nested for loops print DFA transtion table of
            //intersected DFA

            for(int i=0;i<intersectedStates.size();i++){
                String tableRow="";
                tableRow+=(intersectedStates.get(i)).stateName+" ";
                //table Row string stores the current state and all its
                //destination states in one string

                ArrayList<String> intersectedDestStates= new ArrayList<String>();
                intersectedDestStates=(intersectedStates.get(i)).destStates;
                //for loop loops through each state of intersected DFA
                //and prints out each state and its destination states
                for(int j=0;j<intersectedDestStates.size();j++){
                    tableRow+= intersectedDestStates.get(j)+" ";

                }

                pw.println(tableRow);

            }

            pw.close();
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();

        }
        //prints accept states of intersected DFA to console
        String acceptStateMessage="Accepted States of Intersected DFA are: ";
        for(int i=0;i<intersectedAcceptStateNames.size();i++){
            acceptStateMessage+=intersectedAcceptStateNames.get(i)+" ";

        }

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(acceptStateMessage);
        System.out.println("Start State of Intersected DFA is: "+startStateIntersected);

    }
}
