import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AtorComponent } from './pages/ator/ator.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'ator',
        component: AtorComponent
    }
];
