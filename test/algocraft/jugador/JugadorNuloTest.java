package algocraft.jugador;

import static org.junit.Assert.*;

import org.junit.Test;

import algocraft.jugador.JugadorNulo;
import algocraft.stats.Recurso;

public class JugadorNuloTest {

	@Test
	public void testPedirRecursoAJugadorNuloDevuelveUnRecursoNoLimitante(){
		JugadorNulo jugadorNulo= new JugadorNulo();
		Recurso recursoObtenido=jugadorNulo.getRecursos();
		
		assertEquals(1000,recursoObtenido.obtenerMineral() );
		assertEquals(1000,recursoObtenido.obtenerGas() );
	}
	
	@Test
	public void testPedirPoblacionAJugadorNuloDevuelvePoblacionConEspacio5(){
		JugadorNulo jugadorNulo= new JugadorNulo();
		
		assertEquals(5, jugadorNulo.getPoblacionMaxima() );
	}
}
