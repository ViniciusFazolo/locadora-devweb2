import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [ButtonModule, SidebarModule],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent {
  @Input() show: boolean = false;
  @Output() closeModal = new EventEmitter()
  
  close(){
    this.closeModal.emit()
  }
}
