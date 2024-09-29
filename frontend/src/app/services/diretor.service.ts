import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '../classes/CrudService';
import { Diretor } from '../interfaces/diretor';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DiretorService extends CrudService<Diretor>{

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/diretor`)
  }
}
