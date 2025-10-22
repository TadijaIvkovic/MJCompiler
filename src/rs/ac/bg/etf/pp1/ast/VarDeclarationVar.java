// generated with ast extension for cup
// version 0.8
// 22/9/2025 11:53:53


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationVar extends VarDeclaration {

    private String varDeclarationV;

    public VarDeclarationVar (String varDeclarationV) {
        this.varDeclarationV=varDeclarationV;
    }

    public String getVarDeclarationV() {
        return varDeclarationV;
    }

    public void setVarDeclarationV(String varDeclarationV) {
        this.varDeclarationV=varDeclarationV;
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
        buffer.append("VarDeclarationVar(\n");

        buffer.append(" "+tab+varDeclarationV);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationVar]");
        return buffer.toString();
    }
}
