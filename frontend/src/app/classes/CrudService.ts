import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseModelArray, ResponseModelObject } from '../interfaces/responseModel';

export class CrudService<T extends {id?: number}> {
  constructor(private httpClient: HttpClient, private url: string) {}

  listAll(): Observable<ResponseModelArray<T>> {
    return this.httpClient.get<ResponseModelArray<T>>(`${this.url}/listar`);
  }

  listById(id: number): Observable<ResponseModelObject<T>> {
    return this.httpClient.get<ResponseModelObject<T>>(`${this.url}/${id}`);
  }

  create(obj: T): Observable<ResponseModelObject<T>> {
    return this.httpClient.post<ResponseModelObject<T>>(`${this.url}/novo`, obj);
  }

  update(obj: T): Observable<ResponseModelObject<T>> {
    return this.httpClient.put<ResponseModelObject<T>>(`${this.url}/${obj.id}`, obj);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${id}`);
  }
}
