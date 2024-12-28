// generated with ast extension for cup
// version 0.8
// 28/11/2024 19:26:29


package rs.ac.bg.etf.pp1.ast;

public class NoMethTypeName extends MethodSignature {

    private String methName;
    private FormPars FormPars;

    public NoMethTypeName (String methName, FormPars FormPars) {
        this.methName=methName;
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
    }

    public String getMethName() {
        return methName;
    }

    public void setMethName(String methName) {
        this.methName=methName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormPars!=null) FormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoMethTypeName(\n");

        buffer.append(" "+tab+methName);
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoMethTypeName]");
        return buffer.toString();
    }
}
