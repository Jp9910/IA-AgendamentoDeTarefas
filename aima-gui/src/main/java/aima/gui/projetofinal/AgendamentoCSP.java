package aima.gui.projetofinal;

import java.util.*;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import aima.core.search.csp.examples.NotEqualConstraint;
import aima.gui.projetofinal.Variaveis;
import aima.gui.projetofinal.ConstraintTodasAtividadesAlocadas;

public class AgendamentoCSP extends CSP<Variable, String> {

	public static final Variable[] blocos = new Variable[288];
	public static final String[] dias = { "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" };

	static {
		int j = 0;
		for (String dia : dias) {
			for (int i = 0; i < 48; i = i + 2) {
				blocos[j] = new Variable(dia + " " + (i / 2) % 24 + ":00-" + (i / 2) % 24 + ":30");
				blocos[j + 1] = new Variable(dia + " " + (i / 2) % 24 + ":30-" + ((i / 2) + 1) % 24 + ":00");
				//System.out.println(i + " " + blocos[j]);
				//System.out.println((i + 1) + " " + blocos[j + 1]);
				j = j + 2;
			}
		}
	}
	public static final String estudar = "estudar";
	public static final String trabalhar = "Estagio/Trabalho";
	public static final String dormir = "Sono";
	public static final String comer = "Refeicao";
	public static final String init = "PIBIC/PIBIX/PIBITI";
	public static final String voluntario = "Atividade voluntaria";
	public static final String palestra = "Palestra/evento extracurricular";
	public static final String aula = "Horario de aula";

	public AgendamentoCSP(int caso) {
		super(Arrays.asList(blocos));

		switch (caso) {
		case 1:
			Caso1();
			break;
		case 2:
			Caso2();
			break;
		case 3:
			Caso3();
			break;
		}
	}

	public void Caso1() {
		var variables = getVariables();
		// preenche com blocos de materias
		var blocosEstudo = new ArrayList<String>();
		blocosEstudo = criarBlocosMateria(8, "COMP0455", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0481", blocosEstudo);
		blocosEstudo = criarBlocosMateria(8, "COMP0408", blocosEstudo);

		for (int dia = 0; dia < 6; dia++) {
			for (int horario = 0; horario < 48; horario++) {
				Variable var = variables.get((dia * 48) + horario);
				if (horario >= 46 || horario <= 13) {
					setDomain(var, new Domain<>(dormir));
				} else if (horario == 14 || horario == 15 || horario == 24 || horario == 25 || horario == 36
						|| horario == 37) {
					setDomain(var, new Domain<>(comer));
				} else if (horario >= 16 && horario <= 23) {
					setDomain(var, new Domain<>(trabalhar));
				} else if (((dia == 1 || dia == 3) && (horario >= 30 && horario <= 33))
						|| (dia == 3 && (horario >= 38 && horario <= 41))
						|| ((dia == 0 || dia == 2) && (horario >= 42 && horario <= 45))) {
					setDomain(var, new Domain<>(aula));
				} else if (dia == 5 && (horario >= 30 && horario <= 33)) {
					setDomain(var, new Domain<>(palestra));
				} else if (dia == 5 && (horario >= 38 && horario <= 45)) {
					setDomain(var, new Domain<>(voluntario));
				} else {
					setDomain(var, new Domain<>(blocosEstudo));
				}
			}
		}
	}

	public void Caso2() {
		var variables = getVariables();
		// preenche com blocos de materias
		var blocosEstudo = new ArrayList<String>();
		blocosEstudo = criarBlocosMateria(4, "COMP0409", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0438", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0412", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0408", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0461", blocosEstudo);

		for (int dia = 0; dia < 6; dia++) {
			for (int horario = 0; horario < 48; horario++) {
				Variable var = variables.get((dia * 48) + horario);
				if (horario >= 44 || horario <= 11) {
					setDomain(var, new Domain<>(dormir));
				} else if (horario == 12 || horario == 13 || horario == 24 || horario == 25 || horario == 38
						|| horario == 39) {
					setDomain(var, new Domain<>(comer));
				} else if (dia < 5 && (horario >= 14 && horario <= 21)) {
					setDomain(var, new Domain<>(init));
				} else if (((dia == 0 || dia == 2) && ((horario >= 26 && horario <= 29)
						|| (horario >= 34 && horario <= 37) || (horario >= 40 && horario <= 43)))
						|| ((dia == 1 || dia == 3) && (horario >= 26 && horario <= 29))
						|| (dia == 4 && (horario >= 26 && horario <= 33))) {
					setDomain(var, new Domain<>(aula));
				} else if (dia == 5 && (horario >= 30 && horario <= 37)) {
					setDomain(var, new Domain<>(palestra));
				} else if (dia == 5 && (horario >= 14 && horario <= 21)) {
					setDomain(var, new Domain<>(voluntario));
				} else {
					setDomain(var, new Domain<>(blocosEstudo));
				}
			}
		}
	}

	public void Caso3() {
		var variables = getVariables();
		// preenche com blocos de materias
		var blocosEstudo = new ArrayList<String>();
		blocosEstudo = criarBlocosMateria(2, "ELET0043", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "MAT0096", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "MAT0154", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "ESTAT0011", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0409", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0415", blocosEstudo);
		blocosEstudo = criarBlocosMateria(4, "COMP0412", blocosEstudo);
		blocosEstudo = criarBlocosMateria(2, "COMP0417", blocosEstudo);

		for (int dia = 0; dia < 6; dia++) {
			for (int horario = 0; horario < 48; horario++) {
				Variable var = variables.get((dia * 48) + horario);
				if (horario >= 46 || horario <= 13) {
					setDomain(var, new Domain<>(dormir));
				} else if (horario == 14 || horario == 15 || horario == 24 || horario == 25 || horario == 38
						|| horario == 39) {
					setDomain(var, new Domain<>(comer));
				} else if (((dia >= 0 && dia <= 3) && (horario >= 26 && horario <= 37))
						|| (dia == 4 && ((horario >= 26 && horario <= 29) || (horario >= 24 && horario <= 37)))) {
					setDomain(var, new Domain<>(aula));
				} else if (dia == 5 && (horario >= 30 && horario <= 37)) {
					setDomain(var, new Domain<>(palestra));
				} else if ((dia == 0 || dia == 2 || dia == 4) && (horario >= 40 && horario <= 45)) {
					setDomain(var, new Domain<>(voluntario));
				} else {
					setDomain(var, new Domain<>(blocosEstudo));
				}
			}
		}
	}

	private ArrayList<String> criarBlocosMateria(int horas, String codigoMateria, ArrayList<String> blocosEstudo) {
		for (int i = 0; i < (horas * 2); i++) {
			blocosEstudo.add("estudo " + codigoMateria + " " + (i + 1));
		}
		return blocosEstudo;
	}
}
