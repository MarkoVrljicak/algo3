package modelo.unidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import modelo.exception.PropiedadNoEstaEnJuegoException;
import modelo.magias.AtaqueMagico;
import modelo.stats.Magia;

abstract public class UnidadMagica extends Unidad {
	
	protected Collection<AtaqueMagico> magias = new ArrayList<AtaqueMagico>();
	protected Magia magia;
	protected void inicializar() {
		super.inicializar();
		this.setearMagia();
	}

	abstract protected void setearMagia();
	
	public void iniciarTurno() throws PropiedadNoEstaEnJuegoException{
		this.magia.regenerar();
		
		Iterator<AtaqueMagico> iter = magias.iterator();
		while(iter.hasNext()){
			AtaqueMagico siguienteMagia = iter.next();
			if(siguienteMagia.caduco()){
				magias.remove(siguienteMagia);
				iter = magias.iterator();
			} 
		}
		
		iter = magias.iterator();
		while(iter.hasNext()) {
			AtaqueMagico siguienteMagia = iter.next();
			
			siguienteMagia.ejecutar();
			
		}
	}
	
	public int getMagiaActual(){
		return this.magia.actual();
	}
	
	public int getMagiaMaxima(){
		return this.magia.max();
	}
	
	@Override
	public void quitarEnergia() {
		this.magia.quitarEnergia();
		
	}	
	
	
	
}
