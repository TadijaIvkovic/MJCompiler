// generated with ast extension for cup
// version 0.8
// 22/9/2025 11:53:53


package rs.ac.bg.etf.pp1.ast;

public class VarDecLists extends DeclarationList {

    private VarDecl VarDecl;
    private DeclarationList DeclarationList;

    public VarDecLists (VarDecl VarDecl, DeclarationList DeclarationList) {
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDecl!=null) VarDecl.accept(visitor);
        if(DeclarationList!=null) DeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecLists(\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecLists]");
        return buffer.toString();
    }
}
