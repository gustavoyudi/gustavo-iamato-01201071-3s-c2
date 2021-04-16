package br.com.bandtec.gustavoiamatoac2.controle;


import br.com.bandtec.gustavoiamatoac2.dominio.Lutador;
import br.com.bandtec.gustavoiamatoac2.repositorio.LutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/lutadores")
public class LutadorController {

    @Autowired
    private LutadorRepository repository;

    @PostMapping
    public ResponseEntity postLutador(@RequestBody @Valid Lutador novoLutador){
        novoLutador.setVida(100.0);
        novoLutador.setConcentracoesRealizadas(0);
        novoLutador.setVivo(true);
        repository.save(novoLutador);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity getLutadoresDesc(){
        return ResponseEntity.status(200).body(repository.listaLutadoresDesc());
    }

    @GetMapping("/contagem-vivos")
    public ResponseEntity getContagem(){
        return ResponseEntity.status(200).body(repository.findByVivo(true).size());
    }

    @PostMapping("{id}/concentrar")
    public ResponseEntity postIdConcentrar(@PathVariable Integer id) {

        if (repository.existsById(id)) {
            Lutador l = repository.getOne(id);
            if (l.getConcentracoesRealizadas() < 3) {
                Double vidaAtualizada = (l.getVida() * 1.15);

                l.setVida(vidaAtualizada);

                Integer concetracoesAtualizadas = l.getConcentracoesRealizadas() + 1;

                l.setConcentracoesRealizadas(concetracoesAtualizadas);

                repository.save(l);

                return ResponseEntity.status(200).build();
            } else {
                return ResponseEntity.status(400).body("Lutador já se concentrou 3 vezes!");
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/mortos")
    public ResponseEntity getMortos(){
        return ResponseEntity.status(200).body(repository.findByVivo(false));
    }
}

