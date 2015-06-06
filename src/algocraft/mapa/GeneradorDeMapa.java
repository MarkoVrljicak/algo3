package algocraft.mapa;

import algocraft.exception.FueraDeLimitesException;
import algocraft.mapa.terrenos.Terreno;
import algocraft.mapa.terrenos.Terrenos;

public class GeneradorDeMapa {
	//Tamanio minimo para que funcione el algoritmo: 20x20
	Coordenada posicionBase1;
	Coordenada posicionBase2;
	
	private Mapa mapaGenerado;
	
	public GeneradorDeMapa(int ancho, int alto){
		posicionBase1 = new Coordenada(4, alto -3);
		posicionBase2 = new Coordenada(ancho -3, 4);
		mapaGenerado = new Mapa(ancho, alto);
	}
	
	public Mapa generar(){
		
		//1) Colocar Recursos
		//6 minerales a distancia 3 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 8, 3, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 8, 3, posicionBase2);
		
		//2 volcanes a distancia 3 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 3, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 3, posicionBase2);
		
		//5 minerales a distancia 5 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 5, 5, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 5, 5, posicionBase2);
		
		//2 volcanes a distancia 5 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 5, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 5, posicionBase2);
		
		
		//3 minerales a distancia 7 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 3, 7, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 3, 7, posicionBase2);
		
		//2 volcanes a distancia 7 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 7, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 7, posicionBase2);
		
		//2 minerales a distancia 15 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 2, 15, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.MINERALES, 2, 15, posicionBase2);
		
		//2 volcanes a distancia 15 de las bases
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 15, posicionBase1);
		colocarRecursoAlRededorDeBase(Terrenos.VOLCAN, 2, 15, posicionBase2);
		
		//2) Colocar Aire
		colocarAire();
		
		
		return mapaGenerado;
	}

	private void colocarRecursoAlRededorDeBase(Terrenos terreno, int cantidad, int distancia, Coordenada posicionBase){
		/* Coloca cierta cantidad de cierto recurso a cierta distancia de la coordenada provista */
		
		while (cantidad > 0){
		
			for (int i = 1; i <= mapaGenerado.getAncho(); i++) {
				for (int j = 1; j <= mapaGenerado.getAlto(); j++) {
				
					if (posicionBase.distanciaA(new Coordenada(i, j) ) == distancia && casilleroEsApto(i,j)){
						
						if(Math.random() < 0.2){
							mapaGenerado.setearTerrenoEnCoordenada(terreno, i, j);
							cantidad--;
						}
					}
				
				}
			}
		}
		
	}

	private void colocarAire(){
		int minimaCantidadLagosAire = 2;
		
		while (minimaCantidadLagosAire > 0){
			for (int i = 1; i <= mapaGenerado.getAncho(); i++) {
				for (int j = 1; j <= mapaGenerado.getAlto(); j++) {
				
					if (casilleroEsApto(i,j) && Math.random() < 0.01){
						expandirAireConProbabilidad(i, j, 1, 5);
						minimaCantidadLagosAire--;
					
					}
				}
				
			}
		}
			
	}
	
	private void expandirAireConProbabilidad(int i, int j, double probabilidad, int cantidadAireRestante){
		/* La probabilidad es del 0 (imposible) a 1 (seguro) */
		
		if(Math.random() < probabilidad && cantidadAireRestante > 0){
		
			mapaGenerado.setearTerrenoEnCoordenada(Terrenos.AIRE, i, j);
			cantidadAireRestante--;
		} else {
			
			return;
		}
		
		
		if ( i+1 < mapaGenerado.getAncho() && casilleroEsApto(i+1, j) ){
			expandirAireConProbabilidad(i+1, j, probabilidad - 0.1, cantidadAireRestante);
		}
		
		if ( j+1 < mapaGenerado.getAlto() && casilleroEsApto(i, j+1)){
			expandirAireConProbabilidad(i, j+1, probabilidad - 0.1, cantidadAireRestante);
		}
		
		if ( (i-1 > 0) && casilleroEsApto(i-1, j) ){
			expandirAireConProbabilidad(i-1, j, probabilidad - 0.1, cantidadAireRestante);
		}
		
		if ( (j-1 > 0) && casilleroEsApto(i, j-1) ){
			expandirAireConProbabilidad(i, j-1, probabilidad - 0.1, cantidadAireRestante);
		}
		
		
	}
	
	private boolean casilleroEsApto(int i, int j) {
		/* Chequea que un casillero no tenga recursos y no este
		 * en el area reservada para las bases */
		Coordenada coordenadaActual = new Coordenada(i, j);
		
		Terreno unCasillero = null;
		try {
			unCasillero = mapaGenerado.obtenerCasillero(coordenadaActual);
		} catch (FueraDeLimitesException e) {
			e.printStackTrace();
		}
		
		boolean areaPermitida = true; 
		
		if ( coordenadaActual.distanciaA(posicionBase1) < 3 || coordenadaActual.distanciaA(posicionBase2) < 3 ){
			areaPermitida = false;
		}
		
		return (areaPermitida && unCasillero.sePuedeCaminar());
		
	}

}
