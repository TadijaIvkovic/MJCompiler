// generated with ast extension for cup
// version 0.8
// 16/11/2024 19:45:59


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private String I2;
    private Constants Constants;
    private ConstantList ConstantList;

    public ConstDeclList (Type Type, String I2, Constants Constants, ConstantList ConstantList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.Constants=Constants;
        if(Constants!=null) Constants.setParent(this);
        this.ConstantList=ConstantList;
        if(ConstantList!=null) ConstantList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public Constants getConstants() {
        return Constants;
    }

    public void setConstants(Constants Constants) {
        this.Constants=Constants;
    }

    public ConstantList getConstantList() {
        return ConstantList;
    }

    public void setConstantList(ConstantList ConstantList) {
        this.ConstantList=ConstantList;
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
        if(Type!=null) Type.accept(visitor);
        if(Constants!=null) Constants.accept(visitor);
        if(ConstantList!=null) ConstantList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(Constants!=null) Constants.traverseTopDown(visitor);
        if(ConstantList!=null) ConstantList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(Constants!=null) Constants.traverseBottomUp(visitor);
        if(ConstantList!=null) ConstantList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(Constants!=null)
            buffer.append(Constants.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstantList!=null)
            buffer.append(ConstantList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclList]");
        return buffer.toString();
    }
}
