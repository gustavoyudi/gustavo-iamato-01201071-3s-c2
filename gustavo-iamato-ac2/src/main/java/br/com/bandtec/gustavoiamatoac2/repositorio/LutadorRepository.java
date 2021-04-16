package br.com.bandtec.gustavoiamatoac2.repositorio;

import br.com.bandtec.gustavoiamatoac2.dominio.Lutador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LutadorRepository extends JpaRepository <Lutador, Integer> {

    @Query(value = "select * from Lutador order by forca_golpe desc", nativeQuery = true)
    List<Lutador> listaLutadoresDesc();

    List<Lutador> findByVivo(Boolean vivo);

}
