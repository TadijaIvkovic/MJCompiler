// generated with ast extension for cup
// version 0.8
// 28/11/2024 19:26:29


package rs.ac.bg.etf.pp1.ast;

public class NoConstList extends ConstantList {

    public NoConstList () {
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
        buffer.append("NoConstList(\n");

        buffer.append(tab);
        buffer.append(") [NoConstList]");
        return buffer.toString();
    }
}
