import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Collections;

public class LivroDeOfertas {
	double tick;

	// Declaração de enum
	public static enum Direcao {
		COMPRA("compra"), VENDA("venda");

		String direcao;

		Direcao(String direcao) {
			this.direcao = direcao;

		}

		public String getDirecao() {
			return direcao;
		}
	};

	private Map<Double, ArrayList<OrdemLimitada>> compra = new TreeMap<>();
	private Map<Double, ArrayList<OrdemLimitada>> venda = new TreeMap<>();

	public LivroDeOfertas(double tick) {
		this.tick = tick;
	}

	public void registra(OrdemLimitada ordemLimitada) {

		if (ordemLimitada.getOperacao().equals("compra")) {

			if (compra.containsKey(ordemLimitada.valor)) {
				compra.get(ordemLimitada.valor).add(ordemLimitada);
			} else {
				compra.put(ordemLimitada.valor, null);
			}
		}

		if (ordemLimitada.getOperacao().equals("venda")) {

			if (compra.containsKey(ordemLimitada.valor)) {
				venda.get(ordemLimitada.valor).add(ordemLimitada);
			} else {
				venda.put(ordemLimitada.valor, null);
			}
		}
	}

	public double getPrecoCompra() {
		//Jogo todos as Keys em um ArrayList
		Set<Double> valores = compra.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);

		return Collections.max(array);
	}

	public double getPrecoVenda() {
		Set<Double> valores = venda.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);

		return Collections.min(array);
	}

	public double getQuantidadeCompra(double d) {
		double quantidade = 0;
		
		//Valores de Key pra ArrayList
		Set<Double> valores = compra.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);
		

		
		for(int i = 0; i < array.size(); i++) {
			if(array.get(i) <= d) {
				quantidade = quantidade + compra.get(array.get(i)).size();
			}
		}
		return quantidade;
	}

	public double getQuantidadeVenda(double d) {
		// TODO Auto-generated method stub
		return 0;
	}

}
