package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			//Ovde treba promeniti oba naredna imena fajla koji se testira ukoliko se test menja
			String fileName = "test/test301.mj";
			String outputFileName="test/test301.obj";

			File sourceCode = new File(fileName);

			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			/* Formiranje AST */
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();
	        
	        Program prog = (Program)(s.value);
	        
			/* Ispis AST */
			log.info(prog.toString(""));
			log.info("=====================================================================");
			
			// Inicijalizacija tabele simbola 
			Tab.init();
			
			// Inicijalizacija bool tipa
			Struct boolType = new Struct(Struct.Bool);
			Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
			boolObj.setAdr(-1);
			boolObj.setLevel(-1);
			
			
			//Inicijalizacija set tipa
			Struct setType = new Struct(Struct.Interface);
			setType.setElementType(Tab.intType);
			Obj setObj = Tab.insert(Obj.Type, "set", setType);
			setObj.setAdr(-1);
			setObj.setLevel(-1);
			
			// Dodavanje predefinisanih metoda u tabelu simbola
			
			for(Obj fp: Tab.find("chr").getLocalSymbols()) {
				fp.setFpPos(1);
			}
			
			for(Obj fp: Tab.find("ord").getLocalSymbols()) {
				fp.setFpPos(1);
			}
			
			for(Obj fp: Tab.find("len").getLocalSymbols()) {
				fp.setFpPos(1);
			}
			
			//privremeni Obj koji sluzi za dodavanje add, addAll i union metoda u tabelu simbola
			Obj newMethod;
			// Konstruktor obj: int kind, String name, Struct type, int adr, int level
			
			// chr(e); e mora biti izraz tipa int. 
			// ord(c); c mora biti tipa char. 
			// len(a); a mora biti niz ili znakovni niz. 
			//add(a, b); a mora biti skup, b mora biti izraz tipa int. 
			//addAll(a, b); a mora biti skup, b mora biti niz celih brojeva. 
			
			
			// Dodavanje add metode u tabelu simbola
			// Metoda add sadrzi dva formalna parametra: set i int
			
			newMethod=new Obj(Obj.Meth, "add",Tab.noType,0,2);
	        Tab.currentScope.addToLocals(newMethod);
	        {
			Tab.openScope();
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "set", new Struct(Struct.Interface, Tab.intType), 0, 1));
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "i", Tab.intType, 0, 1));
			newMethod.setLocals(Tab.currentScope.getLocals());
			Tab.closeScope();
	        }
	        
			for(Obj fp: Tab.find("add").getLocalSymbols()) {
				fp.setFpPos(1);
			}
			
			// Dodavanje addAll metode u tabelu simbola
			// Metoda addAll sadrzi dva formalna parametra: set i niz intova
			newMethod=new Obj(Obj.Meth, "addAll",Tab.noType,0,2);
			Tab.currentScope.addToLocals(newMethod);
			{
			Tab.openScope();
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "set", new Struct(Struct.Interface, Tab.intType), 0, 1));
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "arr", new Struct(Struct.Array, Tab.intType), 0, 1));
			newMethod.setLocals(Tab.currentScope.getLocals());
			Tab.closeScope();
			}
			for(Obj fp: Tab.find("addAll").getLocalSymbols()) {
				fp.setFpPos(1);
			}
			
			// Dodavanje union metode u tabelu simbola
			// Metoda union sadrzi tri parametra tipa set
			newMethod=new Obj(Obj.Meth, "unionMeth",Tab.noType,0,2);
			Tab.currentScope.addToLocals(newMethod);
			{
			Tab.openScope();
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "set0", new Struct(Struct.Interface, Tab.intType), 0, 1));
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "set1", new Struct(Struct.Interface, Tab.intType), 0, 1));
			Tab.currentScope.addToLocals(new Obj(Obj.Var, "set2", new Struct(Struct.Interface, Tab.intType), 0, 1));
			newMethod.setLocals(Tab.currentScope.getLocals());
			Tab.closeScope();
			}
			for(Obj fp: Tab.find("union").getLocalSymbols()) {
				fp.setFpPos(1);
			}
			
			// Ukoliko bude potrebe za dodatnim metodama one treba da se deklarisu u kodu nadalje na isti nacin
			// kao prethodne tri metode
			
			

			
			
			// Semanticka analiza 
			SemanticPass sp = new SemanticPass();
			prog.traverseBottomUp(sp);
			
			// Ispis tabele simbola 
			log.info("=====================================================================");
			Tab.dump();
			
			if(!p.errorDetected && sp.passed()){
//			if(!p.errorDetected){
				log.info("Parsiranje uspesno zavrseno!");
				
				// Generisanje koda
				
//				File objFile = new File("test/program.obj");
				File objFile = new File(outputFileName);
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator= new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize=sp.nVars;
//				Code.mainPc=codeGenerator.getMainPc();
				Code.mainPc=0;
				Code.write(new FileOutputStream(objFile));
				
				log.info("Generisanje koda uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
