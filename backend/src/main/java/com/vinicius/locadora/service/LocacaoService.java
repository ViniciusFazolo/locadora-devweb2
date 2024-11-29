package com.vinicius.locadora.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinicius.locadora.DTO.RequestDTO.LocacaoRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.LocacaoResponseDTO;
import com.vinicius.locadora.exceptions.CancelarLocacaoException;
import com.vinicius.locadora.exceptions.ItemNaoDisponivelException;
import com.vinicius.locadora.exceptions.LocacoesAtrasadasException;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.PreencherTodosCamposException;
import com.vinicius.locadora.mapper.LocacaoMapper;
import com.vinicius.locadora.model.Locacao;
import com.vinicius.locadora.repository.LocacaoRepository;

@Service
public class LocacaoService{
    
    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private LocacaoMapper locacaoMapper;

    public ResponseEntity<LocacaoResponseDTO> salvar(LocacaoRequestDTO request) {
        if(request.dtLocacao() == null || request.dtDevolucaoEfetiva() == null || request.dtDevolucaoPrevista() == null ||
            request.valorCobrado() == null || request.multaCobrada() == null || request.cliente() == null || request.item() == null
        ){
            throw new PreencherTodosCamposException();
        }

        StringBuilder msg = new StringBuilder();
        request.cliente().getLocacao().forEach((lc) -> {
            if(lc.getDtDevolucaoEfetiva().isAfter(lc.getDtDevolucaoPrevista()) && !lc.getDtDevolucaoPrevista().isEqual(lc.getDtDevolucaoEfetiva())){
                msg.append("\nid: " + lc.getId() + " - " + "Devolução prevista: " + lc.getDtDevolucaoPrevista() + " - " + "dias atrasados: " + ChronoUnit.DAYS.between(lc.getDtDevolucaoPrevista(), lc.getDtDevolucaoEfetiva()));
            }
        }); 

        if(!msg.isEmpty()){
            throw new LocacoesAtrasadasException(msg.toString());
        }

        List<Locacao> locacoes = locacaoRepository.findAll();
        locacoes.forEach((lc) -> {
            if(lc.getDtDevolucaoEfetiva() == null && lc.getItem().equals(request.item())){
                throw new ItemNaoDisponivelException(lc.getDtDevolucaoPrevista());
            }
        });

        Locacao obj = new Locacao();
        obj.setCliente(request.cliente());
        obj.setDtDevolucaoPrevista(request.dtDevolucaoPrevista() != null ? request.dtDevolucaoPrevista() : LocalDate.now().plusDays(request.item().getTitulo().getClasse().getPrazoDevolucao()));
        obj.setDtLocacao(LocalDate.now());
        obj.setItem(request.item());
        obj.setMultaCobrada(request.multaCobrada());
        obj.setValorCobrado(request.valorCobrado() > 0 || request.valorCobrado() != null ? request.valorCobrado() : request.item().getTitulo().getClasse().getValor());

        obj = locacaoRepository.save(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(locacaoMapper.toDTO(obj));
    }

    public ResponseEntity<LocacaoResponseDTO> buscarPorId(int id){
        Locacao obj = locacaoRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a locação de id: " + id));
        return ResponseEntity.ok().body(locacaoMapper.toDTO(obj));
    }

    public ResponseEntity<List<LocacaoResponseDTO>> buscarTodos(){
        return ResponseEntity.ok().body(locacaoRepository.findAll().stream().map(locacaoMapper::toDTO).collect(Collectors.toList()));
    }       

    public ResponseEntity<LocacaoResponseDTO> atualizar(LocacaoRequestDTO request){
        if(request.dtLocacao() == null || request.dtDevolucaoEfetiva() == null || request.dtDevolucaoPrevista() == null ||
            request.valorCobrado() == null || request.multaCobrada() == null || request.cliente() == null || request.item() == null
        ){
            throw new PreencherTodosCamposException();
        }

        StringBuilder msg = new StringBuilder();
        request.cliente().getLocacao().forEach((lc) -> {
            if(lc.getDtDevolucaoEfetiva().isAfter(lc.getDtDevolucaoPrevista()) && !lc.getDtDevolucaoPrevista().isEqual(lc.getDtDevolucaoEfetiva())){
                msg.append("\nid: " + lc.getId() + " - " + "Devolução prevista: " + lc.getDtDevolucaoPrevista() + " - " + "dias atrasados: " + ChronoUnit.DAYS.between(lc.getDtDevolucaoPrevista(), lc.getDtDevolucaoEfetiva()));
            }
        }); 

        if(!msg.isEmpty()){
            throw new LocacoesAtrasadasException(msg.toString());
        }

        List<Locacao> locacoes = locacaoRepository.findAll();
        locacoes.forEach((lc) -> {
            if(lc.getDtDevolucaoEfetiva() == null && lc.getItem().equals(request.item())){
                throw new ItemNaoDisponivelException(lc.getDtDevolucaoPrevista());
            }
        });

        Locacao obj = locacaoRepository.findById(request.id()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a locação de id:" + request.id()));
        obj.setCliente(request.cliente());
        obj.setDtDevolucaoPrevista(request.dtDevolucaoPrevista() != null ? request.dtDevolucaoPrevista() : LocalDate.now().plusDays(request.item().getTitulo().getClasse().getPrazoDevolucao()));
        obj.setItem(request.item());
        obj.setMultaCobrada(request.multaCobrada());
        obj.setValorCobrado(request.valorCobrado() > 0 || request.valorCobrado() != null ? request.valorCobrado() : request.item().getTitulo().getClasse().getValor());
        obj.setItem(request.item());

        return ResponseEntity.ok().body(locacaoMapper.toDTO(obj));
    }

    public ResponseEntity<String> deletar(int id){
        Locacao obj = locacaoRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a locação de id:" + id));
        locacaoRepository.delete(obj);
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }

    public ResponseEntity<String> cancelar(int id){
        Locacao obj = locacaoRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a locação de id:" + id));
    
        if(obj.getValorCobrado() != null){
            throw new CancelarLocacaoException();
        }

        locacaoRepository.deleteById(id);
        return ResponseEntity.ok().body("Cancelado com sucesso");
    }
}
