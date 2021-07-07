package aima.gui.projetofinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

public class ConstraintTodasAtividadesAlocadas<VAR extends Variable, VAL> implements Constraint<VAR, VAL>{

	private VAR var1;
	private VAR var2;
	private List<VAR> scope;

	public ConstraintTodasAtividadesAlocadas(List<VAR> atividades) {
		this.var1 = var1;
		this.var2 = var2;
		scope = new ArrayList<>(2);
		scope.add(var1);
		scope.add(var2);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		VAL value1 = assignment.getValue(var1);
		return value1 == null || !value1.equals(assignment.getValue(var2));
	}
	private List<String> atividadesRestantes;

	public ConstraintTodasAtividadesAlocadas(String[] atvRes){
		this.atividadesRestantes = Arrays.asList(atvRes);
	}

	public List<String> getAtividadesRestantes() {
		return atividadesRestantes;
	}

	public boolean isSatisfied() {
		return atividadesRestantes.isEmpty();
	}
}
