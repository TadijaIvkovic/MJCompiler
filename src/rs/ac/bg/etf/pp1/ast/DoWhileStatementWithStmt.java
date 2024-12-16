// generated with ast extension for cup
// version 0.8
// 16/11/2024 13:50:13


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStatementWithStmt extends Matched {

    private DoWhileStart DoWhileStart;
    private Statement Statement;
    private WhileStart WhileStart;
    private Condition Condition;
    private Statement Statement1;
    private DoWhileEnd DoWhileEnd;

    public DoWhileStatementWithStmt (DoWhileStart DoWhileStart, Statement Statement, WhileStart WhileStart, Condition Condition, Statement Statement1, DoWhileEnd DoWhileEnd) {
        this.DoWhileStart=DoWhileStart;
        if(DoWhileStart!=null) DoWhileStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.WhileStart=WhileStart;
        if(WhileStart!=null) WhileStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
        this.DoWhileEnd=DoWhileEnd;
        if(DoWhileEnd!=null) DoWhileEnd.setParent(this);
    }

    public DoWhileStart getDoWhileStart() {
        return DoWhileStart;
    }

    public void setDoWhileStart(DoWhileStart DoWhileStart) {
        this.DoWhileStart=DoWhileStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public WhileStart getWhileStart() {
        return WhileStart;
    }

    public void setWhileStart(WhileStart WhileStart) {
        this.WhileStart=WhileStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public DoWhileEnd getDoWhileEnd() {
        return DoWhileEnd;
    }

    public void setDoWhileEnd(DoWhileEnd DoWhileEnd) {
        this.DoWhileEnd=DoWhileEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileStart!=null) DoWhileStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(WhileStart!=null) WhileStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
        if(DoWhileEnd!=null) DoWhileEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileStart!=null) DoWhileStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(WhileStart!=null) WhileStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
        if(DoWhileEnd!=null) DoWhileEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileStart!=null) DoWhileStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(WhileStart!=null) WhileStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        if(DoWhileEnd!=null) DoWhileEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStatementWithStmt(\n");

        if(DoWhileStart!=null)
            buffer.append(DoWhileStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileStart!=null)
            buffer.append(WhileStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoWhileEnd!=null)
            buffer.append(DoWhileEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStatementWithStmt]");
        return buffer.toString();
    }
}