package rs.ac.bg.etf.pp1;


import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPC;
	
	public int getMainPc() {
		return this.mainPC;
	}
	
	// Code generation
	
	//Bitne napomene: Posle svake instrukcije, ExpressionStack mora da bude prazan, jer ce u suprotnom neko djubre biti ostavljano na stacku
	
	@Override
	public void visit(MethodSignature methodSignature) {
		//Generalno za B fazu, za A dovoljan samo main method deo
		methodSignature.obj.setAdr(Code.pc);
		if(methodSignature.getMethName().equalsIgnoreCase("main")) {
			this.mainPC=Code.pc;
		}
		
		Code.put(Code.enter);
		Code.put(methodSignature.obj.getLevel()); //Broj formalnih parametara b1 (u level polju kod metoda)
		Code.put(methodSignature.obj.getLocalSymbols().size()); //Broj lokalnih i formalnih parametara, tj. ceo locals, b2
 	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		//Nakon zavrsetka metode, tj. u A nivou nakon zavrsetka main metode
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// Single Statements
	
	@Override
    public void visit(PrintStmt printStmt) {
		//, val, width Operacija ispisa    … width = pop(); writeInt(pop(), width); 
		
		Code.loadConst(0);
		if(printStmt.getExpr().struct==Tab.charType) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
	}
	
	@Override
    public void visit(PrintStmtTwo printStmt) {

		Code.loadConst(printStmt.getN2());
		if(printStmt.getExpr().struct==Tab.charType) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
		
	}
	
	@Override
    public void visit(ReadStmt readStmt) {

		if(readStmt.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
		Code.store(readStmt.getDesignator().obj);
		
	}
	
	@Override
    public void visit(ReturnStmt returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	// Designator assignop, inc, dec, TODO Setop, ActPars
	
    @Override
	public void visit(DesignatorAssignop desginatorAssignop) {
    	
    	Code.store(desginatorAssignop.getDesignator().obj);
    	
    }
    
    @Override
	public void visit(DesignatorInc designatorInc) {
    	
    	//Za nizove, jer bi aload pokupio adresu i ne bi se store izvrsio lepo
    	if(designatorInc.getDesignator().obj.getKind() == Obj.Elem) {
    		Code.put(Code.dup2);
    	}
    	
    	Code.load(designatorInc.getDesignator().obj);
    	Code.loadConst(1);
    	Code.put(Code.add);
    	Code.store(designatorInc.getDesignator().obj);
    	
    }
    
    @Override
	public void visit(DesignatorDec designatorDec) {
    	
    	//Za nizove, jer bi aload pokupio adresu i ne bi se store izvrsio lepo
    	if(designatorDec.getDesignator().obj.getKind() == Obj.Elem) {
    		Code.put(Code.dup2);
    	}
    	
    	Code.load(designatorDec.getDesignator().obj);
    	Code.loadConst(1);
    	Code.put(Code.sub);
    	Code.store(designatorDec.getDesignator().obj);
    }
    
    @Override
	public void visit(DesignatorSetop designatorSetop) {
    	//TODO
    }
    
    @Override
	public void visit(DesignatorActPars designatorActPars) {
    	//TODO
    }
	
	// Ucitavanje za nizove
	
    @Override
	public void visit(DesignatorArrayName designatorArrayName) {
    	
    	Code.load(designatorArrayName.obj);
    	
    }
	
	//Mulop i Addop operacije
	
	@Override
    public void visit(TermMulopList termMulopList) {
		if(termMulopList.getMulop() instanceof MulopMul) {
			Code.put(Code.mul);
		}
		else if(termMulopList.getMulop() instanceof MulopDiv) {
			Code.put(Code.div);
		}
		else if(termMulopList.getMulop() instanceof MulopMod) {
			Code.put(Code.rem);
		}
	}
	
	@Override
    public void visit(ExprAddopList exprAddopList) {
		
		if(exprAddopList.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		}
		else if(exprAddopList.getAddop() instanceof AddopMinus) {
			Code.put(Code.sub);
		}
	}
	
    @Override
	public void visit(UnaryMinusFactor unaryMinusFactor) {

    	Code.put(Code.neg);
    	
    }
	
    @Override
	public void visit(FactorNum factorNum) {

    	Code.loadConst(factorNum.getN1());
    	
    }
	
    @Override
	public void visit(FactorChar factorChar) {

    	Code.loadConst(factorChar.getC1());
    	
    }
    
    @Override
	public void visit(FactorBool factorBool) {

    	Code.loadConst(factorBool.getB1());
    	
    }
    
    @Override
	public void visit(FactorNewExpr factorNewExpr) {
    	
    	Code.put(Code.newarray);
    	if(factorNewExpr.getType().struct==Tab.intType) {
    		Code.put(1);
    	}
    	else if(factorNewExpr.getType().struct.getKind()==Struct.Interface) {
    		Code.put(1);
    	}
    	else {
    		Code.put(0);
    	}
    	
    	
    }
    
    @Override
	public void visit(FactorDes FactorDes) {

    	Code.load(FactorDes.getDesignator().obj);
    	
    }
}
