package aima.gui.demo.search;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.*;
import aima.core.search.agent.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

import aima.core.search.informed.RecursiveBestFirstSearch;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * @author Ravi Mohan
 * @author Ruediger Lunde
 * 
 */

public class EightPuzzleDemo {
	private static EightPuzzleBoard boardWithThreeMoveSolution =
			new EightPuzzleBoard(new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });

	private static EightPuzzleBoard random1 =
			new EightPuzzleBoard(new int[] { 1, 4, 2, 7, 5, 8, 3, 0, 6 });

	private static EightPuzzleBoard board = null;
	
	private static double[] maxQueueSize = {0,0,0,0,0,0,0};
	private static double[] pathCost = {0,0,0,0,0,0,0};
	private static double[] nodesExpanded = {0,0,0,0,0,0,0};
	private static int cont = 0;
	
//	private static EightPuzzleBoard extreme =
//			new EightPuzzleBoard(new int[] { 0, 8, 7, 6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		System.out.println("Inicio");
		for (int i=0;i<32;i++) {
			board = generaterandomBoard();
			System.out.println("-------------------"+i+"-------------------");
			System.out.println("Initial State:\n" + board);
			cont=0;
			BuscaIterativeDeepeningDepthFirst();
			cont=1;
			GreedyBestFirstManhattan();
			cont=2;
			GreedyBestFirstMisplacedTile();
			cont=3;
			AStarManhattan();
			cont=4;
			AStarMisplacedTile();
			cont=5;
			RBFSmanhattan();
			cont=6;
			RBFSmisplacedtiles();
			System.out.println("Fim");
		}
		
		/*board = randomBoard();
		System.out.println("-------------------"+"-------------------");
		System.out.println("Initial State:\n" + board);
		eightPuzzleIDLSDemo();
		eightPuzzleGreedyBestFirstDemo();
		eightPuzzleGreedyBestFirstManhattanDemo();
		eightPuzzleAStarDemo();
		eightPuzzleAStarManhattanDemo();
		eightPuzzleSimulatedAnnealingDemo();*/
	}
	
	private static EightPuzzleBoard randomBoard() {
		EightPuzzleBoard board1 = null;
		board1 = new EightPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < 50; i++) {
			switch (r.nextInt(4)) {
			case 0:
				board1.moveGapUp();
				break;
			case 1:
				board1.moveGapDown();
				break;
			case 2:
				board1.moveGapLeft();
				break;
			case 3:
				board1.moveGapRight();
				break;
			}
		}
		return board1;
	}
	
	private static EightPuzzleBoard generaterandomBoard() {
		Random r = new Random();
		EightPuzzleBoard board1 = new EightPuzzleBoard(new int[] { 0, 1, 2, 3,
				4, 5, 6, 7, 8 });
		for (int i = 0; i < 50; i++) {
			int th = r.nextInt(4);
			if (th == 0) {
				board1.moveGapUp();
			}
			if (th == 1) {
				board1.moveGapDown();
			}
			if (th == 2) {
				board1.moveGapLeft();
			}
			if (th == 3) {
				board1.moveGapRight();
			}
		}
		return board1;
	}
	
	private static void BuscaIterativeDeepeningDepthFirst() {
		System.out.println("\nBusca Iterative-Deepening DepthFirst");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new IterativeDeepeningSearch<>();
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void GreedyBestFirstMisplacedTile() {
		System.out.println("\nGreedy Best First Search (MisplacedTileHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new GreedyBestFirstSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getNumberOfMisplacedTiles);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void GreedyBestFirstManhattan() {
		System.out.println("\nGreedy Best First Search (ManhattanHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new GreedyBestFirstSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getManhattanDistance);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void AStarMisplacedTile() {
		System.out.println("\nAStar (MisplacedTileHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new AStarSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getNumberOfMisplacedTiles);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void AStarManhattan() {
		System.out.println("\nAStar (ManhattanHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new AStarSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getManhattanDistance);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void RBFSmanhattan() {
		System.out.println("\nRBFS (ManhattanHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new RecursiveBestFirstSearch<>(AStarSearch.createEvalFn(EightPuzzleFunctions::getManhattanDistance));
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void RBFSmisplacedtiles() {
		System.out.println("\nRBFS (MisplacedTileHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new RecursiveBestFirstSearch<>(AStarSearch.createEvalFn(EightPuzzleFunctions::getNumberOfMisplacedTiles));
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*private static void BuscaBreadthFirst() {
		System.out.println("\nBusca Breadth First");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new BreadthFirstSearch<>(new GraphSearch<>());
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void BuscaUniformCost() {
		System.out.println("\nBusca Uniform Cost");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new UniformCostSearch<>(new GraphSearch<>());
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void BuscaDepthFirst() {
		System.out.println("\nBusca DepthFirst");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new DepthFirstSearch<>(new GraphSearch<>());
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void BuscaDepthLimited() {
		System.out.println("\nBusca Depth Limited com limite 14");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new DepthLimitedSearch<>(14);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
			atualizarMetricas(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*private static void eightPuzzleDLSDemo() {
		System.out.println("\nEightPuzzleDemo recursive DLS (9)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new DepthLimitedSearch<>(9);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eightPuzzleIDLSDemo() {
		System.out.println("\nEightPuzzleDemo Iterative DLS");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new IterativeDeepeningSearch<>();
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eightPuzzleGreedyBestFirstDemo() {
		System.out.println("\nEightPuzzleDemo Greedy Best First Search (MisplacedTileHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new GreedyBestFirstSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getNumberOfMisplacedTiles);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eightPuzzleGreedyBestFirstManhattanDemo() {
		System.out.println("\nEightPuzzleDemo Greedy Best First Search (ManhattanHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new GreedyBestFirstSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getManhattanDistance);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleAStarDemo() {
		System.out.println("\nEightPuzzleDemo AStar Search (MisplacedTileHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new AStarSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getNumberOfMisplacedTiles);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eightPuzzleSimulatedAnnealingDemo() {
		System.out.println("\nEightPuzzleDemo Simulated Annealing Search");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SimulatedAnnealingSearch<EightPuzzleBoard, Action> search = new SimulatedAnnealingSearch<>
					(EightPuzzleFunctions::getManhattanDistance);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			System.out.println("Final State:\n" + search.getLastState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eightPuzzleAStarManhattanDemo() {
		System.out.println("\nEightPuzzleDemo AStar Search (ManhattanHeursitic)");
		try {
			Problem<EightPuzzleBoard, Action> problem = new BidirectionalEightPuzzleProblem(board);
			SearchForActions<EightPuzzleBoard, Action> search = new AStarSearch<>
					(new GraphSearch<>(), EightPuzzleFunctions::getManhattanDistance);
			SearchAgent<Object, EightPuzzleBoard, Action> agent = new SearchAgent<>(problem, search);
			//printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	private static void atualizarMetricas(Properties properties) {
		
		System.out.println(properties.get("nodesExpanded"));
		System.out.println(properties.get("pathCost"));
		
		if(cont!=0 && cont !=5 && cont!=6) {
		System.out.println(properties.get("maxQueueSize"));
		String mqs = (String)properties.get("maxQueueSize");
		maxQueueSize[cont] += Double.parseDouble(mqs);
		}
		
		String pc = (String)properties.get("pathCost");
		pathCost[cont] += Double.parseDouble(pc);
		
		String ne = (String)properties.get("nodesExpanded");
		nodesExpanded[cont] += Double.parseDouble(ne);
		
		System.out.println("Somatorio Nodes Expanded: "+ nodesExpanded[cont]);
		System.out.println("Somatorio PathCost: "+ pathCost[cont]);
		if(cont!=0 && cont!=5 && cont!=6) {System.out.println("Somatorio MaxQSize: "+ maxQueueSize[cont]);}
	}

	private static void printInstrumentation(Properties properties) {
		properties.keySet().stream().map(key -> key + "=" + properties.get(key)).forEach(System.out::println);
	}

	private static void printActions(List<Action> actions) {
		actions.forEach(System.out::println);
	}
}