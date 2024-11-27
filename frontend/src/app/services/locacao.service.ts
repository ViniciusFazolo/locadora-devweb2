import { HttpClient } from '@angular/common/http';
import { CrudService } from '../classes/CrudService';
import { environment } from '../environments/environment';
import { Injectable } from '@angular/core';
import { Locacao } from '../interfaces/locacao';

@Injectable({
  providedIn: 'root'
})
export class LocacaoService extends CrudService<Locacao>{

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/locacao`)
  }

}
