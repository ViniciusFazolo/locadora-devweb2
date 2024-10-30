import { Component, OnInit } from '@angular/core';
import { LayoutBaseComponent } from '../../components/layout-base/layout-base.component';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { ClasseService } from '../../services/classe.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Classe } from '../../interfaces/classe';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-classe',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule, DatePipe],
  templateUrl: './classe.component.html',
  providers: [MessageService, ConfirmationService, DatePipe],
  styles: ``
})
export class ClasseComponent implements OnInit{
  isDialogOpen: boolean = false;
  items!: Classe[]
  itemToEdit!: Classe | null;
  classe: string = '';
  prazoDevolucao: string = '';
  valor: number = 0;

  constructor(private messageService: MessageService, private classeService: ClasseService, private confirmationService: ConfirmationService, private datePipe: DatePipe) {}

  ngOnInit(): void {
    this.listAll()
  }

  toggleDialog(){
    this.classe = ''
    this.prazoDevolucao = ''
    this.valor = 0
    this.isDialogOpen = !this.isDialogOpen
  }

  listAll(){
    this.classeService.listAll().subscribe({
      next: (res) => {
        res
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar classes' });
      }
    })
  }

  handleSave(){
    if(!this.classe){
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
    this.classeService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res;
        this.classe = this.itemToEdit.nome;
        this.valor = this.itemToEdit.valor;
        this.prazoDevolucao = this.datePipe.transform(this.itemToEdit.prazoDevolucao, 'yyyy-MM-dd') || ''
        this.isDialogOpen = true
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar classe' });
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
    const obj: Classe = {
      nome: this.classe,
      prazoDevolucao: this.prazoDevolucao,
      valor: this.valor,
      id: this.itemToEdit?.id
    }

    this.classeService.update(obj).subscribe({
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
    const obj: Classe = {
      nome: this.classe,
      prazoDevolucao: this.prazoDevolucao,
      valor: this.valor,
    }

    this.classeService.create(obj).subscribe({
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
    this.classeService.delete(id).subscribe({
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
