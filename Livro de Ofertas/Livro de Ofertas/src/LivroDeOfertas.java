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

	private Map<Double, ArrayList<OrdemLimitada>> compra = new TreeMap<>();
	private Map<Double, ArrayList<OrdemLimitada>> venda = new TreeMap<>(( o1, o2 ) -> -o1.compareTo( o2 ));

	public LivroDeOfertas(double tick) {
		this.tick = tick;
	}

	public void registra(OrdemLimitada ordemLimitada) {

		if (ordemLimitada.getOperacao().equals("compra")) {
			
			if(ordemLimitada.quantidade > getQuantidadeVenda(ordemLimitada.valor)) {
				ordemLimitada.quantidade = (int) (ordemLimitada.quantidade - getQuantidadeVenda(ordemLimitada.valor));
				venda.clear();
				
			}
			
			else if (ordemLimitada.quantidade == getQuantidadeVenda(ordemLimitada.valor)) {
				venda.clear();
				ordemLimitada.quantidade = 0;
			}
			
			else {
				Set<Double> valores = compra.keySet();
				ArrayList<Double> array = new ArrayList<>();
				array.addAll(valores);
				
			//Filipe, aqui voce vai passar por cada elemento da arraylist com o loop que criou abaixo
			//Com isso, voce vai ir diminuindo seletivamente a quantidade de cada elemento do array até 
			//Diminuir a quantidade total passada com a ordemLimitada
				
			}

			if (compra.containsKey(ordemLimitada.valor)) {
				compra.get(ordemLimitada.valor).add(ordemLimitada);
			} else {
				ArrayList<OrdemLimitada> arrayDeOrdensCompra = new ArrayList<OrdemLimitada>();
				compra.put(ordemLimitada.valor, arrayDeOrdensCompra);
				compra.get(ordemLimitada.valor).add(ordemLimitada);
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

	public double getQuantidadeCompra(	double d) {
		double quantidade = 0;
		
		//Valores de Key pra ArrayList
		Set<Double> valores = compra.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);
		

		
		for(int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if(temp <= d) {
				
				for(int j = 0; j < compra.get(array.get(i)).size(); j++) {
					quantidade += compra.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}

	public double getQuantidadeVenda(double d) {
		double quantidade = 0;
		
		//Valores de Key pra ArrayList
		Set<Double> valores = venda.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);
		

		
		for(int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if(temp >= d) {
				
				for(int j = 0; j < venda.get(array.get(i)).size(); j++) {
					quantidade += venda.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}
	
	public int getQuantidadeVendaValorEspecifico(double d) {
		int quantidade = 0;
		
		//Valores de Key pra ArrayList
		Set<Double> valores = venda.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);
		

		
		for(int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if(temp == d) {
				
				for(int j = 0; j < venda.get(array.get(i)).size(); j++) {
					quantidade += venda.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}
	
	public int getQuantidadeCompraValorEspecifico(double d) {
		int quantidade = 0;
		
		//Valores de Key pra ArrayList
		Set<Double> valores = compra.keySet();
		ArrayList<Double> array = new ArrayList<>();
		array.addAll(valores);
		

		
		for(int i = 0; i < array.size(); i++) {
			double temp = array.get(i);
			if(temp == d) {
				
				for(int j = 0; j < compra.get(array.get(i)).size(); j++) {
					quantidade += compra.get(array.get(i)).get(j).quantidade;
				}
			}
		}
		return quantidade;
	}

}
