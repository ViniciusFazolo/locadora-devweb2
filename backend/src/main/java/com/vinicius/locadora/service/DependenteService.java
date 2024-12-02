package com.vinicius.locadora.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vinicius.locadora.model.Dependente;
import com.vinicius.locadora.exceptions.LimiteDeDependentesException;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.RelacionamentoException;
import com.vinicius.locadora.repository.DependenteRepository;
import com.vinicius.locadora.repository.LocacaoRepository;

@Service
public class DependenteService{
    
    @Autowired
    private DependenteRepository socioRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    public ResponseEntity<Dependente> salvar(Dependente request) {
        Dependente obj = new Dependente();
        obj.setDtNascimento(request.getDtNascimento());
        obj.setEstahAtivo(request.getEstahAtivo());
        obj.setLocacao(request.getLocacao());
        obj.setNome(request.getNome());
        obj.setSexo(request.getSexo());

        List<Dependente> dependentes = socioRepository.findBySocio(request.getSocio());
        if(dependentes.size() >= 3){
            throw new LimiteDeDependentesException();
        }
        obj.setSocio(request.getSocio());

        int numInscricao = gerarNumeroInscricao();
        obj.setNumInscricao(numInscricao);  
        obj = socioRepository.save(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    private int gerarNumeroInscricao() {
        Random rand = new Random();
        int numInscricao;
        boolean numeroExiste;

        do {
            numInscricao = rand.nextInt(90000) + 10000;

            numeroExiste = socioRepository.existsByNumInscricao(numInscricao);
        } while (numeroExiste); 
        return numInscricao;
    }

    public ResponseEntity<Dependente> buscarPorId(int id){
        Dependente obj = socioRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + id));
        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<List<Dependente>> buscarTodos(){
        List<Dependente> obj = socioRepository.findAll();
        return ResponseEntity.ok().body(obj);
    }       

    public ResponseEntity<Dependente> atualizar(Dependente request){
        Dependente obj = socioRepository.findById(request.getId()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + request.getId()));
        obj.setDtNascimento(request.getDtNascimento());
        obj.setEstahAtivo(request.getEstahAtivo());
        obj.setLocacao(request.getLocacao());
        obj.setNome(request.getNome());
        obj.setNumInscricao(request.getNumInscricao());
        obj.setSexo(request.getSexo());

        List<Dependente> dependentes = socioRepository.findBySocio(request.getSocio());
        if(dependentes.size() >= 3){
            throw new LimiteDeDependentesException();
        }
        obj.setSocio(request.getSocio());
        obj = socioRepository.save(obj);
        
        socioRepository.save(obj);

        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<String> deletar(int id){
        Dependente obj = socioRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id:" + id));
        
        obj.getLocacao().forEach((lc) -> {
            if(lc.getDtDevolucaoEfetiva() == null){
                throw new RelacionamentoException("Não é possivel excluir o cliente, " + obj.getNome() + ". Há locações realizadas que ainda não foram entregues.");
            }
        });
    
        locacaoRepository.deleteAll(obj.getLocacao());
        socioRepository.delete(obj);
     
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }

    public ResponseEntity<Void> alterarStatus(int id) {
        Optional<Dependente> dependenteOpt = socioRepository.findById(id);
        if (dependenteOpt.isPresent()) {
            Dependente dependente = dependenteOpt.get();
            dependente.setEstahAtivo(!dependente.getEstahAtivo());
            socioRepository.save(dependente);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

