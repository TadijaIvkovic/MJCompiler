// generated with ast extension for cup
// version 0.8
// 2/0/2025 18:17:23


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationArray extends VarDeclaration {

    private String varDeclaration;

    public VarDeclarationArray (String varDeclaration) {
        this.varDeclaration=varDeclaration;
    }

    public String getVarDeclaration() {
        return varDeclaration;
    }

    public void setVarDeclaration(String varDeclaration) {
        this.varDeclaration=varDeclaration;
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

        buffer.append(" "+tab+varDeclaration);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationArray]");
        return buffer.toString();
    }
}
