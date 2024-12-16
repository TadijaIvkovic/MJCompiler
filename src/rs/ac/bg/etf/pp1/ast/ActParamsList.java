// generated with ast extension for cup
// version 0.8
// 16/11/2024 13:50:13


package rs.ac.bg.etf.pp1.ast;

public class ActParamsList extends ActParamList {

    private ActParamList ActParamList;
    private Expr Expr;

    public ActParamsList (ActParamList ActParamList, Expr Expr) {
        this.ActParamList=ActParamList;
        if(ActParamList!=null) ActParamList.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ActParamList getActParamList() {
        return ActParamList;
    }

    public void setActParamList(ActParamList ActParamList) {
        this.ActParamList=ActParamList;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParamList!=null) ActParamList.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParamList!=null) ActParamList.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParamList!=null) ActParamList.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParamsList(\n");

        if(ActParamList!=null)
            buffer.append(ActParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParamsList]");
        return buffer.toString();
    }
}
