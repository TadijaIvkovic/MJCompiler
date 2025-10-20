// generated with ast extension for cup
// version 0.8
// 20/9/2025 17:57:57


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationArray extends VarDeclaration {

    private String varDeclarationA;

    public VarDeclarationArray (String varDeclarationA) {
        this.varDeclarationA=varDeclarationA;
    }

    public String getVarDeclarationA() {
        return varDeclarationA;
    }

    public void setVarDeclarationA(String varDeclarationA) {
        this.varDeclarationA=varDeclarationA;
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
        buffer.append("VarDeclarationArray(\n");

        buffer.append(" "+tab+varDeclarationA);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationArray]");
        return buffer.toString();
    }
}
