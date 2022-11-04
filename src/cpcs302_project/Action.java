/*
 Khaled Alasmari - 2035189
 Omar abdulrahman abdulbagi - 2037070
 Salman alhodaly - 2044556
 */
package cpcs302_project;

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
