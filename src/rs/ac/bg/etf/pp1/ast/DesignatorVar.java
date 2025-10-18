// generated with ast extension for cup
// version 0.8
// 18/9/2025 18:25:34


package rs.ac.bg.etf.pp1.ast;

public class DesignatorVar extends Designator {

    private String d;

    public DesignatorVar (String d) {
        this.d=d;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d=d;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorVar(\n");

        buffer.append(" "+tab+d);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorVar]");
        return buffer.toString();
    }
}
