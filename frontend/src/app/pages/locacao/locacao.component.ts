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
import { ClienteService } from '../../services/cliente.service';
import { ItemService } from '../../services/item.service';

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
  dataLocacaoPrevista: string = '';
  dataDevolucaoEfetiva: string = '';
  valorLocacao: number = 0;
  valorMulta: number = 0;
  cliente: Cliente = {} as Cliente;
  item: Item = {} as Item;
  numeroSerie: number = 0;

  selectedCliente!: Cliente;
  clientes: Cliente[] = [];

  selectedItem!: Item;
  itens: Item[] = [];

  constructor(private messageService: MessageService, private locacaoService: LocacaoService, private confirmationService: ConfirmationService, private datePipe: DatePipe, private clienteService: ClienteService, private itemService: ItemService) {}

  ngOnInit(): void {
    this.listAll();
  }

  toggleDialog(){
    this.dataLocacao = ''
    this.dataLocacaoPrevista = ''
    this.dataDevolucaoEfetiva = ''
    this.valorLocacao = 0
    this.valorMulta = 0
    this.cliente = {} as Cliente
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

  listAllClientes(){
    this.clienteService.listAll().subscribe({
      next: (res) => {
        this.clientes = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar titulos' });
      }
    })
  }

  listAllItens(){
    this.itemService.listAll().subscribe({
      next: (res) => {
        this.itens = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar titulos' });
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
    if(!this.dataLocacao || !this.dataDevolucaoEfetiva || !this.dataLocacaoPrevista || !this.valorLocacao || !this.valorMulta || !this.cliente || !this.item){
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
        this.dataLocacaoPrevista = this.datePipe.transform(this.itemToEdit.dtLocacaoPrevista, 'yyyy-MM-dd') || ''
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
      dtLocacaoPrevista: this.dataLocacaoPrevista,
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
      dtLocacaoPrevista: this.dataLocacaoPrevista,
      dtDevolucaoEfetiva: this.dataDevolucaoEfetiva,
      valorCobrado: this.valorLocacao,
      multaCobrada: this.valorMulta,
      cliente: this.selectedCliente,
      item: this.selectedItem
    }

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
