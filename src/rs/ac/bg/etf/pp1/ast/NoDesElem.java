// generated with ast extension for cup
// version 0.8
// 15/11/2024 17:37:2


package rs.ac.bg.etf.pp1.ast;

public class NoDesElem extends DesignatorElem {

    public NoDesElem () {
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
        buffer.append("NoDesElem(\n");

        buffer.append(tab);
        buffer.append(") [NoDesElem]");
        return buffer.toString();
    }
}
