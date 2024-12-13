package rs.ac.bg.etf.pp1;

import org.apache.log4j.*;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor{

	Logger log=Logger.getLogger(getClass());
	int designatorCount=0;
	int varDeclCount=0;
	
    public void visit(Addop Addop) { 

    	designatorCount++;
    	log.info("Prepoznata Addop naredba.");
    	
    }
    
    public void visit(VarDecl varDecl) { 

    	varDeclCount++;
    	log.info("Prepoznata varDecl naredba.");
    	
    }

	
}
