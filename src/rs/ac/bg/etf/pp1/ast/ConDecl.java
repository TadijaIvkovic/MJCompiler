// generated with ast extension for cup
// version 0.8
// 23/9/2025 13:38:52


package rs.ac.bg.etf.pp1.ast;

public class ConDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constDecl;
    private Constants Constants;

    public ConDecl (String constDecl, Constants Constants) {
        this.constDecl=constDecl;
        this.Constants=Constants;
        if(Constants!=null) Constants.setParent(this);
    }

    public String getConstDecl() {
        return constDecl;
    }

    public void setConstDecl(String constDecl) {
        this.constDecl=constDecl;
    }

    public Constants getConstants() {
        return Constants;
    }

    public void setConstants(Constants Constants) {
        this.Constants=Constants;
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
        if(Constants!=null) Constants.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Constants!=null) Constants.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Constants!=null) Constants.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConDecl(\n");

        buffer.append(" "+tab+constDecl);
        buffer.append("\n");

        if(Constants!=null)
            buffer.append(Constants.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConDecl]");
        return buffer.toString();
    }
}
