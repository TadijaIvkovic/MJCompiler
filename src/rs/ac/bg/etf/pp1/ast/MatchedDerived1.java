// generated with ast extension for cup
// version 0.8
// 25/11/2024 15:28:25


package rs.ac.bg.etf.pp1.ast;

public class MatchedDerived1 extends Matched {

    public MatchedDerived1 () {
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
        buffer.append("MatchedDerived1(\n");

        buffer.append(tab);
        buffer.append(") [MatchedDerived1]");
        return buffer.toString();
    }
}