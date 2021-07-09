package aima.gui.projetofinal;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.Domain;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.Variable;
import aima.core.search.csp.examples.MapCSP;
import aima.core.search.csp.inference.AC3Strategy;

public class Demo {

	public static void main(String[] args) {
		CSP<Variable, String> csp = new AgendamentoCSP(2);
		CspListener.StepCounter<Variable, String> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, String> solver;
		Optional<Assignment<Variable, String>> solution;
		String[] dias = { "", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" };

		solver = new FlexibleBacktrackingSolver<Variable, String>();//.set(new AtividadesRemoveStrategy<>());// .set(new
																											// AC3Strategy<>());
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		System.out.println("Bloco CSP (Backtracking + AC3)");
		solution = solver.solve(csp);
		System.out.format("%25s%25s%25s%25s%25s%25s%25s\n", dias);
		boolean meiaHora = false;
		for (int i = 0; i < 24; i++) {
			var v1 = i + ":00-" + i + ":30";
			var v2 = i + ":30-" + (i == 23 ? 0 : i + 1) + ":00";
			meiaHora = !meiaHora;
			imprimirHorario(csp, solution, v1);
			imprimirHorario(csp, solution, v2);

		}
		System.out.println(stepCounter.getResults() + "\n");
	}

	private static String getValue(Variable variable, CSP<Variable, String> csp,
			Optional<Assignment<Variable, String>> solution) {
		var valor = solution.get().getValue(variable);
		if (valor == null) {
			valor = csp.getDomain(variable).size() == 1 ? csp.getDomain(variable).get(0) : "livre";
		}
		return valor;
	}

	private static void imprimirHorario(CSP<Variable, String> csp, Optional<Assignment<Variable, String>> solution,
			String vlr) {
		var values = csp.getVariables().stream().filter(v -> v.getName().contains(vlr)).collect(Collectors.toList());
		ArrayList<String> toPrint = new ArrayList<>();
		toPrint.add(vlr);
		for (int j = 0; j < 6; j++) {
			toPrint.add(getValue(values.get(j), csp, solution));
		}
		System.out.format("%25s%25s%25s%25s%25s%25s%25s\n", toPrint.toArray());
	}
}
