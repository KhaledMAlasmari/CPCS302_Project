/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpcs302_project;

/**
 *
 * @author sirkh
 */
public class Action {

    private String type;
    private int operand;

    public Action(String type, int operand) {
        this.operand = operand;
        this.type = type;
    }

    public int getOperand() {
        return operand;
    }

    public String getType() {
        return type;
    }
    
    public boolean isShift(){
        return type.equals("Shift");
    }

    public boolean isReduce(){
        return type.equals("Reduce");
    }

    public boolean isAccept(){
        return type.equals("Accept");
    }
    public boolean isError(){
        return type.equals("error");
    }

    @Override
    public String toString() {
        return String.format("%s %d", type, operand);
    }    
}
