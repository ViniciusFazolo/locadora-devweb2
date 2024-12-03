import { Component, OnInit } from '@angular/core';
import { LayoutBaseComponent } from "../../components/layout-base/layout-base.component";
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { MessageService, ConfirmationService } from 'primeng/api';
import { SocioService } from '../../services/socio.service';
import { Socio } from '../../interfaces/socio';
import { Dependente } from '../../interfaces/dependente';
import { Locacao } from '../../interfaces/locacao';

@Component({
  selector: 'app-socio',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule, CommonModule, DatePipe],
  providers: [MessageService, ConfirmationService, DatePipe],
  templateUrl: './socio.component.html',
  styles: ``
})
export class SocioComponent implements OnInit{
  isDialogOpen: boolean = false;
  items!: Socio[];
  itemToEdit!: Socio | null;
  socio: string = '';
  numInscricao: number = 0;
  dtNascimento: string = '';
  sexo: string = '';
  estahAtivo: boolean = true;
  cpf: string = '';
  endereco: string = '';
  tel: number = 0;
  dependentes: Dependente[] = [];
  locacoes: Locacao[] = [];

  constructor(private messageService: MessageService, private socioService: SocioService, private confirmationService: ConfirmationService, private datePipe: DatePipe) {}

  ngOnInit(): void {
    this.listAll();
  }

  toggleDialog(){
    this.socio = ''
    this.dtNascimento = ''
    this.sexo = ''
    this.endereco = ''
    this.cpf = ''
    this.tel = 0
    this.isDialogOpen = !this.isDialogOpen
  }

  toggleStatus(item: Dependente): void {
    if (item.id) {
      this.socioService.updateStatus(item.id).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Status atualizado com sucesso', life: 3000 });
          item.estahAtivo = !item.estahAtivo;
        },
        error: (err) => {
          console.error('Erro ao atualizar status', err);
        },
      });
    } else {
      console.error('ID do dependente não definido.');
    }
  }

  listAll(){
    this.socioService.listAll().subscribe({
      next: (res) => {
        this.items = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar socios' });
      }
    })
  }

  handleSave(){
    if(!this.socio || !this.dtNascimento || !this.sexo){
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
    this.socioService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res
        this.socio = this.itemToEdit.nome;
        this.numInscricao = this.itemToEdit.numInscricao;
        this.dtNascimento = this.datePipe.transform(this.itemToEdit.dtNascimento, 'yyyy-MM-dd') || ''
        this.sexo = this.itemToEdit.sexo;
        this.estahAtivo = this.itemToEdit.estahAtivo;
        this.isDialogOpen = true
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar sócio' });
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
    const obj: Socio = {
      nome: this.socio,
      numInscricao: this.numInscricao,
      dtNascimento: this.dtNascimento,
      sexo: this.sexo,
      estahAtivo: this.estahAtivo,
      cpf: this.cpf,
      endereco: this.endereco,
      tel: this.tel,
      dependentes: this.dependentes,
      locacoes: this.locacoes,
      id: this.itemToEdit?.id
    }

    this.socioService.update(obj).subscribe({
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
    const obj: Socio = {
      nome: this.socio,
      numInscricao: this.numInscricao,
      dtNascimento: this.dtNascimento,
      sexo: this.sexo,
      estahAtivo: this.estahAtivo,
      cpf: this.cpf,
      endereco: this.endereco,
      tel: this.tel,
      dependentes: this.dependentes,
      locacoes: this.locacoes
    }

    this.socioService.create(obj).subscribe({
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
    this.socioService.delete(id).subscribe({
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
