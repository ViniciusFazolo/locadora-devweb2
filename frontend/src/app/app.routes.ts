import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AtorComponent } from './pages/ator/ator.component';
import { ClasseComponent } from './pages/classe/classe.component';
import { DiretorComponent } from './pages/diretor/diretor.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ItemComponent } from './pages/item/item.component';
import { TituloComponent } from './pages/titulo/titulo.component';
import { SocioComponent } from './pages/socio/socio.component';
import { DependenteComponent } from './pages/dependente/dependente.component';
import { LocacaoComponent } from './pages/locacao/locacao.component';
import { BuscarComponent } from './pages/buscar/buscar.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'ator',
        component: AtorComponent
    },
    {
        path: 'classe',
        component: ClasseComponent
    },
    {
        path: 'diretor',
        component: DiretorComponent
    },
    {
      path: 'item',
      component: ItemComponent
    },
    {
      path: 'titulo',
      component: TituloComponent
    },
    {
      path: 'socio',
      component: SocioComponent
    },
    {
      path: 'dependente',
      component: DependenteComponent
    },
    {
      path: 'locacao',
      component: LocacaoComponent
    },
    {
      path: 'buscar',
      component: BuscarComponent
    },
    {
        path: '**',
        component: NotFoundComponent
    }
];
