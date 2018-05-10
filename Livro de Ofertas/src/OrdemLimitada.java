import java.util.ArrayList;

public class OrdemLimitada {
    
    int cliente;
    int numCorretora;
    int quantidade;
    double valor;
    String operacao;
    

    public OrdemLimitada( int cliente, int numCorretora,
            LivroDeOfertas.Direcao compra, int i, double d ) {
        this.cliente = cliente;
        this.numCorretora = numCorretora;
        this.quantidade = i;
        this.valor = d;
        operacao = compra.getDirecao();
        
        
    }
    
    public String getOperacao() {
    	return operacao;
    }

}
