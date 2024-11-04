import { HttpClient } from '@angular/common/http';
import { CrudService } from '../classes/CrudService';
import { Item } from './../interfaces/item';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ItemService extends CrudService<Item>{

  constructor(httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/item`)
   }
}
