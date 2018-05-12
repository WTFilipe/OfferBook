import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

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

	private Map<Double, ArrayList<OrdemLimitada>> compra = new TreeMap<>((o1, o2) -> -o1.compareTo(o2));
	private Map<Double, ArrayList<OrdemLimitada>> venda = new TreeMap<>((o1, o2) -> o1.compareTo(o2));

	public LivroDeOfertas(double tick) {
		this.tick = tick;
	}

	public void registra(OrdemLimitada ordemLimitada) {

		if (ordemLimitada.getOperacao().equals("compra")) {

			if (!venda.isEmpty()) {
				if (ordemLimitada.quantidade > getQuantidadeCompra(ordemLimitada.valor)) {
					int contador = ordemLimitada.quantidade;
					ordemLimitada.quantidade = (int) (ordemLimitada.quantidade
							- getQuantidadeCompra(ordemLimitada.valor));

					Set<Double> valores = venda.keySet();
					ArrayList<Double> array = new ArrayList<>();
					array.addAll(valores);

					while (contador != 0) {
						for (int i = 0; i < array.size(); i++) {
							for (int j = 0; j < venda.get(array.get(i)).size(); j++) {
								if (contador > venda.get(array.get(i)).get(j).quantidade) {
									contador -= venda.get(array.get(i)).get(j).quantidade;
									venda.get(array.get(i)).get(j).quantidade = 0;
								}

								else if (contador == venda.get(array.get(i)).get(j).quantidade) {
									contador = 0;
									venda.get(array.get(i)).get(j).quantidade = 0;
								}
								
								else {
									venda.get(array.get(i)).get(j).quantidade -= contador;
									contador = 0;
																		
								}
							}
						}
					}
					

					for (int i = 0; i < array.size(); i++) {
						for (int j = 0; j < venda.get(array.get(i)).size(); j++) {
							if (venda.get(array.get(i)).get(j).quantidade <= 0) {
								venda.get(array.get(i)).remove(j);

							}
						}
					}
					if(venda.get(ordemLimitada.valor).size() == 0) {
						venda.remove(ordemLimitada.valor);
					}

				}

				else if (ordemLimitada.quantidade == getQuantidadeCompra(ordemLimitada.valor)) {
					Set<Double> valores = venda.keySet();
					ArrayList<Double> array = new ArrayList<>();
					array.addAll(valores);

					for (int i = 0; i < array.size(); i++) {
						for (int j = 0; j < venda.get(array.get(i)).size(); j++) {
							if (venda.get(array.get(i)).get(j).quantidade <= 0) {
								venda.get(array.get(i)).remove(j);

							}
						}
					}
					ordemLimitada.quantidade = 0;
				}

				else {
					Set<Double> valores = venda.keySet();
					ArrayList<Double> array = new ArrayList<>();
					array.addAll(valores);

					while (ordemLimitada.quantidade > 0) {
						for (int i = 0; i < array.size(); i++) {
							for (int j = 0; j < venda.get(array.get(i)).size(); j++) {
								if (venda.get(array.get(i)).get(j).quantidade > ordemLimitada.quantidade) {
									venda.get(array.get(i)).get(j).quantidade -= ordemLimitada.quantidade;
									ordemLimitada.quantidade = 0;

								}

								else if (venda.get(array.get(i)).get(j).quantidade == ordemLimitada.quantidade) {
									venda.get(array.get(i)).remove(j);
									ordemLimitada.quantidade = 0;
								}

								else {
									ordemLimitada.quantidade -= venda.get(array.get(i)).get(j).quantidade;

								}
							}
						}
					}

				}

			}

			if (ordemLimitada.quantidade > 0) {
				if (compra.containsKey(ordemLimitada.valor)) {
					compra.get(ordemLimitada.valor).add(ordemLimitada);
				} else {
					ArrayList<OrdemLimitada> arrayDeOrdensCompra = new ArrayList<OrdemLimitada>();
					compra.put(ordemLimitada.valor, arrayDeOrdensCompra);
					compra.get(ordemLimitada.valor).add(ordemLimitada);
				}
			}
		}

		if (ordemLimitada.getOperacao().equals("venda")) {

			if (venda.containsKey(ordemLimitada.valor)) {
				venda.get(ordemLimitada.valor).add(ordemLimitada);
			} else {
				ArrayList<OrdemLimitada> arrayDeOrdensVenda = new ArrayList<OrdemLimitada>();
				venda.put(ordemLimitada.valor, arrayDeOrdensVenda);
				venda.get(ordemLimitada.valor).add(ordemLimitada);
			}
		}
	}

	public double getPrecoCompra() {

		// Jogo todos as Keys em um ArrayList
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

		// Valores de Key pra ArrayList
		Set<Double> valores = venda.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);

		for (int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if (temp <= d) {

				for (int j = 0; j < venda.get(array.get(i)).size(); j++) {
					quantidade += venda.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}

	public double getQuantidadeVenda(double d) {
		double quantidade = 0;

		// Valores de Key pra ArrayList
		Set<Double> valores = compra.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);

		for (int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if (temp >= d) {

				for (int j = 0; j < compra.get(array.get(i)).size(); j++) {
					quantidade += compra.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}

	public int getQuantidadeVendaValorEspecifico(double d) {
		int quantidade = 0;

		// Valores de Key pra ArrayList
		Set<Double> valores = venda.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);

		for (int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if (temp == d) {

				for (int j = 0; j < venda.get(array.get(i)).size(); j++) {
					quantidade += venda.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}

	public int getQuantidadeCompraValorEspecifico(double d) {
		int quantidade = 0;

		// Valores de Key pra ArrayList
		Set<Double> valores = compra.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);

		for (int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if (temp == d) {

				for (int j = 0; j < compra.get(array.get(i)).size(); j++) {
					quantidade += compra.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}

}
