package algocraft.construccionesAlternativas;

import stats.Escudo;

public class EdificioConEscudo extends EdificioBasico{

	public EdificioConEscudo(EnumEdificios nombre, int vidaMaxima, int tiempoDeConstruccion, int escudoMaximo) {
		super(nombre, vidaMaxima, tiempoDeConstruccion);
		this.stat = new Escudo(escudoMaximo, this.stat);
	}
	
	public int getEscudo() {
		return stat.actual();
	}
	
	public int getVida(){
		return ((Escudo)stat).actualProtegida();
	}
	
	public void pasarTurno() {
		this.disminuirTiempoDeConstruccion();
		this.stat.regenerar();
	}
}
