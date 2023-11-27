# Proposta: refatorar o jogo aplicando o padrão State para criar fases.

## Como nós aplicamos o padrão State:

```java
public interface JogoState {

	void inicializar(Jogo jogo);
    void atualizar();
    void desenhar(Graphics g);
    void passarProximaFase();
}
```

- Com essa interface, conseguimos controlar o estado do jogo nas classes "Fase":

```java
public class Fase implements JogoState {

	private Jogo jogo;
	private final int numeroDaFase = 5;
	
	@Override
	public void inicializar(Jogo jogo) {
		// TODO Auto-generated method stub
		...
	}
	
	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		...
	}
	
	@Override
	public void desenhar(Graphics g) {
		// TODO Auto-generated method stub
		...
	}
	
	@Override
	public void passarProximaFase() {
		// TODO Auto-generated method stub
		...
	}
}
```

