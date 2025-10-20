package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	private boolean errorDetected = false;
	Logger log=Logger.getLogger(getClass());
	private Obj currentProgram;
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Struct boolType=Tab.find("bool").getType();
	private Obj currentMethod =null;
	private boolean mainMethodExists = false;
	int nVars; // za cetvrtu fazu
	
	//Logs
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected  = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	//Semantic pass code
	
	@Override
	public void visit(ProgName programName) {
		currentProgram = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	
	@Override
	public void visit(Program program) {
		
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(currentProgram);
		Tab.closeScope();
		currentProgram=null;
		
//		if(!mainMethodExists || mainMethod.getLevel()>0)
		if(!mainMethodExists)
			report_error("Nema adekvatne main metode", program);
	}
	
	//Const declarations
	
	@Override
	public void visit(ConDecl conDecl) {
		
		Obj constObj = Tab.find(conDecl.getConstDecl());
		if(constObj != Tab.noObj) {
			report_error("Dvostruka definicija const: " + conDecl.getConstDecl(),conDecl);
		}
		else {
			if(!constantType.assignableTo(currentType)) {
				report_error("Neadekvatna dodela vrednosti konstante: " + conDecl.getConstDecl(),conDecl);
			}
			else {
				constObj = Tab.insert(Obj.Con, conDecl.getConstDecl(), currentType);
				constObj.setAdr(constant);
			}

		}

	}
	
	@Override
	public void visit(NumConstant constantNum) {
		constant=constantNum.getN1();
		constantType=Tab.intType;
	}
	@Override
	public void visit(CharConstant constantChr) {
		constant=constantChr.getC1();
		constantType=Tab.charType;
	}
	@Override
	public void visit(BoolConstant constantBool) {
		constant=constantBool.getB1();
		constantType=boolType;
	}
	
	//Variable declarations
	
	@Override
	public void visit(VarDeclarationVar varDeclarationVar) {
		Obj varObj=null;
		if(currentMethod==null) {
			varObj = Tab.find(varDeclarationVar.getVarDeclarationV());
		}
		else {
			varObj=Tab.currentScope().findSymbol(varDeclarationVar.getVarDeclarationV());
		}
			
			
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, varDeclarationVar.getVarDeclarationV(), currentType);
		}
		else {
			report_error("Dvostruka definicija varijable: " + varDeclarationVar.getVarDeclarationV(),varDeclarationVar);
		}
	}
	
	@Override
	public void visit(VarDeclarationArray varDeclArray) {
		
		Obj varObj=null;
		if(currentMethod==null) {
			varObj = Tab.find(varDeclArray.getVarDeclarationA());
		}
		else {
			varObj=Tab.currentScope().findSymbol(varDeclArray.getVarDeclarationA());
		}
		
		
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, varDeclArray.getVarDeclarationA(), new Struct(Struct.Array, currentType));
		}
		else {
			report_error("Dvostruka definicija varijable(niza): " + varDeclArray.getVarDeclarationA(),varDeclArray);
		}
		
	}
	
	
	//Method declarations
	
	@Override
	public void visit(MethodSignature methodSignature) {
		
    	if(methodSignature.getMethName().equalsIgnoreCase("main")) {
		mainMethodExists =true;
		//mainMethod=currentMethod;
	}
		methodSignature.obj = currentMethod = Tab.insert(Obj.Meth, methodSignature.getMethName(), Tab.noType);
		Tab.openScope();
		
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod=null;
		
	}
	
	
	@Override
	public void visit(Type type) {
		Obj typeObj= Tab.find(type.getTypeName());
		
//		report_info(""+typeObj.getKind() +" " + typeObj.getType() + " " + type.getTypeName() + " na pocetku type klase" , type);
		if(typeObj == Tab.noObj) {
			report_error("Nepostojeci tip podataka: " + type.getTypeName(),type);
			type.struct=currentType =Tab.noType;
		}
		else if(typeObj.getKind()!=Obj.Type) {
			report_error("Neadekvatan tip podataka: " + type.getTypeName(),type);
			type.struct=currentType =Tab.noType;
		}
		else {
			
			type.struct= currentType=typeObj.getType();
			
//			report_info("" + currentType.getKind() + " u type klasi", type);
		}
	}
	
	
	//Designator
    
    @Override
    public void visit(DesignatorArray designatorArray) {
    	
    	Obj arrObj = designatorArray.getDesignatorArrayName().obj;
    	if(arrObj == Tab.noObj) {
    		designatorArray.obj=Tab.noObj;
    	}
    	else if(!designatorArray.getExpr().struct.equals(Tab.intType)) {
    		report_error("Indeks niza nije tipa int", designatorArray);
    		designatorArray.obj=Tab.noObj;
    	}
    	else {
    		designatorArray.obj=new Obj(Obj.Elem, arrObj.getName()+"[#]",arrObj.getType().getElemType());
    	}
    	
    	
    }
    
    @Override
	public void visit(DesignatorVar designatorVar) {

    	Obj varObj = Tab.find(designatorVar.getD());
//    	report_info("Designator Var vrednost: " + varObj.getKind(), designatorVar);
    	if(varObj == Tab.noObj) {
    		report_error("Pristup nedefinisanoj promenljivoj: "+ designatorVar.getD(), designatorVar);
    		designatorVar.obj=Tab.noObj;
    	}
    	else if(varObj.getKind()!= Obj.Var && varObj.getKind()!=Obj.Con && varObj.getKind()!=Obj.Elem && varObj.getKind() != Obj.Meth ) {
    		report_error("Neadekvatan tip promenljive: "+ designatorVar.getD(), designatorVar);
    		designatorVar.obj=Tab.noObj;
    	}
    	else {
    		designatorVar.obj=varObj;
//    		report_info("Pristup promenljivoj: "+ designatorVar.obj.getKind() + " " + 
//    				designatorVar.obj.getName() + " " + designatorVar.obj.getType() + " " +
//    				designatorVar.obj.getAdr() + " " + designatorVar.obj.getLevel() + " " +
//    				designatorVar.obj.getFpPos() + " " +designatorVar.obj.getLocalSymbols() , designatorVar);
    	}
    }
    
    
    @Override
	public void visit(DesignatorArrayName designatorArrayName) {

    	Obj arrObj = Tab.find(designatorArrayName.getD());
//    	report_info("Designator Array vrednost: " + arrObj.getKind(), designatorArrayName);
    	if(arrObj == Tab.noObj) {
    		report_error("Pristup nedefinisanoj promenljivoj: "+ designatorArrayName.getD(), designatorArrayName);
    		designatorArrayName.obj=Tab.noObj;
    	}
    	else if(arrObj.getKind()!= Obj.Var || arrObj.getType().getKind()!= Struct.Array) {
    		report_error("Neadekvatan tip promenljive u nizu "+ designatorArrayName.getD(), designatorArrayName);
    		designatorArrayName.obj=Tab.noObj;
    	}
    	else {
    		designatorArrayName.obj=arrObj;
    	}
    }
	
	//Factor
	
	@Override
	public void visit(UnaryMinusFactor unaryMinusFactor) {
		
		if(unaryMinusFactor.getFactor().struct.equals(Tab.intType)) {
			unaryMinusFactor.struct=Tab.intType;
		}
		else {
			report_error("Negacija vrednosti koja nije broj ", unaryMinusFactor);
			unaryMinusFactor.struct=Tab.noType;
		}
	}
	
    @Override
	public void visit(UnaryFactor unaryFactor) {

    	unaryFactor.struct=unaryFactor.getFactor().struct;
    }
    
    @Override
	public void visit(FactorNum factorNum) {

    	factorNum.struct=Tab.intType;
    }
    
    @Override
	public void visit(FactorChar factorChar) {

    	factorChar.struct=Tab.charType;
    }

    
    @Override
	public void visit(FactorBool factorBool) {

    	factorBool.struct=boolType;
    }
    
    @Override
	public void visit(FactorNewExpr factorNewExpr) {

    	if(!factorNewExpr.getExpr().struct.equals(Tab.intType)) {
    		report_error("Velicina niza nije promenljiva tipa int(Factor new expr) "+ factorNewExpr, factorNewExpr);
    		factorNewExpr.struct=Tab.noType;
    	}
    	else if(factorNewExpr.getType().getTypeName().equals("set")) {
    		factorNewExpr.struct=new Struct(Struct.Interface);
    	}
    	else {
    		factorNewExpr.struct=new Struct(Struct.Array, currentType);
    	}
    }
    
    @Override
    public void visit(FactorExpr factorExpr) {
    	factorExpr.struct=factorExpr.getExpr().struct;
    	
    }
    
    @Override
	public void visit(FactorDes factorDes) {

    	factorDes.struct=factorDes.getDesignator().obj.getType();
    }
    
    
    @Override
    public void visit(FactorMeth factorMeth) {
    	
    	factorMeth.struct=factorMeth.getDesignator().obj.getType();
//    	report_info(""+factorMeth.struct.getKind(), factorMeth);
    	
    }

    //Expr, identicna situacija kao kod Term, bukvalno prepisan kod
    
    
    @Override
    public void visit(Expr expr) {
    	//Prosledjivanje Expr struct tipa ka gore
    	expr.struct=expr.getExprList().struct;
    }
    
    @Override
    public void visit(ExprAddopList exprAddopList) {
    	
    	//Uporedjivanje da li su sa obe strane Addop operacije int vrednosti i prosledjivanje struct tipa ka gore
    	
    	Struct right=exprAddopList.getTerm().struct;
    	Struct left = exprAddopList.getExprList().struct;
    	
    	if(right.equals(Tab.intType) && left.equals(Tab.intType)) {
    		exprAddopList.struct=Tab.intType;
    	}
    	else {
    		report_error("Sabiranje vrednosti koje nisu tipa int", exprAddopList);
    		exprAddopList.struct=Tab.noType;
    	}
    }
    
    @Override
    public void visit(ExprTerm exprTerm) {
    	//Proveravanje tipa podataka najlevljeg sina i prosledjivanje njegovog struct tipa ka gore
    	exprTerm.struct=exprTerm.getTerm().struct;
    	
    }
    
    //Term
    
    @Override
    public void visit(Term term) {
    	
    	//Prosledjivanje Term struct tipa ka gore, tojest ka Addop operacijama i Expr
    	term.struct=term.getTermList().struct;
    	
    }
    
    @Override
    public void visit(TermMulopList termMulopList) {
    	
    	//Uporedjivanje da li su sa obe strane Mulop operacije int vrednosti i prosledjivanje struct tipa ka gore
    	
    	Struct right = termMulopList.getUnary().struct;
    	Struct left= termMulopList.getTermList().struct;
    	
    	if(right.equals(Tab.intType) && left.equals(Tab.intType)) {
    		termMulopList.struct=Tab.intType;
    	}
    	else {
    		report_error("Mnozenje vrednosti koje nisu tipa int", termMulopList);
    		termMulopList.struct=Tab.noType;
    	}
    	
    }
    
    @Override
    public void visit(TermFactor termFactor) {
    	
    	//Proveravanje tipa podataka najlevljeg sina i prosledjivanje njegovog struct tipa ka gore
    	termFactor.struct=termFactor.getUnary().struct;
    	
    }
    
    
    //Designator statements
    
    @Override
    public void visit(DesignatorAssignop designatorAssignop) {
    	
    	int desKind=designatorAssignop.getDesignator().obj.getKind();
//		report_info("Designator assignop klasa " + designatorAssignop.getDesignator().obj.getType().getKind() + " " + designatorAssignop.getExpr().struct.getKind() ,designatorAssignop);

    	if(desKind!=Obj.Var && desKind !=Obj.Elem) {
    		report_error("Dodela vrednost neadekvatnoj promenljivoj: "
    				+ designatorAssignop.getDesignator().obj.getName(), designatorAssignop);
    	}
    	else if(designatorAssignop.getDesignator().obj.getType().getKind()==Struct.Interface && designatorAssignop.getExpr().struct.getKind()==Struct.Interface) {
    		return;
    	}
    	else if(!designatorAssignop.getExpr().struct.assignableTo(designatorAssignop.getDesignator().obj.getType())) {
    		report_error("Dodela neadekvatne vrednosti u promenljivu: "
    				+ designatorAssignop.getDesignator().obj.getName(), designatorAssignop);
    	}
    	
    }
    
    @Override
    public void visit(DesignatorInc designatorInc) {
    	
    	int desKind=designatorInc.getDesignator().obj.getKind();
    	
    	if(desKind!=Obj.Var && desKind !=Obj.Elem) {
    		report_error("Dodela vrednost neadekvatnoj promenljivoj u ++ naredbi: "
    				+ designatorInc.getDesignator().obj.getName(), designatorInc);
    	}
    	else if(!designatorInc.getDesignator().obj.getType().equals(Tab.intType)) {
    		report_error("Inkrement promenljive koja nije tipa int: "
    				+ designatorInc.getDesignator().obj.getName(), designatorInc);
    	}
    	
    }

    
    @Override
    public void visit(DesignatorDec designatorDec) {
    	
    	int desKind=designatorDec.getDesignator().obj.getKind();
    	
    	if(desKind!=Obj.Var && desKind !=Obj.Elem) {
    		report_error("Dodela vrednost neadekvatnoj promenljivoj u -- naredbi: "
    				+ designatorDec.getDesignator().obj.getName(), designatorDec);
    	}
    	else if(!designatorDec.getDesignator().obj.getType().equals(Tab.intType)) {
    		report_error("Dekrement promenljive koja nije tipa int: "
    				+ designatorDec.getDesignator().obj.getName(), designatorDec);
    	}
    }
    
    @Override
    public void visit(DesignatorSetop designatorSetop) {
    	
    	
    	if(designatorSetop.getDesignator().obj.getType().getKind()!=Struct.Interface
    			||
    			designatorSetop.getDesignator1().obj.getType().getKind()!=Struct.Interface
    			||
    			designatorSetop.getDesignator2().obj.getType().getKind()!=Struct.Interface) {
    		report_error("Setop operacija nad objektu koji nije tipa set ",designatorSetop);
    	}
    	
    }
    
    @Override
    public void visit(DesignatorActPars designatorActPars) {
    	//TODO
    	report_error("TODO", designatorActPars);
    }
    
    //Single Statements
	
    @Override
    public void visit(ReadStmt readStmt) {
    	
    	Struct type = readStmt.getDesignator().obj.getType();
    	int kind=readStmt.getDesignator().obj.getKind();
    	
    	
    	if(kind!=Obj.Var && kind !=Obj.Elem) {
    		report_error("Operacija read nad neadekvatnom promenljivom u naredbi: "
    				+ readStmt.getDesignator().obj.getName(), readStmt);
    	}
    	else if(!type.equals(Tab.intType) && !type.equals(boolType) && !type.equals(Tab.charType)) {
    		report_error("Read operacija nad tipom koji nije int, char ili bool: "
    				+ readStmt.getDesignator().obj.getName(), readStmt);
    	}
    	
    }
    
    @Override
    public void visit(PrintStmt printStmt) {
    	Struct type = printStmt.getExpr().struct;
    	
    	if(!type.equals(Tab.intType) && !type.equals(boolType) && !type.equals(Tab.charType) && type.getKind()!= Struct.Interface) {
    		report_error("Print operacija nad tipom koji nije int, char ili bool ", printStmt);
    	}
    }
    
    @Override
    public void visit(PrintStmtTwo printStmt) {
    	Struct type = printStmt.getExpr().struct;
    	
    	if(!type.equals(Tab.intType) && !type.equals(boolType) && !type.equals(Tab.charType) && type.getKind()!= Struct.Interface) {
    		report_error("Print operacija nad tipom koji nije int, char ili bool ", printStmt);
    	}
    }
    
}
