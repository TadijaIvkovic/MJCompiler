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
		Code.put(obj.getLevel()); // 1
		Code.put(obj.getLocalSymbols().size()); // 1 
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// chr

		obj = Tab.find("chr");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel()); // 1
		Code.put(obj.getLocalSymbols().size()); // 1
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// ord

		obj = Tab.find("ord");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel()); // 1
		Code.put(obj.getLocalSymbols().size()); // 1
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		// add, TODO mozda treba malo dorada

		obj = Tab.find("add");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
//		Code.put(obj.getLevel()); // Broj formalnih parametara b1 (u level polju kod metoda)
//		Code.put(obj.getLocalSymbols().size()); //Broj lokalnih i formalnih parametara, tj. ceo locals, b2
		Code.put(2);
		Code.put(4);
		add();
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// Code generation
	//Bitne napomene: Posle svake instrukcije, ExpressionStack mora da bude prazan, jer ce u suprotnom neko djubre biti ostavljano na stacku
	
	
	// Predeclared methods
	
	
	// Sadrzaj: pocetna adresa seta, broj koji treba dodati u set
	public void add() {
		/*
		int a=3; // 
		int s[] = new int[5];
		int i=0;
		int width= s[0];
		int comp;
		while(i<width) {
			comp=s[i];
			if(comp==a)
				break;
				jmp vecunizu;
			else
				i++;
		}
		i=s[0]++;
		s[i]=a;
		
		vecunizu: 
			sredjivanje stacka i vracanje nazad;
			*/
		
		// Promenljive
		// 0 ->set 
		// 1 ->numberToAdd
		// 2 -> count tj. n
		// 3 -> counter tj. i
		
		Code.put(Code.load_n); // stack: set
		Code.loadConst(0);	// stack: set, 0 ( aload:: stack: adr, index  -> stack: arr[index])
		Code.put(Code.aload); // n = count = set[0], ako ima 4 elementa, count ce biti 5
		
		Code.put(Code.store_2); // Cuvanje count tj. n promenljive (LOKALNA PROMENLJIVA 2)
		Code.put(Code.load_2); // stack: n
		
		//Provera da set slucajno nije vec pun, ako jeste ne radimo nista
		Code.put(Code.load_n); // stack: n, set
		Code.put(Code.arraylength); // stack: n, len[set], ova duzina je zapravo veca za 1 od prave
		Code.loadConst(1); // stack: n, len[set], 1
		Code.put(Code.sub); // stack: n, len[set]-1 , prava duzina seta
		
		Code.putFalseJump(Code.lt, 0); // Skacemo na kraj metode jer je set pun i nov element ne moze da se doda
		int patchAddrIfSetFull=Code.pc-2; //Stack: ..
		
		//ubacujemo promenljivu i, incijalno 1 jer krecemo od prvog indeksa seta
		Code.loadConst(1); 
		Code.put(Code.store_3); // Cuvanje i promenljive, tojest brojaca (LOKALNA PROMENLJIVA 3)
		
		//Provera da li ubacujemo prvi element seta na pocetak seta:
		Code.put(Code.load_2);
		Code.loadConst(0);
		Code.putFalseJump(Code.ne,0);
		int patchAddrFirstElement=Code.pc-2;
		
		//While start
		int whileStart=Code.pc;
		
		Code.put(Code.load_3); // stack: i
		Code.put(Code.load_2); // stack: i, n

		
		Code.putFalseJump(Code.le, 0); // Ako je i > n izlazimo iz while petlje da ubacimo element
		int patchAddrWhileEnd=Code.pc-2;
		
		// While petlja
		// Treba da se proveri da li se na poziciji set[i] nalazi 
		
		//Poredjenje set[i] == numberToAdd
		Code.put(Code.load_n); // Stavljanje set[i] na stack
		Code.put(Code.load_3);
		Code.put(Code.aload); // stack: set[i]
		Code.put(Code.load_1); // stack: set[i], numberToAdd
		Code.putFalseJump(Code.ne, 0); // jump ako set[i] == numberToAdd
		int numberFound=Code.pc-2; // Izlazimo iz petlje ako
		
		//Inkrementiranje i++;
		Code.put(Code.load_3);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store_3);// Lokalna promenljiva 3: i tj. counter postaje i+1
		
		Code.putJump(whileStart);
		
		
		//Ubacujemo prvi element u set, treba povecati prvi element seta na jedan i postaviti drugi element 
		Code.fixup(patchAddrFirstElement);
		
		//Ubacujemo element na pravu poziciju ovde TODO, moguce da ce morati da se promeni pozicija ali videcemo
		Code.fixup(patchAddrWhileEnd);
		
		// Dodavanje elementa u set na poziciju
		//U ovom momentu inkrementiramo set[0] i ubacujemo novi element na set[i]
		
		Code.put(Code.load_n);// stack: set
		Code.put(Code.load_3); // stack: set, i 
		Code.put(Code.load_1); // stack: set, i, numberToadd
		Code.put(Code.astore); // stack: .... , na poziciji set[i]=numberToAdd;
		
		Code.put(Code.load_n);// stack: set
		Code.loadConst(0); // stack: set, 0 
		Code.put(Code.load_2); // stack: set, 0, n
		Code.loadConst(1); // stack: set, 0, n, 1
		Code.put(Code.add); // stack: set, 0, n+1;
		Code.put(Code.astore);
		
		
		
		//Nadjen je broj tako da ne treba ubaciti numberToAdd u set
		Code.fixup(numberFound);
		
		// Set je pun pa se ne moze dodati, treba samo izaci iz metode
		Code.fixup(patchAddrIfSetFull);
	}
	
	public void addAll() {
		
	}
	
	public void unionMethod() {
		
	}
	
	
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
		if(printStmt.getExpr().struct.getKind()!=Struct.Interface) {
			Code.loadConst(0);
			if(printStmt.getExpr().struct==Tab.charType) {
				Code.put(Code.bprint);
			}
			else {
				Code.put(Code.print);
			}
		}
		else {
			// 0 -> set
			// 1 -> width print fje, u ovom slucaju 0
			// 2 -> count
			// 3 -> i(counter)
			
			// Inicijalizacija pocetnih promenljivih
			Code.put(Code.store_n);
//			Code.loadConst(0);
//			Code.put(Code.store_1);
			
			Code.put(Code.load_n);
			Code.loadConst(0);
			Code.put(Code.aload);
			Code.put(Code.store_2);
			
			Code.loadConst(1);
			Code.put(Code.store_3);
			
			
			// While petlja pocetak
			int whileStart=Code.pc;
			
			Code.put(Code.load_3); // stack: i
			Code.put(Code.load_2); // stack: i, n

			
			Code.putFalseJump(Code.le, 0); // Ako je i > n izlazimo iz while petlje da ubacimo element
			int patchAddrWhileEnd=Code.pc-2;
			
			//Printanje set[i]
			Code.put(Code.load_n);
			Code.put(Code.load_3);
			Code.put(Code.aload);
			Code.loadConst(0); // stack: set[i], width
			Code.put(Code.print);
			
			Code.loadConst(' ');
			Code.loadConst(0);
			Code.put(Code.bprint);
			
			// Inkrementiranje brojaca
			Code.put(Code.load_3);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.store_3);
			
			Code.putJump(whileStart);
			
			
			
			Code.fixup(patchAddrWhileEnd);
			

		}
	}
	
	@Override
    public void visit(PrintStmtTwo printStmt) {

		if(printStmt.getExpr().struct.getKind()!=Struct.Interface) {
			Code.loadConst(printStmt.getN2());
			if(printStmt.getExpr().struct==Tab.charType) {
				Code.put(Code.bprint);
			}
			else {
				Code.put(Code.print);
			}
		}
		else {
			// 0 -> set
			// 1 -> width print fje.
			// 2 -> count
			// 3 -> i(counter)
			
			// Inicijalizacija pocetnih promenljivih
			Code.put(Code.store_n);
			Code.loadConst(printStmt.getN2());
			Code.put(Code.store_1);
			
			Code.put(Code.load_n);
			Code.loadConst(0);
			Code.put(Code.aload);
			Code.put(Code.store_2);
			
			Code.loadConst(1);
			Code.put(Code.store_3);
			
			
			// While petlja pocetak
			int whileStart=Code.pc;
			
			Code.put(Code.load_3); // stack: i
			Code.put(Code.load_2); // stack: i, n

			
			Code.putFalseJump(Code.le, 0); // Ako je i > n izlazimo iz while petlje da ubacimo element
			int patchAddrWhileEnd=Code.pc-2;
			
			//Printanje set[i]
			Code.put(Code.load_n);
			Code.put(Code.load_3);
			Code.put(Code.aload);
			Code.put(Code.load_1); // stack: set[i], width
			Code.put(Code.print);
			
			Code.loadConst(' ');
			Code.put(Code.load_1);
			Code.put(Code.bprint);
			
			// Inkrementiranje brojaca
			Code.put(Code.load_3);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.store_3);
			
			Code.putJump(whileStart);
			
			
			
			Code.fixup(patchAddrWhileEnd);
			

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
