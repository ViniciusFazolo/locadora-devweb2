import { Injectable } from '@angular/core';
import { CrudService } from '../classes/CrudService';
import { Ator } from '../interfaces/ator';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AtorService extends CrudService<Ator>{

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/ator`)
  }
}
