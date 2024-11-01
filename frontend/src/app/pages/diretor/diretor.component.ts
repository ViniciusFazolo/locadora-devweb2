import { Component, OnInit } from '@angular/core';
import { LayoutBaseComponent } from '../../components/layout-base/layout-base.component';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { Diretor } from '../../interfaces/diretor';
import { DiretorService } from '../../services/diretor.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Titulo } from '../../interfaces/titulo';

@Component({
  selector: 'app-diretor',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule],
  templateUrl: './diretor.component.html',
  providers: [MessageService, ConfirmationService],
  styles: ``
})
export class DiretorComponent {
  isDialogOpen: boolean = false;
  items!: Diretor[]
  itemToEdit!: Diretor | null;
  diretor: string = ''
  titulos: Titulo[] = []

  constructor(private messageService: MessageService, private diretorService: DiretorService, private confirmationService: ConfirmationService) {}

  ngOnInit(): void {
    this.listAll()
  }

  toggleDialog(){
    this.diretor = ''
    this.isDialogOpen = !this.isDialogOpen
  }

  listAll(){
    this.diretorService.listAll().subscribe({
      next: (res) => {
        this.items = res
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar diretores' });
      }
    })
  }

  handleSave(){
    if(!this.diretor){
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
    this.diretorService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res
        this.diretor = this.itemToEdit.nome;
        this.isDialogOpen = true
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar diretor' });
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
    const obj: Diretor = {
      nome: this.diretor,
      titulos: this.titulos,
      id: this.itemToEdit?.id
    }

    this.diretorService.update(obj).subscribe({
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
    const obj: Diretor = {
      nome: this.diretor,
      titulos: this.titulos
    }

    this.diretorService.create(obj).subscribe({
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
    this.diretorService.delete(id).subscribe({
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
