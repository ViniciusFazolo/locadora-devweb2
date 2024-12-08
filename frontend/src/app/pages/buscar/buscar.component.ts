import { Component } from '@angular/core';
import { LayoutBaseComponent } from '../../components/layout-base/layout-base.component';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';
import { Titulo } from '../../interfaces/titulo';
import { TituloService } from '../../services/titulo.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { Ator } from '../../interfaces/ator';
import { AtorService } from '../../services/ator.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-buscar',
  standalone: true,
  imports: [LayoutBaseComponent, RadioButtonModule, FormsModule, ButtonModule, ToastModule, CardModule, DatePipe],
  providers: [MessageService, DatePipe],
  templateUrl: './buscar.component.html',
  styles: `
    :host ::ng-deep .p-card-content	{
      padding: 0
    }
  `
})
export class BuscarComponent {
  buscaTipo: string = ''
  titulos!: Titulo[]
  nome: string = ''
  categoria: string = ''
  ator: string = ''

  //dados para o select
  categorias!: string[]
  atores!: string[]

  constructor(private tituloService: TituloService, private atorService: AtorService, private messageService: MessageService){}

  getCategorias(){
    if(!this.categorias){
      this.tituloService.listAll().subscribe({
        next: (res) => {
          const set = new Set<string>();

          res.forEach((titulo) => {
            if (titulo.categoria) {
              set.add(titulo.categoria);
            }
          });

          this.categorias = Array.from(set);
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar categorias' })
      })
    }
  }

  getAtores(){
    if(!this.atores){
      this.atorService.listAll().subscribe({
        next: (res) => {
          const set = new Set<string>();

          res.forEach((ator) => {
            if (ator.nome) {
              set.add(ator.nome);
            }
          });

          this.atores = Array.from(set);
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao listar atores' })
      })
    }
  }

  buscarPorNome(query: string){
    if(query == ""){
      this.toastWarn('Preencha o nome do título')
      return
    }

    this.tituloService.buscarPorNome(query).subscribe({
      next: (res) => this.titulos = res,
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao realizar busca' })
    })
  }

  buscarPorCategoria(query: string){
    if(query == ""){
      this.toastWarn('Selecione uma opção')
      return
    }

    this.tituloService.buscarPorCategoria(query).subscribe({
      next: (res) => this.titulos = res,
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao realizar busca' })
    })
  }
  
  buscarPorAtor(query: string){
    if(query == ""){
      this.toastWarn('Selecione uma opção')
      return
    }

    this.tituloService.buscarPorAtor(query).subscribe({
      next: (res) => this.titulos = res,
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao realizar busca' })
    })
  }

  toastWarn(mensagem: string){
    this.messageService.add({ severity: 'warn', summary: 'Aviso', detail: `${mensagem}` })
  }
}
