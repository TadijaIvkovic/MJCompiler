// generated with ast extension for cup
// version 0.8
// 16/11/2024 19:45:59


package rs.ac.bg.etf.pp1.ast;

public class DesignatorEl extends Designator {

    private String desName;
    private DesignatorElem DesignatorElem;

    public DesignatorEl (String desName, DesignatorElem DesignatorElem) {
        this.desName=desName;
        this.DesignatorElem=DesignatorElem;
        if(DesignatorElem!=null) DesignatorElem.setParent(this);
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName=desName;
    }

    public DesignatorElem getDesignatorElem() {
        return DesignatorElem;
    }

    public void setDesignatorElem(DesignatorElem DesignatorElem) {
        this.DesignatorElem=DesignatorElem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorElem!=null) DesignatorElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorElem!=null) DesignatorElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorElem!=null) DesignatorElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorEl(\n");

        buffer.append(" "+tab+desName);
        buffer.append("\n");

        if(DesignatorElem!=null)
            buffer.append(DesignatorElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorEl]");
        return buffer.toString();
    }
}
