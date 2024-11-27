import { Component, OnInit } from '@angular/core';
import { LayoutBaseComponent } from "../../components/layout-base/layout-base.component";
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { Item } from '../../interfaces/item';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ItemService } from '../../services/item.service';
import { Titulo } from '../../interfaces/titulo';
import { TituloService } from '../../services/titulo.service';
import { CommonModule, DatePipe } from '@angular/common';
import { Locacao } from '../../interfaces/locacao';


@Component({
  selector: 'app-item',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule, CommonModule, DatePipe],
  providers: [MessageService, ConfirmationService, DatePipe],
  templateUrl: './item.component.html',
  styles: ``
})
export class ItemComponent implements OnInit{
  isDialogOpen: boolean = false;
  items!:  Item[]
  itemToEdit!: Item | null;
  numSerie: number = 0;
  dtAquisicao: string = '';
  tipoItem: string = '';
  titulos: Titulo[] = [];
  locacoes: Locacao[] = [];

  selectedTitulo!: Titulo;

  constructor(private messageService: MessageService, private itemService: ItemService, private confirmationService: ConfirmationService, private tituloService: TituloService, private datePipe: DatePipe) {}


  ngOnInit(): void {
    this.listAll();
    this.listAllTitulos();
  }

  toggleDialog(){
    this.numSerie = 0
    this.dtAquisicao = ''
    this.tipoItem = ''
    this.isDialogOpen = !this.isDialogOpen
  }

  listAll(){
    this.itemService.listAll().subscribe({
      next: (res) => {
        this.items = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar itens' });
      }
    })
  }

  listAllTitulos(){
    this.tituloService.listAll().subscribe({
      next: (res) => {
        this.titulos = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar titulos' });
      }
    })
  }

  handleSave(){
    if(!this.numSerie || !this.dtAquisicao || !this.tipoItem){
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
    this.itemService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res
        this.numSerie = this.itemToEdit.numSerie;
        this.dtAquisicao = this.datePipe.transform(this.itemToEdit.dtAquisicao, 'yyyy-MM-dd') || ''
        this.tipoItem = this.itemToEdit.tipoItem;
        this.selectedTitulo = this.itemToEdit.titulo;
        this.isDialogOpen = true

        console.log('Título selecionado:', this.selectedTitulo);
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
    const obj: Item = {
      numSerie: this.numSerie,
      dtAquisicao: this.dtAquisicao,
      tipoItem: this.tipoItem,
      titulo: this.selectedTitulo,
      locacoes: this.locacoes,
      id: this.itemToEdit?.id
    }

    this.itemService.update(obj).subscribe({
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
    const obj: Item = {
      numSerie: this.numSerie,
      dtAquisicao: this.dtAquisicao,
      tipoItem: this.tipoItem,
      titulo: this.selectedTitulo,
      locacoes: this.locacoes
    }

    this.itemService.create(obj).subscribe({
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
    this.itemService.delete(id).subscribe({
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
