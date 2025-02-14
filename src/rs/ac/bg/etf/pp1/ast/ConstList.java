// generated with ast extension for cup
// version 0.8
// 4/0/2025 18:36:11


package rs.ac.bg.etf.pp1.ast;

public class ConstList extends ConstantList {

    private ConstantList ConstantList;
    private String constDecl2;
    private Constants Constants;

    public ConstList (ConstantList ConstantList, String constDecl2, Constants Constants) {
        this.ConstantList=ConstantList;
        if(ConstantList!=null) ConstantList.setParent(this);
        this.constDecl2=constDecl2;
        this.Constants=Constants;
        if(Constants!=null) Constants.setParent(this);
    }

    public ConstantList getConstantList() {
        return ConstantList;
    }

    public void setConstantList(ConstantList ConstantList) {
        this.ConstantList=ConstantList;
    }

    public String getConstDecl2() {
        return constDecl2;
    }

    public void setConstDecl2(String constDecl2) {
        this.constDecl2=constDecl2;
    }

    public Constants getConstants() {
        return Constants;
    }

    public void setConstants(Constants Constants) {
        this.Constants=Constants;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstantList!=null) ConstantList.accept(visitor);
        if(Constants!=null) Constants.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstantList!=null) ConstantList.traverseTopDown(visitor);
        if(Constants!=null) Constants.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstantList!=null) ConstantList.traverseBottomUp(visitor);
        if(Constants!=null) Constants.traverseBottomUp(visitor);
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

        buffer.append(" "+tab+constDecl2);
        buffer.append("\n");

        if(Constants!=null)
            buffer.append(Constants.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstList]");
        return buffer.toString();
    }
}
