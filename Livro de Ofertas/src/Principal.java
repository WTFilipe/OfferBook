public class Principal {

    public static void main( String[] args ) {
        LivroDeOfertas livro = new LivroDeOfertas( 0.5 );
        int numCorretora = 45;
        int cliente = 23;

        livro.registra( new OrdemLimitada( cliente, numCorretora,
                LivroDeOfertas.Direcao.COMPRA, 500, 14.97 ) );
        livro.registra( new OrdemLimitada( cliente, numCorretora,
                LivroDeOfertas.Direcao.COMPRA, 500, 19.87 ) );
        livro.registra( new OrdemLimitada( cliente, numCorretora,
                LivroDeOfertas.Direcao.COMPRA, 500, 15.97 ) );
        
        System.out.println( livro.getPrecoCompra() ); 
        //System.out.println( livro.getPrecoVenda() );
        System.out.println( livro.getQuantidadeCompra( 14.97 ) );
        //System.out.println( livro.getQuantidadeVenda( 14.98 ) );
    }
}
