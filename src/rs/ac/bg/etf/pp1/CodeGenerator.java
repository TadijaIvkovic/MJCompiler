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
	
	public CodeGenerator() {
		predeclaredMethodInit();
	}
	
	private void predeclaredMethodInit() {
        //len
        
		Obj obj = Tab.find("len");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel()); //
		Code.put(obj.getLocalSymbols().size()); //1 
//        Code.put(1);
//        Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// chr

		obj = Tab.find("chr");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel()); //1
		Code.put(obj.getLocalSymbols().size()); //1
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// ord

		obj = Tab.find("ord");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel()); //1
		Code.put(obj.getLocalSymbols().size()); //1
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
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
		//TODO treba implementirati print za set
		//loop koji bi u svakoj instanci povecavao vrednost indeksa i tako ispisivao sve dok ne zavrsi
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
		//TODO treba implementirati print za set
		//loop koji bi u svakoj instanci povecavao vrednost indeksa i tako ispisivao sve dok ne zavrsi
		
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
    	
    	//Dovoljno i za array i za var objekte
    	Code.store(desginatorAssignop.getDesignator().obj);
    	
    }
    
    @Override
	public void visit(DesignatorInc designatorInc) {
    	
    	//Za nizove, jer bi aload pokupio adresu i ne bi se store izvrsio lepo
    	if(designatorInc.getDesignator().obj.getKind() == Obj.Elem|| designatorInc.getDesignator().obj.getType().getKind()==Struct.Interface) {
    		Code.put(Code.dup2);
    	}
    	
    	// Stavlja designator na stack zajedno sa jedinicom, sabira ih i store-uje nazad podatak
    	Code.load(designatorInc.getDesignator().obj);
    	Code.loadConst(1);
    	Code.put(Code.add);
    	Code.store(designatorInc.getDesignator().obj);
    	
    }
    
    @Override
	public void visit(DesignatorDec designatorDec) {
    	
    	//Za nizove, jer bi aload pokupio adresu i ne bi se store izvrsio lepo
    	if(designatorDec.getDesignator().obj.getKind() == Obj.Elem || designatorDec.getDesignator().obj.getType().getKind()==Struct.Interface) {
    		Code.put(Code.dup2);
    	}
    	
    	// Stavlja designator na stack zajedno sa jedinicom, oduzima ih i store-uje nazad podatak
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

		int adr = designatorActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(adr);
    	
    }
	
	// Ucitavanje za nizove
	
    @Override
	public void visit(DesignatorArrayName designatorArrayName) {
    	
    	Code.load(designatorArrayName.obj); // Moguce da ovde Stack izgleda ovako: ..index, adr 
    	// A da pravilno treba da bude ...adr, index
    	
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
    	
    	
    	if(factorNewExpr.getType().struct==Tab.intType) {
    		//Za int nizove
    		Code.put(Code.newarray);
    		Code.put(1);
    	}
    	else if(factorNewExpr.getType().struct.getKind()==Struct.Interface) {
    		
    		//Uvelicavamo niz za jedan, da bi prvi element bio count za set tip
    		Code.loadConst(1);
    		Code.put(Code.add);
    		
    		//Za samu array fju
    		Code.put(Code.newarray);
    		Code.put(1); 
    		
    		//Inicijalizacija countera na nultom indeksu (set[0]=0)
    		Code.put(Code.dup); // Trenutno stack : addr, addr
    		Code.loadConst(0);
    		Code.loadConst(0);
    		Code.put(Code.astore); // Potrebni adr, index, val za upisivanje u prvi element niza
    		
    	}
    	else {
    		//Za char nizove
    		Code.put(Code.newarray);
    		Code.put(0);
    	}
    	
    	
    }
    
    @Override
	public void visit(FactorDes factorDes) {

    	Code.load(factorDes.getDesignator().obj);
    }
    
    
    @Override
    public void visit(FactorMeth factorMeth) {

		int adr = factorMeth.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(adr);
    	
    }
    
    
    
    
}
