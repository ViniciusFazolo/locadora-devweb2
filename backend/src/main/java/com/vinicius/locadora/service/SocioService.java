package com.vinicius.locadora.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinicius.locadora.model.Dependente;
import com.vinicius.locadora.model.Socio;
import com.vinicius.locadora.exceptions.LimiteDeDependentesException;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.RelacionamentoException;
import com.vinicius.locadora.repository.DependenteRepository;
import com.vinicius.locadora.repository.LocacaoRepository;
import com.vinicius.locadora.repository.SocioRepository;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    public ResponseEntity<Socio> salvar(Socio request) {
        Socio obj = new Socio();
        obj.setCpf(request.getCpf());
        obj.setDtNascimento(request.getDtNascimento());
        obj.setEndereco(request.getEndereco());
        obj.setEstahAtivo(true);
        obj.setLocacao(request.getLocacao());
        obj.setNome(request.getNome());
        obj.setSexo(request.getSexo());
        obj.setTel(request.getTel());

        int numInscricao = gerarNumeroInscricao();
        obj.setNumInscricao(numInscricao);

        int[] i = new int[1];
        request.getDependentes().forEach(dp -> {
            if (dp.getEstahAtivo()) {
                i[0]++;
            }
        });

        if (i[0] > 3) {
            throw new LimiteDeDependentesException();
        }

        if (obj.getEstahAtivo() == false) {
            request.getDependentes().forEach((dp) -> {
                dp.setEstahAtivo(false);
            });
        }

        obj.setDependentes(request.getDependentes());

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

    public ResponseEntity<Socio> buscarPorId(int id) {
        Socio obj = socioRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + id));
        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<List<Socio>> buscarTodos() {
        List<Socio> obj = socioRepository.findAll();
        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<Socio> atualizar(Socio request) {
        Socio obj = socioRepository.findById(request.getId()).orElseThrow(
                () -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + request.getId()));
        obj.setCpf(request.getCpf());
        obj.setDtNascimento(request.getDtNascimento());
        obj.setEndereco(request.getEndereco());
        obj.setEstahAtivo(request.getEstahAtivo());
        obj.setLocacao(request.getLocacao());
        obj.setNome(request.getNome());
        obj.setNumInscricao(request.getNumInscricao());
        obj.setSexo(request.getSexo());
        obj.setTel(request.getTel());
        obj = socioRepository.save(obj);

        int[] i = new int[1];
        request.getDependentes().forEach(dp -> {
            if (dp.getEstahAtivo()) {
                i[0]++;
            }
        });

        if (i[0] > 3) {
            throw new LimiteDeDependentesException();
        }

        if (obj.getEstahAtivo() == false) {
            request.getDependentes().forEach((dp) -> {
                dp.setEstahAtivo(false);
            });
        }

        obj.setDependentes(request.getDependentes());

        socioRepository.save(obj);
        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<String> deletar(int id) {
        Socio obj = socioRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id:" + id));

        obj.getLocacao().forEach((lc) -> {
            if (lc.getDtDevolucaoEfetiva() == null) {
                throw new RelacionamentoException("Não é possivel excluir o cliente, " + obj.getNome()
                        + ". Há locações realizadas que ainda não foram entregues.");
            }
        });

        locacaoRepository.deleteAll(obj.getLocacao());
        dependenteRepository.deleteAll(obj.getDependentes());
        socioRepository.delete(obj);
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }

    public ResponseEntity<Void> alterarStatus(int id) {
        Optional<Socio> socioOpt = socioRepository.findById(id);

        if (socioOpt.isPresent()) {
            Socio socio = socioOpt.get();

            if (socio.getEstahAtivo()) {
                socio.setEstahAtivo(false);
                socioRepository.save(socio);

                // Desativa todos os dependentes
                List<Dependente> dependentes = dependenteRepository.findBySocio(socio);
                for (Dependente dependente : dependentes) {
                    dependente.setEstahAtivo(false);
                    dependenteRepository.save(dependente);
                }
            } else {
                socio.setEstahAtivo(true);
                socioRepository.save(socio);

                // Ativa até 3 dependentes
                List<Dependente> dependentes = dependenteRepository.findBySocio(socio);
                int count = 0;
                for (Dependente dependente : dependentes) {
                    if (!dependente.getEstahAtivo() && count < 3) {
                        dependente.setEstahAtivo(true);
                        dependenteRepository.save(dependente);
                        count++;
                    }
                }
            }

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
