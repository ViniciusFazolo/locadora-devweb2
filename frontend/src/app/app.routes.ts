import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AtorComponent } from './pages/ator/ator.component';
import { ClasseComponent } from './pages/classe/classe.component';
import { DiretorComponent } from './pages/diretor/diretor.component';

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
];
