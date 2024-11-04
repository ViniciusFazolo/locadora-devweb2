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
import { Titulo } from '../../interfaces/titulo';
import { Item } from '../../interfaces/item';
import { Ator } from '../../interfaces/ator';
import { Diretor } from '../../interfaces/diretor';
import { Classe } from '../../interfaces/classe';
import { TituloService } from '../../services/titulo.service';
import { CommonModule, DatePipe } from '@angular/common';
import { ItemService } from '../../services/item.service';
import { AtorService } from '../../services/ator.service';
import { DiretorService } from '../../services/diretor.service';
import { ClasseService } from '../../services/classe.service';

@Component({
  selector: 'app-titulo',
  standalone: true,
  imports: [LayoutBaseComponent, TableModule, DialogComponent, ButtonModule, ToastModule, SplitButtonModule, ConfirmPopupModule, FormsModule, DatePipe, CommonModule],
  providers: [MessageService, ConfirmationService, DatePipe],
  templateUrl: './titulo.component.html',
  styles: ``
})
export class TituloComponent implements OnInit{
  isDialogOpen: boolean = false;
  titulos!: Titulo[];
  itemToEdit!: Titulo | null;
  nome: string = '';
  ano: string = '';
  sinopse: string = '';
  categoria: string = '';
  items: Item[] = [];

  atores: Ator[] = [];
  selectedAtores!: Ator[];
  diretores: Diretor[] = [];
  selectedDiretor!: Diretor;
  classes: Classe[] = [];
  selectedClasse!: Classe;

  constructor(private messageService: MessageService, private tituloService: TituloService, private confirmationService: ConfirmationService, private datePipe: DatePipe, private itemService: ItemService, private atorService: AtorService, private diretorService: DiretorService, private classeService: ClasseService) {}

  ngOnInit(): void {
    this.listAll();
    this.listAllItems();
    this.listAllAtores();
    this.listAllDiretores();
    this.listAllClasses();
  }

  toggleDialog(){
    this.nome = ''
    this.ano = ''
    this.sinopse = ''
    this.categoria = ''
    this.isDialogOpen = !this.isDialogOpen
  }

  listAll(){
    this.tituloService.listAll().subscribe({
      next: (res) => {
        this.titulos = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar titulos' });
      }
    })
  }

  listAllItems(){
    this.itemService.listAll().subscribe({
      next: (res) => {
        this.items = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar itens' });
      }
    })
  }

  listAllAtores(){
    this.atorService.listAll().subscribe({
      next: (res) => {
        this.atores = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar atores' });
      }
    })
  }

  listAllDiretores(){
    this.diretorService.listAll().subscribe({
      next: (res) => {
        this.diretores = res;
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar diretores' });
      }
    })
  }

  listAllClasses(){
    this.classeService.listAll().subscribe({
      next: (res) => {
        this.classes = res;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar classes' });
      }
    })
  }

  handleSave(){
    if(!this.nome || !this.ano || !this.sinopse || !this.categoria || !this.selectedAtores || !this.selectedDiretor || !this.selectedClasse){
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
    this.tituloService.listById(id).subscribe({
      next: (res) => {
        this.itemToEdit = res
        this.nome = this.itemToEdit.nome;
        this.ano = this.datePipe.transform(this.itemToEdit.ano, 'yyyy') || ''
        this.sinopse = this.itemToEdit.sinopse;
        this.categoria = this.itemToEdit.categoria;
        this.items = this.itemToEdit.items;
        this.selectedAtores = this.itemToEdit.ator;
        this.selectedDiretor = this.itemToEdit.diretor;
        this.selectedClasse = this.itemToEdit.classe;
        this.isDialogOpen = true
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar título' });
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
    const obj: Titulo = {
      nome: this.nome,
      ano: this.ano,
      sinopse: this.sinopse,
      categoria: this.categoria,
      items: this.items,
      ator: this.selectedAtores,
      diretor: this.selectedDiretor,
      classe: this.selectedClasse,
      id: this.itemToEdit?.id
    }

    this.tituloService.update(obj).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Título editado com sucesso', life: 3000 });
        this.itemToEdit = null
        this.listAll()
        this.toggleDialog()
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao editar título' });
      }
    })
  }

  create(){
    const obj: Titulo = {
      nome: this.nome,
      ano: this.ano,
      sinopse: this.sinopse,
      categoria: this.categoria,
      items: this.items,
      ator: this.selectedAtores,
      diretor: this.selectedDiretor,
      classe: this.selectedClasse
    }

    console.log(obj.ator)
    this.tituloService.create(obj).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Título criado com sucesso', life: 3000 });
        this.listAll()
        this.toggleDialog()
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao criar título' });
      }
    })
  }

  delete(id: number){
    this.tituloService.delete(id).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Título excluído com sucesso', life: 3000 });
        this.listAll()
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir registro' });
      }
    })
  }

}
