import { Injectable } from '@angular/core';
import { CrudService } from '../classes/CrudService';
import { Titulo } from '../interfaces/titulo';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TituloService extends CrudService<Titulo> {

  constructor(httpClient: HttpClient, private _httpClient: HttpClient) {
    super(httpClient, `${environment.apiUrl}/titulo`)
   }

   buscarPorNome(query: string): Observable<Titulo[]> {
    return this._httpClient.get<Titulo[]>(`${environment.apiUrl}/titulo/buscaPorNome/${query}`);
  }
   buscarPorCategoria(query: string): Observable<Titulo[]> {
    return this._httpClient.get<Titulo[]>(`${environment.apiUrl}/titulo/buscaPorCategoria/${query}`);
  }
   buscarPorAtor(query: string): Observable<Titulo[]> {
    return this._httpClient.get<Titulo[]>(`${environment.apiUrl}/titulo/buscaPorAtor/${query}`);
  }
}
