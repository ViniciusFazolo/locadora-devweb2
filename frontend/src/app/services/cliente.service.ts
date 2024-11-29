import { Injectable } from '@angular/core';
import { CrudService } from '../classes/CrudService';
import { Cliente } from '../interfaces/cliente';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends CrudService<Cliente> {

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/cliente`);
  }
}
