package rs.ac.bg.etf.pp1;

import java.util.List;
import java.util.ArrayList;

import rs.ac.bg.etf.pp1.ast.*;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ActParsPass extends VisitorAdaptor{
	
	List<Struct> actPars = new ArrayList<>();
	
	@Override
	public void visit(ActPar actPar) {
		actPars.add(actPar.getExpr().struct);
	}

}
