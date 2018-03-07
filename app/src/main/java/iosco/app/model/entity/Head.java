package iosco.app.model.entity;

/**
 * Created by Victor Casas on 12/02/2016.
 */
public class Head extends Row{
    private String name;
    private int val1;
    private int val2;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVal1() {
        return val1;
    }

    public void setVal1(int val1) {
        this.val1 = val1;
    }

    public int getVal2() {
        return val2;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }
}
