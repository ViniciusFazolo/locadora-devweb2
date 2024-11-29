import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MessageService, ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { LayoutBaseComponent } from '../../components/layout-base/layout-base.component';
import { Locacao } from '../../interfaces/locacao';
import { LocacaoService } from '../../services/locacao.service';
import { Item } from '../../interfaces/item';
import { Cliente } from '../../interfaces/cliente';
import { ItemService } from '../../services/item.service';
import { SocioService } from '../../services/socio.service';
import { DependenteService } from '../../services/dependente.service';
import { Socio } from '../../interfaces/socio';
import { Dependente } from '../../interfaces/dependente';

@Component({
  selector: 'app-locacao',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule, CommonModule, DatePipe],
  templateUrl: './locacao.component.html',
  providers: [MessageService, ConfirmationService, DatePipe],
  styles: ``
})
export class LocacaoComponent implements OnInit{
  isDialogOpen: boolean = false;
  isDialogDevolucaoOpen: boolean = false;
  tituloDevolucao: string = 'Devolução de item';
  items!:  Locacao[]
  itemToEdit!: Locacao | null;
  dataLocacao: string = '';
  dataDevolucaoPrevista: string = '';
  dataDevolucaoEfetiva: string = '';
  valorLocacao: number = 0;
  valorMulta: number = 0;
  cliente: Socio | Dependente = {} as Socio | Dependente;
  item: Item = {} as Item;
  numeroSerie: number = 0;

  selectedCliente!: Cliente;
  clientes: Cliente[] = [];

  socios: Socio[] = [];
  dependentes: Dependente[] = [];

  selectedItem!: Item;
  itens: Item[] = [];

  constructor(private messageService: MessageService, private locacaoService: LocacaoService, private confirmationService: ConfirmationService, private datePipe: DatePipe, private socioService: SocioService, private itemService: ItemService, private dependenteService: DependenteService) {}

  ngOnInit(): void {
    this.listAll();
    this.listAllSocios();
    this.listAllDependentes();
    this.listAllItens();
  }

  toggleDialog(){
    this.dataLocacao = ''
    this.dataDevolucaoPrevista = ''
    this.dataDevolucaoEfetiva = ''
    this.valorLocacao = 0
    this.valorMulta = 0
    this.cliente = {} as Socio | Dependente
    this.item = {} as Item
    this.isDialogOpen = !this.isDialogOpen
  }

  toggleDevolucaoDialog() {
    this.numeroSerie = 0;
    this.isDialogDevolucaoOpen = !this.isDialogDevolucaoOpen;
  }

  listAll(){
    this.locacaoService.listAll().subscribe({
      next: (res) => {
        this.items = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar locacoes' });
      }
    })
  }

  listAllSocios(){
    this.socioService.listAll().subscribe({
      next: (res) => {
        console.log(res);
        this.socios = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar socios' });
      }
    })
  }

  listAllDependentes(){
    this.dependenteService.listAll().subscribe({
      next: (res) => {
        this.dependentes = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar dependentes' });
      }
    })
  }

  listAllItens(){
    this.itemService.listAll().subscribe({
      next: (res) => {
        this.itens = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar itens' });
      }
    })
  }

  handleDevolucao() {
    if (this.numeroSerie) {
      this.toggleDevolucaoDialog();
    } else {
      console.error('Por favor, insira um número de série válido.');
    }
  }

  handleSave(){
    if(!this.dataLocacao || !this.dataDevolucaoEfetiva || !this.dataDevolucaoPrevista || !this.valorLocacao || !this.valorMulta || !this.cliente || !this.item){
      this.messageService.add({ severity: 'warn', summary: 'Aviso', detail: 'Preencha todos os campos' });
      return
    }

    if(this.itemToEdit){
      this.edit()
    }else{
      this.create()
    }
  }

  handleEdit(id: number){
    this.locacaoService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res
        this.dataLocacao = this.datePipe.transform(this.itemToEdit.dtLocacao, 'yyyy-MM-dd') || ''
        this.dataDevolucaoPrevista = this.datePipe.transform(this.itemToEdit.dtDevolucaoPrevista, 'yyyy-MM-dd') || ''
        this.dataDevolucaoEfetiva = this.datePipe.transform(this.itemToEdit.dtDevolucaoEfetiva, 'yyyy-MM-dd') || ''
        this.valorLocacao = this.itemToEdit.valorCobrado;
        this.valorMulta = this.itemToEdit.multaCobrada;
        this.selectedCliente = this.itemToEdit.cliente;
        this.selectedItem = this.itemToEdit.item;
        this.isDialogOpen = true
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar item' });
      }
    })
  }

  handleDelete(event: Event, id: number){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Realmente deseja excluir este registro?',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: 'p-button-danger p-button-sm',
      accept: () => {
        this.delete(id)
      },
    });
  }

  edit(){
    const obj: Locacao = {
      id: this.itemToEdit?.id,
      dtLocacao: this.dataLocacao,
      dtDevolucaoPrevista: this.dataDevolucaoPrevista,
      dtDevolucaoEfetiva: this.dataDevolucaoEfetiva,
      valorCobrado: this.valorLocacao,
      multaCobrada: this.valorMulta,
      cliente: this.selectedCliente,
      item: this.selectedItem
    }

    this.locacaoService.update(obj).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Registro atualizado com sucesso', life: 3000 });
        this.itemToEdit = null
        this.listAll()
        this.toggleDialog()
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao atualizar registro' });
      }
    })
  }

  create(){
    const obj: Locacao = {
      dtLocacao: this.dataLocacao,
      dtDevolucaoPrevista: this.dataDevolucaoPrevista,
      dtDevolucaoEfetiva: this.dataDevolucaoEfetiva,
      valorCobrado: this.valorLocacao,
      multaCobrada: this.valorMulta,
      cliente: this.selectedCliente,
      item: this.selectedItem
    }
    console.log(obj);
    this.locacaoService.create(obj).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Registro inserido com sucesso', life: 3000 });
        this.listAll()
        this.toggleDialog()
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao inserir registro' });
      }
    })
  }

  delete(id: number){
    this.locacaoService.delete(id).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Registro excluído com sucesso', life: 3000 });
        this.listAll()
      },
      error: (err) => {
        let errorMessage: string;
        try{
          const errorResponse = JSON.parse(err.error);
          errorMessage = errorResponse.message;

        }catch{
          errorMessage = 'Erro ao excluir registro';
        }
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: errorMessage });
      }
    })
  }

}
