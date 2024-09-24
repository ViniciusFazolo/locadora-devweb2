import { Component, Input } from '@angular/core';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-layout-base',
  standalone: true,
  imports: [SidebarComponent, NavbarComponent, ButtonModule],
  templateUrl: './layout-base.component.html',
})
export class LayoutBaseComponent {
  @Input() title: string = ''
  @Input() btnTitle: string = ''
  showNavbar: boolean = false;

  toggleNavbar(){
    this.showNavbar = !this.showNavbar
  }
}
