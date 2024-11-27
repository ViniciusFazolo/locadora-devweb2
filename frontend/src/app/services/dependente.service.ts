import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Dependente } from '../interfaces/dependente';
import { CrudService } from '../classes/CrudService';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DependenteService extends CrudService<Dependente>{

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/dependente`);
  }
}
