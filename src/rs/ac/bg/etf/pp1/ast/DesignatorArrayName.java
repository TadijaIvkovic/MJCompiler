// generated with ast extension for cup
// version 0.8
// 24/9/2025 9:14:15


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArrayName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String d;

    public DesignatorArrayName (String d) {
        this.d=d;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d=d;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("DesignatorArrayName(\n");

        buffer.append(" "+tab+d);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArrayName]");
        return buffer.toString();
    }
}
