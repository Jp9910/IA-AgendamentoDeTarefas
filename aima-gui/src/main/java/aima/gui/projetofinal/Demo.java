package aima.gui.projetofinal;

import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.Variable;
import aima.core.search.csp.examples.MapCSP;
import aima.core.search.csp.inference.AC3Strategy;

public class Demo {

	public static void main(String[] args) {
		CSP<Variable, String> csp = new AgendamentoCSP(1);
		CspListener.StepCounter<Variable, String> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, String> solver;
		Optional<Assignment<Variable, String>> solution;

		solver = new FlexibleBacktrackingSolver<Variable, String>().set(new AC3Strategy<>());
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		System.out.println("Bloco CSP (Backtracking + AC3)");
		solution = solver.solve(csp);
		// solution.ifPresent(System.out::println);
		for (var v : csp.getVariables()) {
			var variavel = v.getName();
			var valor = solution.get().getValue(v);
			if (valor == null) {
				valor = csp.getDomain(v).size() == 1 ? csp.getDomain(v).get(0) : "livre";
			}
			System.out.println(variavel + "=" + valor);
		}
		System.out.println(stepCounter.getResults() + "\n");
	}
}
