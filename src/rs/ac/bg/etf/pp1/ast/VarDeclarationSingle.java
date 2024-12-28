// generated with ast extension for cup
// version 0.8
// 28/11/2024 19:26:29


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationSingle extends VarDeclaration {

    private String varDeclaration;

    public VarDeclarationSingle (String varDeclaration) {
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
        buffer.append("VarDeclarationSingle(\n");

        buffer.append(" "+tab+varDeclaration);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationSingle]");
        return buffer.toString();
    }
}
