package aima.gui.projetofinal;

import java.util.*;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import aima.core.search.csp.examples.NotEqualConstraint;
import aima.gui.projetofinal.Variaveis;
import aima.gui.projetofinal.ConstraintTodasAtividadesAlocadas;

public class AgendamentoCSP extends CSP<Variable, String>{

	//Integer[] array = new Integer[100];
	//List<Variable> blocosSeg = new ArrayList<Variable>();
	public static final Variable[] blocos = new Variable[288];
	public static final String[] dias = {"Seg","Ter","Qua","Qui","Sex","Sab"};
	
	static {
		int j=0;
		for (String dia : dias) {
			for (int i=0;i<48;i=i+2) {
				blocos[j] = new Variable(dia+" "+(i/2)%24+":00-"+(i/2)%24+":30");
				blocos[j+1] = new Variable(dia+" "+(i/2)%24+":30-"+((i/2)+1)%24+":00");
				j=j+2;
			}
		}
	}
	public static final String estudar = "estudar";
	public static final String trabalhar = "trabalhar";
	public static final String dormir = "dormir";
	public static final String comer = "comer";
	public static final String[] atividadesRestantes = {estudar,trabalhar};
	//public static final String[] dominioDormir = {dormir};
	
	public AgendamentoCSP() {
		super(Arrays.asList(blocos));
		
		Domain<String> atividades = new Domain<>(atividadesRestantes); //construtor de Domain funciona com array?
		Domain<String> domDormir = new Domain<>(dormir);
		Domain<String> domComer = new Domain<>(comer);
		int i=-1;
		for (Variable var : getVariables()){
			i=(i+1)%48;
			System.out.print(i+"-");
			System.out.println(var);
			if(i<=15) {
				setDomain(var,domDormir);
			}
			else if (i<=17){
				setDomain(var,domComer);
			}
			else
				setDomain(var, atividades);
		}
		
		//addConstraint(new NotEqualConstraint<>(blocos[0],blocos[1]));
		//addConstraint(new ConstraintTodasAtividadesAlocadas(atividadesRestantes));
		
	}
	
	//main apenas para testar as variáveis
	/* 
	 * public static void main(String[] args) {
		Variable[] blocos = new Variable[288];{
			//for (int dia=1;dia<=6;dia++) {
				for (int i=0;i<288;i=i+2) {
					blocos[i] = new Variable((i/2)%24+":00-"+(i/2)%24+":30");
					blocos[i+1] = new Variable((i/2)%24+":30-"+((i/2)+1)%24+":00");
					
				}
			//}
		}
		
		System.out.println("teste");
		for (int i=0;i<288;i=i+1) {
			System.out.println(blocos[i]);
		}
		System.out.println("fim");
	}*/
}
