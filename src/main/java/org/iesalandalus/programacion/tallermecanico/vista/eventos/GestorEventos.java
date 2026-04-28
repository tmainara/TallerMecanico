package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private Map<Evento, List<ReceptorEventos>> receptores;

    public GestorEventos (Evento... eventos){
        receptores = new EnumMap<>(Evento.class);
        for (Evento evento : eventos) {
            receptores.put(evento, new ArrayList<>());
        }
    }
    public void suscribir(ReceptorEventos receptor, Evento... evento) {
        for (Evento e : evento) {
            List<ReceptorEventos> receptoresEvento = receptores.get(e);
            if (receptoresEvento != null) {
                receptoresEvento.add(receptor);
            }
        }
    }
    public void desuscribir(ReceptorEventos receptor, Evento... evento) {
        for (Evento e : evento) {
            List<ReceptorEventos> receptoresEvento = receptores.get(e);
            if (receptoresEvento != null) {
                receptoresEvento.remove(receptor);
            }
        }
    }
    public void notificar(Evento evento) {
        List<ReceptorEventos> receptoresEvento = receptores.get(evento);
        if (receptoresEvento != null) {
            for (ReceptorEventos receptor : receptoresEvento) {
                receptor.actualizar(evento);
            }
        }
    }
}
