// generated with ast extension for cup
// version 0.8
// 22/9/2025 11:53:53


package rs.ac.bg.etf.pp1.ast;

public class NoDecLists extends DeclarationList {

    public NoDecLists () {
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
        buffer.append("NoDecLists(\n");

        buffer.append(tab);
        buffer.append(") [NoDecLists]");
        return buffer.toString();
    }
}
