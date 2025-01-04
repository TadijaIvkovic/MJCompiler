package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

	private boolean errorDetected = false;
	Logger log=Logger.getLogger(getClass());
	int designatorCount=0;
	int varDeclCount=0;
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Struct boolType=Tab.find("bool").getType();
	private Obj currentMethod =null;
	private boolean mainMethodExists = false;
	private Obj mainMethod=null;

	
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
	
	@Override
    public void visit(Addop Addop) { 

    	designatorCount++;
    	log.info("Prepoznata Addop naredba.");
    	
    }
    
    @Override
    public void visit(VarDecl varDecl) { 

    	varDeclCount++;
    	log.info("Prepoznata varDecl naredba.");
    	
    }

    @Override
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
    @Override
	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		
		if(!mainMethodExists || mainMethod.getLevel()>0)
			report_error("Nema adekvatne main metode", program);

	}
    
    @Override
	public void visit(ConstDeclList constDeclList) {
    	
    	Obj constObj = Tab.find(constDeclList.getConstDecl());
    	if(constObj != Tab.noObj) {
			report_error("Dvostruka definicija konstante: "+ constDeclList.getConstDecl(), constDeclList);

    	}
    	else {
    		
    		if(constantType.assignableTo(currentType)) {
        		constObj = Tab.insert(Obj.Con, constDeclList.getConstDecl(), currentType);
        		constObj.setAdr(constant);
    		}
    		else {
    			report_error("neadekvatna dodela konstanti: "+ constDeclList.getConstDecl(), constDeclList);
    		}

    	}

	}
    
    @Override
	public void visit(ConstList constList) {
    	
    	Obj constObj = Tab.find(constList.getConstDecl2());
    	if(constObj != Tab.noObj) {
			report_error("Dvostruka definicija konstante: "+ constList.getConstDecl2(), constList);

    	}
    	else {
    	
    		if(constantType.assignableTo(currentType)) {
    	    	constObj =Tab.insert(Obj.Con, constList.getConstDecl2(), currentType);
    	    	constObj.setAdr(constant);
    		}
    		else {
    			report_error("Neadekvatna dodela konstanti: "+ constList.getConstDecl2(), constList);
    		}
    		

    	}
	}
    
    @Override
	public void visit(NumConstant numConstant) {
		constant = numConstant.getN1();
		constantType = Tab.intType;
	}
    
    @Override
	public void visit(CharConstant charConstant) {
		constant = charConstant.getC1();
		constantType = Tab.charType;
	}
    
    @Override
	public void visit(BoolConstant boolConstant) {
		constant = boolConstant.getB1();
		constantType = boolType;
	}
    
    /* Var declarations: */
    
    @Override
	public void visit(VarDeclarationSingle varDeclarationSingle) {
    	Obj varObj = null;
    	if(currentMethod==null)
    		varObj = Tab.find(varDeclarationSingle.getVarDeclaration());
    	else
    		varObj = Tab.currentScope().findSymbol(varDeclarationSingle.getVarDeclaration());
    	
    	
    	if(varObj == null ||varObj == Tab.noObj) {
    		varObj = Tab.insert(Obj.Var, varDeclarationSingle.getVarDeclaration(), currentType);
    	}
    	else {
			report_error("Dvostruka definicija promenljive: "+ varDeclarationSingle.getVarDeclaration(), varDeclarationSingle);

    	}

	}
    
    @Override
	public void visit(VarDeclarationArray varDeclarationArray) {
    	
    	Obj varObj = null;
    	if(currentMethod==null)
    		varObj = Tab.find(varDeclarationArray.getVarDeclaration());
    	else
    		varObj = Tab.currentScope().findSymbol(varDeclarationArray.getVarDeclaration());
    	
    	if(varObj == null ||varObj == Tab.noObj) {
    		varObj = Tab.insert(Obj.Var, varDeclarationArray.getVarDeclaration(), new Struct(Struct.Array, currentType));
    	}
    	else {
			report_error("Dvostruka definicija promenljive: "+ varDeclarationArray.getVarDeclaration(), varDeclarationArray);

    	}
	}
    
    
    /* Method declarations  */
    
    @Override
    public void visit(MethodDecl methodDecl) {
    	
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	currentMethod=null;
    	
    }
    
    @Override
    public void visit(NoMethTypeName noMethTypeName) {
    	

    	
    	currentMethod=Tab.insert(Obj.Meth, noMethTypeName.getMethName(), Tab.noType);
    	Tab.openScope();
    	if(noMethTypeName.getMethName().equalsIgnoreCase("main")) {
    		mainMethodExists =true;
    		mainMethod=currentMethod;
    	}
    	
    }
    
    @Override
    public void visit(MethTypeName methTypeName) {
    	
    	currentMethod=Tab.insert(Obj.Meth, methTypeName.getMethName(), currentType);

    	Tab.openScope();
    	
    }
    
    /* FormalParameters declarations: */
    
    @Override
    public void visit(FormalParamSingle formalParamSingle) {
    	Obj fparObj = null;
    	if(currentMethod==null)
			report_error("Formalni parametar javljen u metodi koja je jednaka null. [formalParamSingle]", formalParamSingle);
    	else
    		fparObj = Tab.currentScope().findSymbol(formalParamSingle.getFparDeclaration());
    	
    	
    	if(fparObj == null ||fparObj == Tab.noObj) {
    		fparObj = Tab.insert(Obj.Var, formalParamSingle.getFparDeclaration(), currentType);
    		fparObj.setFpPos(1);
    		currentMethod.setLevel(currentMethod.getLevel()+1);
    	}
    	else {
			report_error("Dvostruka definicija promenljive: "+ formalParamSingle.getFparDeclaration(), formalParamSingle);

    	}
    }
    
    @Override
    public void visit(FormalParamArray formalParamArray) {
    	Obj fparObj = null;
    	if(currentMethod==null)
			report_error("Formalni parametar javljen u metodi koja je jednaka null. [formalParamArray]", formalParamArray);
    	else
    		fparObj = Tab.currentScope().findSymbol(formalParamArray.getFparDeclaration());
    	
    	if(fparObj == null ||fparObj == Tab.noObj) {
    		fparObj = Tab.insert(Obj.Var, formalParamArray.getFparDeclaration(), new Struct(Struct.Array, currentType));
    		fparObj.setFpPos(1);
    		currentMethod.setLevel(currentMethod.getLevel()+1);
    	}
    	else {
			report_error("Dvostruka definicija promenljive: "+ formalParamArray.getFparDeclaration(), formalParamArray);

    	}
    }
    
    @Override
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getTypeName());
		if (typeObj==Tab.noObj) {
			report_error("Nepostojeci tip podataka: " + type.getTypeName(),type);
			currentType =Tab.noType;
		}
		else if(typeObj.getKind()!= Obj.Type) {
			report_error("Neadekvatan tip podataka: "+ type.getTypeName(), type);
			currentType=Tab.noType;
		}
		else
			currentType=typeObj.getType();
    }
    
    /* Kontekstni uslovi */
    
    @Override
	public void visit(UnaryMinusFactor unaryMinusFactor) {
    	//provera uslova za dozvoljenost negiranja
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
	public void visit(FactorBool factorBool) {

    	factorBool.struct=boolType;

    	
    }
    
    @Override
	public void visit(FactorChar factorChar) {

    	factorChar.struct=Tab.charType;
//    	factorChar.struct=boolType;

    	
    }

    @Override
	public void visit(FactorNum factorNum) {

    	factorNum.struct=Tab.intType;
    	
    }
    
}
