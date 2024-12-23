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
import { CommonModule, DatePipe } from '@angular/common';
import { Titulo } from '../../interfaces/titulo';
import { TituloService } from '../../services/titulo.service';

@Component({
  selector: 'app-classe',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule, CommonModule],
  templateUrl: './classe.component.html',
  providers: [MessageService, ConfirmationService, DatePipe],
  styles: ``
})
export class ClasseComponent implements OnInit{
  isDialogOpen: boolean = false;
  items!: Classe[]
  itemToEdit!: Classe | null;
  classe: string = '';
  prazoDevolucao: number = 0;
  valor: number = 0;
  titulos: Titulo[] = [];

  constructor(private messageService: MessageService, private classeService: ClasseService, private confirmationService: ConfirmationService, private datePipe: DatePipe, private tituloService: TituloService) {}

  ngOnInit(): void {
    this.listAll()
    this.listAllTitulos()
  }

  toggleDialog(){
    this.classe = ''
    this.prazoDevolucao = 0
    this.valor = 0
    this.isDialogOpen = !this.isDialogOpen
  }

  listAll(){
    this.classeService.listAll().subscribe({
      next: (res) => {
        this.items = res
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar classes' });
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
    if(!this.classe || !this.prazoDevolucao || !this.valor){
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
        this.prazoDevolucao = this.itemToEdit.prazoDevolucao;
        this.titulos = this.itemToEdit.titulos;
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
      titulos: this.titulos,
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
      titulos: this.titulos
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
