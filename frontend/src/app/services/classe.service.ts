import { Injectable } from '@angular/core';
import { CrudService } from '../classes/CrudService';
import { Classe } from '../interfaces/classe';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClasseService extends CrudService<Classe> {

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/classe`)
  }
}
