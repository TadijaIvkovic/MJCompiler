// generated with ast extension for cup
// version 0.8
// 13/11/2024 16:10:20


package rs.ac.bg.etf.pp1.ast;

public class ProgNameDerived1 extends ProgName {

    private String pName;

    public ProgNameDerived1 (String pName) {
        this.pName=pName;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName=pName;
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
        buffer.append("ProgNameDerived1(\n");

        buffer.append(" "+tab+pName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgNameDerived1]");
        return buffer.toString();
    }
}
