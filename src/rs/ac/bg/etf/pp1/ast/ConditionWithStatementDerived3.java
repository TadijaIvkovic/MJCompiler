// generated with ast extension for cup
// version 0.8
// 16/11/2024 19:45:59


package rs.ac.bg.etf.pp1.ast;

public class ConditionWithStatementDerived3 extends ConditionWithStatement {

    public ConditionWithStatementDerived3 () {
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
        buffer.append("ConditionWithStatementDerived3(\n");

        buffer.append(tab);
        buffer.append(") [ConditionWithStatementDerived3]");
        return buffer.toString();
    }
}
