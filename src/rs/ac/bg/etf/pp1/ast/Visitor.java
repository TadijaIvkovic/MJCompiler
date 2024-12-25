// generated with ast extension for cup
// version 0.8
// 25/11/2024 17:4:4


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Unmatched Unmatched);
    public void visit(DeclarationList DeclarationList);
    public void visit(ConditionWithStatement ConditionWithStatement);
    public void visit(Mulop Mulop);
    public void visit(ConstantList ConstantList);
    public void visit(Matched Matched);
    public void visit(Relop Relop);
    public void visit(Constants Constants);
    public void visit(DesignatorElem DesignatorElem);
    public void visit(Unary Unary);
    public void visit(MethodSignature MethodSignature);
    public void visit(StatementList StatementList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(Brackets Brackets);
    public void visit(VarDeclList VarDeclList);
    public void visit(FormalParamList FormalParamList);
    public void visit(VarDeclarationList VarDeclarationList);
    public void visit(Expr Expr);
    public void visit(ActPars ActPars);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Statement Statement);
    public void visit(ActParamList ActParamList);
    public void visit(CondFact CondFact);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormPars FormPars);
    public void visit(Setop Setop);
    public void visit(MulopMod MulopMod);
    public void visit(MulopDiv MulopDiv);
    public void visit(MulopMul MulopMul);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(RelopMore RelopMore);
    public void visit(RelopLess RelopLess);
    public void visit(RelopLArrow RelopLArrow);
    public void visit(RelopRArrow RelopRArrow);
    public void visit(RelopNotEqual RelopNotEqual);
    public void visit(RelopEqual RelopEqual);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(DesignatorName DesignatorName);
    public void visit(NoDesElem NoDesElem);
    public void visit(DesElem DesElem);
    public void visit(DesignatorIdent DesignatorIdent);
    public void visit(DesignatorEl DesignatorEl);
    public void visit(FactorDesActPars FactorDesActPars);
    public void visit(FactorDes FactorDes);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorNewActPars FactorNewActPars);
    public void visit(FactorNewExpr FactorNewExpr);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(UnaryMinusFactor UnaryMinusFactor);
    public void visit(UnaryFactor UnaryFactor);
    public void visit(TermFactor TermFactor);
    public void visit(TermMulop TermMulop);
    public void visit(ExprMap ExprMap);
    public void visit(ExprTerm ExprTerm);
    public void visit(ExprAddop ExprAddop);
    public void visit(CondExpr CondExpr);
    public void visit(Cond Cond);
    public void visit(ConditionFact ConditionFact);
    public void visit(CondTermList CondTermList);
    public void visit(ConditionTerm ConditionTerm);
    public void visit(ConditionList ConditionList);
    public void visit(ActParamsExpr ActParamsExpr);
    public void visit(ActParamsList ActParamsList);
    public void visit(NoActParams NoActParams);
    public void visit(ActParams ActParams);
    public void visit(DesignatorActPars DesignatorActPars);
    public void visit(DesignatorSetop DesignatorSetop);
    public void visit(DesignatorDec DesignatorDec);
    public void visit(DesignatorInc DesignatorInc);
    public void visit(DesginatorAssignop DesginatorAssignop);
    public void visit(WhileStart WhileStart);
    public void visit(DoWhileEnd DoWhileEnd);
    public void visit(DoWhileStart DoWhileStart);
    public void visit(WithoutCond WithoutCond);
    public void visit(CondSolo CondSolo);
    public void visit(CondWithStatement CondWithStatement);
    public void visit(BracedStmt BracedStmt);
    public void visit(IfElseStmt IfElseStmt);
    public void visit(DoWhileStatementWithStmt DoWhileStatementWithStmt);
    public void visit(ReturnEmptyStmt ReturnEmptyStmt);
    public void visit(ReturnStmt ReturnStmt);
    public void visit(ContinueStmt ContinueStmt);
    public void visit(BreakStmt BreakStmt);
    public void visit(DesStmt DesStmt);
    public void visit(ReadStmt ReadStmt);
    public void visit(PrintStmtTwo PrintStmtTwo);
    public void visit(MatchedDerived1 MatchedDerived1);
    public void visit(PrintStmt PrintStmt);
    public void visit(UnmatchedIfElse UnmatchedIfElse);
    public void visit(UnmatchedIf UnmatchedIf);
    public void visit(UnmatchedStmt UnmatchedStmt);
    public void visit(MatchedStmt MatchedStmt);
    public void visit(NoStmtList NoStmtList);
    public void visit(StmtList StmtList);
    public void visit(Statements Statements);
    public void visit(Type Type);
    public void visit(NoBracket NoBracket);
    public void visit(Bracket Bracket);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(FormalParsDecl FormalParsDecl);
    public void visit(FormalParsList FormalParsList);
    public void visit(NoFormalPars NoFormalPars);
    public void visit(FormalPars FormalPars);
    public void visit(NoMethTypeName NoMethTypeName);
    public void visit(MethTypeName MethTypeName);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethDeclList NoMethDeclList);
    public void visit(MethDeclList MethDeclList);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(VarDeclarat VarDeclarat);
    public void visit(VarDeclaratList VarDeclaratList);
    public void visit(VarDecl VarDecl);
    public void visit(NoVarDeclarList NoVarDeclarList);
    public void visit(VarDeclarList VarDeclarList);
    public void visit(BoolConstant BoolConstant);
    public void visit(CharConstant CharConstant);
    public void visit(NumConstant NumConstant);
    public void visit(NoConstList NoConstList);
    public void visit(ConstList ConstList);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(NoDecLists NoDecLists);
    public void visit(VarDecLists VarDecLists);
    public void visit(ConstDecLists ConstDecLists);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
