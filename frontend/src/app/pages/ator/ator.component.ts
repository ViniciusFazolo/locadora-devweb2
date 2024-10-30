import { Component, OnInit } from '@angular/core';
import { LayoutBaseComponent } from '../../components/layout-base/layout-base.component';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { Ator } from '../../interfaces/ator';
import { AtorService } from '../../services/ator.service';
import { FormsModule } from '@angular/forms';
import { Titulo } from '../../interfaces/titulo';

@Component({
  selector: 'app-ator',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule],
  templateUrl: './ator.component.html',
  providers: [MessageService, ConfirmationService],
  styles: ``
})
export class AtorComponent implements OnInit{
  isDialogOpen: boolean = false;
  items!: Ator[]
  itemToEdit!: Ator | null;
  ator: string = ''
  titulos: Titulo[] = []

  constructor(private messageService: MessageService, private atorService: AtorService, private confirmationService: ConfirmationService) {}

  ngOnInit(): void {
    this.listAll()
  }

  toggleDialog(){
    this.ator = ''
    this.isDialogOpen = !this.isDialogOpen
  }

  listAll(){
    this.atorService.listAll().subscribe({
      next: (res) => {
        this.items = res;
        console.log(this.items)
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar atores' });
      }
    })
  }

  handleSave(){
    if(!this.ator){
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
    this.atorService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res
        this.ator = this.itemToEdit.nome;
        this.titulos = this.itemToEdit.titulo;
        this.isDialogOpen = true
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar ator' });
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
    const obj: Ator = {
      nome: this.ator,
      titulo: this.titulos,
      id: this.itemToEdit?.id
    }

    this.atorService.update(obj).subscribe({
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
    const obj: Ator = {
      nome: this.ator,
      titulo: this.titulos
    }

    this.atorService.create(obj).subscribe({
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
    this.atorService.delete(id).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Registro excluído com sucesso', life: 3000 });
        this.listAll()
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir registro' });
      }
    })
  }
}
