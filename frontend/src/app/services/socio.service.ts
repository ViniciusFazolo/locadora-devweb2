import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { CrudService } from '../classes/CrudService';
import { Socio } from '../interfaces/socio';

@Injectable({
  providedIn: 'root'
})
export class SocioService extends CrudService<Socio>{

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/socio`)
  }

}
