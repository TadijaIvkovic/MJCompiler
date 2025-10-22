// generated with ast extension for cup
// version 0.8
// 22/9/2025 11:53:53


package rs.ac.bg.etf.pp1.ast;

public class ConstList extends ConstantList {

    private ConstantList ConstantList;
    private ConDecl ConDecl;

    public ConstList (ConstantList ConstantList, ConDecl ConDecl) {
        this.ConstantList=ConstantList;
        if(ConstantList!=null) ConstantList.setParent(this);
        this.ConDecl=ConDecl;
        if(ConDecl!=null) ConDecl.setParent(this);
    }

    public ConstantList getConstantList() {
        return ConstantList;
    }

    public void setConstantList(ConstantList ConstantList) {
        this.ConstantList=ConstantList;
    }

    public ConDecl getConDecl() {
        return ConDecl;
    }

    public void setConDecl(ConDecl ConDecl) {
        this.ConDecl=ConDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstantList!=null) ConstantList.accept(visitor);
        if(ConDecl!=null) ConDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstantList!=null) ConstantList.traverseTopDown(visitor);
        if(ConDecl!=null) ConDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstantList!=null) ConstantList.traverseBottomUp(visitor);
        if(ConDecl!=null) ConDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstList(\n");

        if(ConstantList!=null)
            buffer.append(ConstantList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConDecl!=null)
            buffer.append(ConDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstList]");
        return buffer.toString();
    }
}
