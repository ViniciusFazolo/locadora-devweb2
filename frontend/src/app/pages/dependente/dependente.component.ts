import { Dependente } from './../../interfaces/dependente';
import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { LayoutBaseComponent } from '../../components/layout-base/layout-base.component';
import { MessageService, ConfirmationService } from 'primeng/api';
import { DependenteService } from '../../services/dependente.service';
import { Socio } from '../../interfaces/socio';
import { Locacao } from '../../interfaces/locacao';
import { SocioService } from '../../services/socio.service';

@Component({
  selector: 'app-dependente',
  standalone: true,
  imports: [
    LayoutBaseComponent,
    TableModule,
    DialogComponent,
    ButtonModule,
    ToastModule,
    SplitButtonModule,
    ConfirmPopupModule,
    FormsModule,
    CommonModule,
  ],
  providers: [MessageService, ConfirmationService, DatePipe],
  templateUrl: './dependente.component.html',
  styles: ``,
})
export class DependenteComponent implements OnInit {
  isDialogOpen: boolean = false;
  items!: Dependente[];
  itemToEdit!: Dependente | null;
  dependente: string = '';
  numInscricao: number = 0;
  dtNascimento: string = '';
  sexo: string = '';
  estahAtivo: boolean = true;
  socio: Socio = {} as Socio;
  locacoes: Locacao[] = [];

  selectedSocio!: Socio;
  socios: Socio[] = [];

  constructor(
    private messageService: MessageService,
    private dependenteService: DependenteService,
    private confirmationService: ConfirmationService,
    private datePipe: DatePipe,
    private socioService: SocioService
  ) {}

  ngOnInit(): void {
    this.listAll();
    this.listAllSocios();
  }

  toggleDialog() {
    this.dependente = '';
    this.dtNascimento = '';
    this.sexo = '';
    this.estahAtivo = true;
    this.socio = {} as Socio;
    this.isDialogOpen = !this.isDialogOpen;
  }

  toggleStatus(item: Dependente): void {
    if (item.id) {
      this.dependenteService.updateStatus(item.id).subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Status atualizado com sucesso',
            life: 3000,
          });
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

  listAll() {
    this.dependenteService.listAll().subscribe({
      next: (res) => {
        this.items = res;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Erro ao listar dependentes',
        });
      },
    });
  }

  listAllSocios() {
    this.socioService.listAll().subscribe({
      next: (res) => {
        this.socios = res;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Erro ao listar titulos',
        });
      },
    });
  }

  handleSave() {
    if (!this.dependente || !this.dtNascimento || !this.sexo || !this.socio) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Aviso',
        detail: 'Preencha todos os campos',
      });
      return;
    }

    if (this.itemToEdit) {
      this.edit();
    } else {
      this.create();
    }
  }

  handleEdit(id: number) {
    this.dependenteService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res;
        this.dependente = this.itemToEdit.nome;
        this.numInscricao = this.itemToEdit.numInscricao;
        this.dtNascimento =
          this.datePipe.transform(this.itemToEdit.dtNascimento, 'yyyy-MM-dd') ||
          '';
        this.sexo = this.itemToEdit.sexo;
        this.estahAtivo = this.itemToEdit.estahAtivo;
        this.selectedSocio = this.itemToEdit.socio;
        this.isDialogOpen = true;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Erro ao editar ator',
        });
      },
    });
  }

  handleDelete(event: Event, id: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Realmente deseja excluir este registro?',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: 'p-button-danger p-button-sm',
      accept: () => {
        this.delete(id);
      },
    });
  }

  edit() {
    const obj: Dependente = {
      nome: this.dependente,
      numInscricao: this.numInscricao,
      dtNascimento: this.dtNascimento,
      sexo: this.sexo,
      estahAtivo: this.estahAtivo,
      socio: this.selectedSocio,
      locacoes: this.locacoes,
      id: this.itemToEdit?.id,
    };

    this.dependenteService.update(obj).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Registro atualizado com sucesso',
          life: 3000,
        });
        this.itemToEdit = null;
        this.listAll();
        this.toggleDialog();
      },
      error: (err) => {
        let errorMessage: string;
        if (err.error && err.error.message) {
          errorMessage = err.error.message;
        } else {
          errorMessage = 'Erro ao criar registro';
        }
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: errorMessage,
        });
      },
    });
  }

  create() {
    const obj: Dependente = {
      nome: this.dependente,
      numInscricao: this.numInscricao,
      dtNascimento: this.dtNascimento,
      sexo: this.sexo,
      estahAtivo: this.estahAtivo,
      locacoes: this.locacoes,
      socio: this.selectedSocio,
    };

    this.dependenteService.create(obj).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Registro inserido com sucesso',
          life: 3000,
        });
        this.listAll();
        this.toggleDialog();
      },
      error: (err) => {
        let errorMessage: string;
        if (err.error && err.error.message) {
          errorMessage = err.error.message;
        } else {
          errorMessage = 'Erro ao criar registro';
        }
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: errorMessage,
        });
      },
    });
  }

  delete(id: number) {
    this.dependenteService.delete(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Registro excluído com sucesso',
          life: 3000,
        });
        this.listAll();
      },
      error: (err) => {
        let errorMessage: string;
        try {
          const errorResponse = JSON.parse(err.error);
          errorMessage = errorResponse.message;
        } catch {
          errorMessage = 'Erro ao excluir registro';
        }
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: errorMessage,
        });
      },
    });
  }
}
